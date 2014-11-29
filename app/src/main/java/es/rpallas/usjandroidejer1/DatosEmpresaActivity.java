package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import es.rpallas.usjandroidejer1.model.PersonaJuridica;


public class DatosEmpresaActivity extends Activity {


    private EditText cifEditText;
    private EditText nombreEditText;
    private EditText telefonoEditText;
    private EditText emailEditText;
    private EditText urlEditText;

    private Button llamarButton;
    private Button emailButton;
    private Button webButton;
    private Button siguienteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_empresa);


        cifEditText = (EditText) findViewById(R.id.datosCIFEditText);
        nombreEditText = (EditText) findViewById(R.id.datosNombreEditText);
        telefonoEditText = (EditText) findViewById(R.id.datosTelEditText);
        emailEditText = (EditText) findViewById(R.id.datosEmailEditText);
        urlEditText = (EditText) findViewById(R.id.datosWebEditText);

        llamarButton = (Button) findViewById(R.id.datosLlamarButton);
        emailButton = (Button) findViewById(R.id.datosEmailButton);
        webButton = (Button) findViewById(R.id.datosWebButton);

        llamarButton.setEnabled(false);
        emailButton.setEnabled(false);
        webButton.setEnabled(false);

        siguienteButton = (Button) findViewById(R.id.depositoMaterialImage);
        siguienteButton.setEnabled(false);


        cifEditText.setEnabled(false);
        cifEditText.setText(Ejercico1Application.PERSONA.getId());

        TextWatcher simpleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (telefonoEditText.getText().hashCode() == s.hashCode()) {
                    activarBotonSiRellenado(llamarButton, telefonoEditText);
                } else if (emailEditText.getText().hashCode() == s.hashCode()){
                    activarBotonSiRellenado(emailButton, emailEditText);

                } else if (urlEditText.getText().hashCode() == s.hashCode()) {
                    activarBotonSiRellenado(webButton, urlEditText);

                } else if (nombreEditText.getText().hashCode() == s.hashCode()){
                    activarBotonSiRellenado(null, nombreEditText);
                }
            }
        };

        telefonoEditText.addTextChangedListener(simpleTextWatcher);
        emailEditText.addTextChangedListener(simpleTextWatcher);
        urlEditText.addTextChangedListener(simpleTextWatcher);
        nombreEditText.addTextChangedListener(simpleTextWatcher);


        configLlamarButton();
        configEmailButton();
        configWebButton();

        configSiguienteButton();
    }

    private void configLlamarButton() {
        llamarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Patterns.PHONE.matcher(telefonoEditText.getText().toString()).matches()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + telefonoEditText.getText().toString()));
                    startActivity(intent);
                } else {
                    Toast.makeText(DatosEmpresaActivity.this, "Teléfono incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configEmailButton() {
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", emailEditText.getText().toString(), null));
                    startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                } else {
                    Toast.makeText(DatosEmpresaActivity.this, "Email incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configWebButton() {
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Patterns.WEB_URL.matcher(urlEditText.getText().toString()).matches()) {
                    if (!urlEditText.getText().toString().startsWith("http://") && !urlEditText.getText().toString().startsWith("https://")) {
                        urlEditText.setText("http://" + urlEditText.getText().toString());
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlEditText.getText().toString()));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(DatosEmpresaActivity.this, "URL incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configSiguienteButton(){

        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Patterns.PHONE.matcher(telefonoEditText.getText().toString()).matches() &&
                        Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() &&
                        Patterns.WEB_URL.matcher(urlEditText.getText().toString()).matches() ){

                    if(Ejercico1Application.PERSONA instanceof PersonaJuridica){
                        PersonaJuridica empresa = (PersonaJuridica) Ejercico1Application.PERSONA;
                        empresa.setEmail(emailEditText.getText().toString());
                        empresa.setNombre(nombreEditText.getText().toString());
                        empresa.setTelefono(telefonoEditText.getText().toString());
                        empresa.setWeb(urlEditText.getText().toString());
                    }
                    Intent lanzarDepositoActivity = new Intent(DatosEmpresaActivity.this, DepositoActivity.class);
                    startActivity(lanzarDepositoActivity);
                }
                else {
                    Toast.makeText(DatosEmpresaActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void activarBotonSiRellenado(Button button, EditText editText) {

        boolean editTextRellenado = editText.getText().toString().length() > 1;
        boolean telefonoRellenado = telefonoEditText.getText().toString().length() > 1;
        boolean emailRellenado = emailEditText.getText().toString().length() > 1;
        boolean webRellenada = urlEditText.getText().toString().length() > 1;
        boolean nombreRellenado = nombreEditText.getText().toString().length() > 1;

        if(button!=null) {
            if (editTextRellenado) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        }

        if (telefonoRellenado && emailRellenado && webRellenada && nombreRellenado) {
            siguienteButton.setEnabled(true);
        } else {
            siguienteButton.setEnabled(false);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.datos_empresa, menu);
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
