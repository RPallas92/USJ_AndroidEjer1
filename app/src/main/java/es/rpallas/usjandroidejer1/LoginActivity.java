package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {

    TextView userText;
    TextView passwordText;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userText = (TextView) findViewById(R.id.loginUserText);
        passwordText = (TextView) findViewById(R.id.loginPasswordText);

        leerCredenciales();

        enterButton = (Button) findViewById(R.id.loginEnterButton);
        if(userText.getText().equals("") && passwordText.getText().equals("")){
            enterButton.setEnabled(false);
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almacenarCredenciales();
                if (((userText.getText().toString()).equals(Ejercico1Application.USER)) && ((passwordText.getText().toString()).equals(Ejercico1Application.PASSWORD))){
                    Toast.makeText(LoginActivity.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                    Intent lanzarPuntoLimpioActivity = new Intent(LoginActivity.this, PuntoLimpioActivity.class);
                    lanzarPuntoLimpioActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(lanzarPuntoLimpioActivity);
                } else {
                    Toast.makeText(LoginActivity.this, "Acceso incorrecto", Toast.LENGTH_SHORT).show();
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

        userText.addTextChangedListener(textoHaCambiado);
        passwordText.addTextChangedListener(textoHaCambiado);

    }
    private void almacenarCredenciales(){
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", userText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.commit();
    }
    private void leerCredenciales(){
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        String nombre = prefs.getString("nombre", "Usuario");
        String password = prefs.getString("password", "******");

        userText.setText(nombre);
        passwordText.setText(password);
    }


    private void activarBotonSiRellenado() {

        boolean userRellenado = userText.getText().toString().length()>1;
        boolean passwordRellenado = passwordText.getText().toString().length()>1;

        if (userRellenado && passwordRellenado) {
            enterButton.setEnabled(true);
        } else {
            enterButton.setEnabled(false);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
