package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.rpallas.usjandroidejer1.model.PersonaJuridica;


public class ResultadosActivity extends Activity {

    private TextView depositante;
    private TextView material;
    private TextView nevera;
    private TextView aceites;
    private TextView kilos;
    private TextView kilosCoste;
    private TextView ivaCoste;
    private TextView totalCoste;
    private TextView numeroResiduos;

    private Button email;
    private Button registrar;

    private RelativeLayout costeLayout;

    private int cantidadResiduos = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        depositante = (TextView) findViewById(R.id.resultadoCIFText);
        material = (TextView) findViewById(R.id.resultadoMaterialInformaticoText);
        nevera = (TextView) findViewById(R.id.resultadoNeverasText);
        aceites = (TextView) findViewById(R.id.resultadosAceiteText);
        kilos = (TextView) findViewById(R.id.resultadosKgText);
        kilosCoste = (TextView) findViewById(R.id.resultadosPrecioText);
        ivaCoste = (TextView) findViewById(R.id.resultadoCosteIVAText);
        totalCoste = (TextView) findViewById(R.id.resultadoPrecioTotalText);
        numeroResiduos = (TextView) findViewById(R.id.resultadoNumeroText);

        costeLayout = (RelativeLayout) findViewById(R.id.resultadoCosteRLayout);

        email = (Button) findViewById(R.id.resultadoMailButton);
        registrar = (Button) findViewById(R.id.resultadoRegistrarButton);

        depositante.setText(Ejercico1Application.PERSONA.getId());

        if(!Ejercico1Application.isMaterialChecked){
            material.setVisibility(View.GONE);
            cantidadResiduos--;
        }
        if(!Ejercico1Application.isAceitesChecked){
            aceites.setVisibility(View.GONE);
            cantidadResiduos--;
        }
        if(!Ejercico1Application.isNeverasChecked){
            nevera.setVisibility(View.GONE);
            cantidadResiduos--;
        }
        numeroResiduos.setText(cantidadResiduos + " " +numeroResiduos.getText().toString());

        if(!(Ejercico1Application.PERSONA instanceof PersonaJuridica)){
            costeLayout.setVisibility(View.GONE);
        }
        else {
            kilos.setText(Ejercico1Application.kilos + " " + kilos.getText().toString());
            double precioSinIVA = ((double)Ejercico1Application.kilos)*2.5;
            double IVA = ((double)Ejercico1Application.kilos)*2.5*0.20;
            double precioTotal = precioSinIVA + IVA;
            kilosCoste.setText(precioSinIVA+"€");
            ivaCoste.setText(IVA+"€");
            totalCoste.setText(precioTotal+"€");
        }

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanzarLoginActivity = new Intent(ResultadosActivity.this, LoginActivity.class);
                lanzarLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(lanzarLoginActivity);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Nuevo depósito realizado por la persona "+ Ejercico1Application.PERSONA.getId());
                intent.setType("message/rfc822");
                Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resultados, menu);
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
}
