package es.rpallas.usjandroidejer1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.zip.CheckedOutputStream;


public class DepositoActivity extends Activity {

    private EditText depositante;
    private CheckBox materiales;
    private CheckBox neveras;
    private CheckBox aceites;
    private EditText peso;
    private Button depositar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);

        depositante = (EditText) findViewById(R.id.depositoCIFEditText);
        materiales = (CheckBox) findViewById(R.id.depositoMaterialCheck);
        neveras = (CheckBox) findViewById(R.id.depositoNeveraCheck);
        aceites = (CheckBox) findViewById(R.id.depositoAceiteCheck);
        peso = (EditText) findViewById(R.id.depositoKiloEditText);
        depositar = (Button) findViewById(R.id.depositoDepositarButton);

        depositante.setText(Ejercico1Application.PERSONA.getId());
        depositante.setEnabled(false);

        peso.setEnabled(false);
        depositar.setEnabled(false);

        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (materiales.isChecked() || neveras.isChecked() || aceites.isChecked()) {
                    peso.setEnabled(true);
                    depositar.setEnabled(true);
                } else {
                    peso.setEnabled(false);
                    depositar.setEnabled(false);
                }
            }
        };

        materiales.setOnCheckedChangeListener(checkListener);
        neveras.setOnCheckedChangeListener(checkListener);
        aceites.setOnCheckedChangeListener(checkListener);

        configDepositarButton();

    }

    private void configDepositarButton() {

        depositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materiales.isChecked()) {
                    Ejercico1Application.isMaterialChecked = true;
                }
                else {
                    Ejercico1Application.isMaterialChecked = false;
                }
                if (neveras.isChecked()) {
                    Ejercico1Application.isNeverasChecked = true;
                } else {
                    Ejercico1Application.isNeverasChecked = false;
                }
                if (aceites.isChecked()) {
                    Ejercico1Application.isAceitesChecked = true;
                } else {
                    Ejercico1Application.isAceitesChecked = false;
                }

                Ejercico1Application.kilos = Integer.parseInt(peso.getText().toString());

                Intent lanzarResultadosActivity = new Intent(DepositoActivity.this, ResultadosActivity.class);
                startActivity(lanzarResultadosActivity);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deposito, menu);
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
