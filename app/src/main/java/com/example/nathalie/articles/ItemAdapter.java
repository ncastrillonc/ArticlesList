package com.example.nathalie.articles;

/**
 * Created by Nathalie on 13/09/2015.
 */
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Article> items;
    private Bitmap loadedImage;
    private List<Bitmap> imgs = new ArrayList<Bitmap>();

    /* Recibe el contacto de la aplicación y la lista de elementos que se van a mostrar
       en la lista
     */
    public ItemAdapter(Context context, List<Article> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        // Devuelve el número de elementos de la lista
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        // Devuelve el elemento en una determinada posición de la lista
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Devuelve el identificador de fila de una determinada posición de la lista
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /* Este método ha de construir un nuevo objeto View con el layout correspondiente a la
           posición position y devolverlo. Opcionalmente, podemos partir de una vista base
           convertView para generar más rápido las vistas. El último parámetro corresponde al
           padre al que la vista va a ser añadida.
         */

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Set data into the view.
        final ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivItem);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);

        final Article item = this.items.get(position);
        tvTitle.setText(item.getWebsite() + ": " + item.getAuthors());

        //ivItem.setImageResource(item.getImg());
        //downloadFile(item.getImage());

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    final URL imageUrl = new URL(item.getImage());
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2

                    Bitmap loadedImage = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
                    ivItem.setImageBitmap(loadedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return rowView;
    }

}