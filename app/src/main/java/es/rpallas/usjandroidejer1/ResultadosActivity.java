package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.rpallas.usjandroidejer1.model.PersonaJuridica;


public class ResultadosActivity extends Activity implements DatePickerDialog.OnDateSetListener{

    private TextView puntoLimpio;
    private TextView depositante;
    private TextView material;
    private TextView nevera;
    private TextView aceites;
    private TextView kilos;
    private TextView kilosCoste;
    private TextView ivaCoste;
    private TextView totalCoste;
    private TextView numeroResiduos;

    private TextView resultadoFecha;
    private Button editarFecha;

    private Button email;
    private Button registrar;

    private RelativeLayout costeLayout;

    private int cantidadResiduos = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        puntoLimpio = (TextView) findViewById(R.id.resultadosDepositoText);
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

        editarFecha = (Button) findViewById(R.id.editarFecha);
        resultadoFecha = (TextView) findViewById(R.id.resultadoFecha);

        puntoLimpio.setText(Ejercico1Application.puntoLimpio);

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


            editarFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "datePicker");


                }
            });
        }

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResultadosActivity.this, "Depósito registrado correctamente",Toast.LENGTH_SHORT).show();
                Intent lanzarPuntoActivity = new Intent(ResultadosActivity.this, PuntoLimpioActivity.class);
                lanzarPuntoActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(lanzarPuntoActivity);
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
            Ejercico1Application.showLogoutDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment
             {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        }


    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        resultadoFecha.setText(day+"/"+month+"/"+year);
    }
}
