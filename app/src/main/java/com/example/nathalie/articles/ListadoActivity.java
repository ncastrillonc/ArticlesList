package com.example.nathalie.articles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nathalie on 13/09/2015.
 */
public class ListadoActivity extends Activity {

    private ArrayList<Article> listArt = new ArrayList<Article>();
    private ListView listView;
    private TextView textView;
    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display a indeterminate progress bar on title bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        this.setContentView(R.layout.activity_listado);

        // Asociamos cada elementos de la actividad a una variable
        this.listView = (ListView) findViewById(R.id.listView);
        this.textView = (TextView) findViewById(R.id.TextContent);
        this.webView = (WebView) findViewById(R.id.webView);

        // Recuperamos la informacion pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        this.listArt = bundle.getParcelableArrayList("articleslist");

        // Sets the data behind this ListView
        this.listView.setAdapter(new ItemAdapter(this, this.listArt));

        // Register a callback to be invoked when an item in this AdapterView
        // has been clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {

                // Sets the visibility of the indeterminate progress bar in the
                // title
                setProgressBarIndeterminateVisibility(true);
                // Show progress dialog
                progressDialog = ProgressDialog.show(ListadoActivity.this,
                        "ProgressDialog", "Loading!");

                // Tells JavaScript to open windows automatically.
                webView.getSettings().setJavaScriptEnabled(true);
                // Sets our custom WebViewClient.
                webView.setWebViewClient(new myWebClient());
                // Loads the given URL
                Article item = (Article) listView.getAdapter().getItem(position);
                webView.loadUrl(item.getImage());
                // Muestra la toda la información del artículo en un TextView
                textView.setText("Website: " + item.getWebsite() + "\n\nTitle: " + item.getTitle() +
                                 "\n\nContent: " + item.getContent() + "\n\nAuthors: " +
                                 item.getAuthors() + "\n\nDate: " + item.getDate());
            }
        });
    }

    /* Se define un cliente para que la página se cargue en la misma pantalla y nos se abra la
       página en un navegador Web que tengamos instalados, para ello definimos la siguiente clase
       interna:
     */
    private class myWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Load the given URL on our WebView.
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // When the page has finished loading, hide progress dialog and
            // progress bar in the title.
            super.onPageFinished(view, url);
            setProgressBarIndeterminateVisibility(false);
            progressDialog.dismiss();
        }
    }


}