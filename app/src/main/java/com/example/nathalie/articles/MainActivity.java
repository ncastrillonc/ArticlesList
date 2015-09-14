package com.example.nathalie.articles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.cengalabs.flatui.views.FlatRadioButton;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> nuevoArt = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread tr = new Thread() {
            @Override
            public void run() {

                final String Resultado = getData();
                runOnUiThread(
                        new Runnable() {

                            @Override
                            public void run() {
                                articlesJSON(Resultado);
                            }
                        });
            }
        };
        tr.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getData(){
        HttpClient cliente =new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpget = new HttpGet("http://www.ckl.io/challenge/");
        String resultado=null;

        // Se obtiene el archivo JSON de la página indicada
        try {
            HttpResponse response = cliente.execute(httpget,contexto);
            HttpEntity entity = response.getEntity();
            resultado = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return resultado;
    }

    public void articlesJSON(String response){

        // Creamos una lista de articulos con los datos almacenados en el archivo JSON

        try {
            JSONArray json= new JSONArray(response);

            for (int i=0; i<json.length();i++){

                this.nuevoArt.add(new Article(json.getJSONObject(i).getString("website"),
                                              json.getJSONObject(i).getString("title"),
                                              json.getJSONObject(i).getString("image"),
                                              R.drawable.memento,
                                              json.getJSONObject(i).getString("content"),
                                              json.getJSONObject(i).getString("authors"),
                                              json.getJSONObject(i).getString("date")));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void listadoOnClick(View view) {

        onRadioButtonClicked();

        Intent i = new Intent(MainActivity.this, ListadoActivity.class);

        // Creamos la informacion a pasar entre actividades
        Bundle b_on = new Bundle();
        b_on.putParcelableArrayList("articleslist", this.nuevoArt);

        // Agregamos la informacion al intent
        i.putExtras(b_on);
        startActivity(i);
    }

    public void onRadioButtonClicked() {
        // Is the button now checked?

        FlatRadioButton rb = (FlatRadioButton) findViewById(R.id.datee);
        FlatRadioButton rb1 = (FlatRadioButton) findViewById(R.id.authors);
        FlatRadioButton rb2 = (FlatRadioButton) findViewById(R.id.titlee);

        if(rb.isChecked()){
            Article.op = 0;
        } else if(rb1.isChecked()){
            Article.op = 2;
        } else if(rb2.isChecked()) {
            Article.op = 1;
        } else {
            Article.op = 3;
        }

        Article[] arrayArticles = new Article[this.nuevoArt.size()];

        for(int k=0; k<this.nuevoArt.size(); k++){
            arrayArticles[k] = this.nuevoArt.get(k);
        }

        /* Método Arrays.sort(), que recibe un array y simplemente lo ordena teniendo en
           cuenta las condiciones que le hayamos dicho dentro del método compareTo de la
           clase Article
         */
        Arrays.sort(arrayArticles);

        for(int k=0; k<this.nuevoArt.size(); k++){
            this.nuevoArt.set(k, arrayArticles[k]);
        }
    }
}
