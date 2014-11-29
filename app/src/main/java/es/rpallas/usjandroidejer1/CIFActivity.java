package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import es.rpallas.usjandroidejer1.model.Persona;
import es.rpallas.usjandroidejer1.model.PersonaJuridica;


public class CIFActivity extends Activity {


    private RadioGroup radioGroup;
    private boolean esEmpresa = true;

    private TextView cifText;
    private EditText cifEditText;
    private TextView dniText;
    private EditText dniEditText;

    private Button inciarDeposito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cif);

        cifText = (TextView) findViewById(R.id.cifText);
        cifEditText = (EditText) findViewById(R.id.CIFEditText);
        dniText = (TextView) findViewById(R.id.DNIText);
        dniEditText = (EditText) findViewById(R.id.DNIEditText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        inciarDeposito = (Button) findViewById(R.id.datosWebButton);
        inciarDeposito.setEnabled(false);

        dniText.setVisibility(View.GONE);
        dniEditText.setVisibility(View.GONE);



        int checkedRadioButtonID = radioGroup.getCheckedRadioButtonId();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.empresaRadioButton:
                        esEmpresa = true;
                        dniEditText.setText("");
                        dniText.setVisibility(View.GONE);
                        dniEditText.setVisibility(View.GONE);
                        cifEditText.setVisibility(View.VISIBLE);
                        cifText.setVisibility(View.VISIBLE);

                        break;
                    case R.id.ciudadanoRadioButton:
                        esEmpresa = false;
                        cifEditText.setText("");
                        dniText.setVisibility(View.VISIBLE);
                        dniEditText.setVisibility(View.VISIBLE);
                        cifEditText.setVisibility(View.GONE);
                        cifText.setVisibility(View.GONE);

                        break;

                    default:

                        break;
                }
            }
        });


        TextWatcher textoHaCambiado = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activarBotonSiRellenado();
            }
        };

        cifEditText.addTextChangedListener(textoHaCambiado);
        dniEditText.addTextChangedListener(textoHaCambiado);

        inciarDeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esEmpresa){
                    Ejercico1Application.PERSONA = new PersonaJuridica(cifEditText.getText().toString());
                    Intent irPantallaDatosEmpresa = new Intent(CIFActivity.this, DatosEmpresaActivity.class);
                    startActivity(irPantallaDatosEmpresa);
                } else {
                    Ejercico1Application.PERSONA = new Persona(dniEditText.getText().toString());
                    Intent irPantallaDeposito = new Intent(CIFActivity.this, DepositoActivity.class);
                    startActivity(irPantallaDeposito);
                }
            }
        });

    }





    private void activarBotonSiRellenado() {

        boolean cifRellenado = cifEditText.getText().toString().length() > 1;
        boolean dniRellenado = dniEditText.getText().toString().length() > 1;

        if (cifRellenado || dniRellenado) {
            inciarDeposito.setEnabled(true);
        } else {
            inciarDeposito.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ci, menu);
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
