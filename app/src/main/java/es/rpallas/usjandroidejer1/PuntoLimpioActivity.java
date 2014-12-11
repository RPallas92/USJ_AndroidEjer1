package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import es.rpallas.usjandroidejer1.R;

public class PuntoLimpioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_limpio);

        ListView listView = (ListView) findViewById(R.id.puntosLimpiosListView);
        PuntoLimpioListAdapter adapter = new PuntoLimpioListAdapter(Ejercico1Application.puntosLimpios);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ejercico1Application.puntoLimpio = Ejercico1Application.puntosLimpios.get(position);
                Intent lanzarCIFActivity = new Intent(PuntoLimpioActivity.this, CIFActivity.class);
                startActivity(lanzarCIFActivity);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.punto_limpio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Ejercico1Application.showLogoutDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PuntoLimpioListAdapter extends BaseAdapter{
        private List<String> puntosLimpios = null;

        public PuntoLimpioListAdapter(List<String> puntosLimpios){
            this.puntosLimpios = puntosLimpios;
        }
        @Override
        public int getCount() {
            return puntosLimpios.size();
        }

        @Override
        public Object getItem(int position) {
            return puntosLimpios.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String puntoLimpio = (String) getItem(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(PuntoLimpioActivity.this);
                convertView = inflater.inflate(R.layout.item_punto_limpio, parent, false);
                viewHolder.nombre = (TextView) convertView.findViewById(R.id.listViewText);
                viewHolder.imagen = (ImageView) convertView.findViewById(R.id.listViewImage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nombre.setText(puntoLimpio);
            new DownloadImageTask().execute(viewHolder);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView nombre;
        ImageView imagen;
        Bitmap bitmap;
    }

    private class DownloadImageTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {

            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(Ejercico1Application.puntoLimpioImagenURL);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", "Error: no se ha podido descargar la imagen");
                viewHolder.bitmap = null;
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder viewHolder) {
            if (viewHolder.bitmap != null) {
                viewHolder.imagen.setImageBitmap(viewHolder.bitmap);
            }
        }
    }
}
