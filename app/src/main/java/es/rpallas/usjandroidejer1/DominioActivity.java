package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import es.rpallas.usjandroidejer1.model.PersonaJuridica;


public class DominioActivity extends FragmentActivity {

    private EditText urlET;
    private TextView ipTV;
    private TextView paisTV;
    private TextView localidadTV;
    private TextView coordenadasTV;

    private double latitude, longitude = 0.0;

    private GoogleMap mMap;



    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominio);



        urlET = (EditText) findViewById(R.id.dominioEditText);
        ipTV = (TextView) findViewById(R.id.dominioIP);
        paisTV = (TextView) findViewById(R.id.dominioPais);
        localidadTV = (TextView) findViewById(R.id.dominioLocalidad);
        coordenadasTV = (TextView) findViewById(R.id.dominioCoordenadas);



        urlET.setText(Ejercico1Application.empresaURL);

        new AsyncTask<Void, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Void... urls) {
                return getJSONFromUrl("http://www.telize.com/geoip/"+getIpAddress(Ejercico1Application.empresaURL));
            }

            @Override
            protected void onPostExecute(JSONObject jsonData) {
                String pais = "Desconocido";
                String ip = "Desconocido";
                String localidad = "Desconocido";

                try {
                    longitude = jsonData.getDouble("longitude");
                    latitude = jsonData.getDouble("latitude");
                    pais = jsonData.getString("country") + "(" +jsonData.getString("country_code") + ")";
                    ip = jsonData.getString("ip");
                    localidad = jsonData.getString("city");

                    if (mMap != null) {
                        setUpMap();
                    }




                } catch (Exception e){
                    e.printStackTrace();
                }

                ipTV.setText(ip);
                paisTV.setText(pais);
                localidadTV.setText(localidad);
                coordenadasTV.setText(latitude+", "+longitude);
            }
        }.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        }
    }

    private void setUpMap() {
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(latitude,
                        longitude));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
    }

    private String getIpAddress(String url){
        InetAddress address = null;
        try {
            address = InetAddress.getByName(new URL(url).getHost());
            ;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dominio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







    public JSONObject getJSONFromUrl(String url) {
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }

}
