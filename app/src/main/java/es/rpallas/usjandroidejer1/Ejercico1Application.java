package es.rpallas.usjandroidejer1;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.rpallas.usjandroidejer1.model.Persona;

/**
 * Created by rpallas on 28/11/14.
 */
public class Ejercico1Application extends Application {

    public static Persona PERSONA;
    public static final String USER = "Ricardo";
    public static final String PASSWORD = "pallas";
    public static boolean isMaterialChecked = false;
    public static boolean isNeverasChecked = false;
    public static boolean isAceitesChecked = false;
    public static int kilos = 0;
    public static List<String> puntosLimpios = new ArrayList<String>();
    public static String puntoLimpio;
    public static String puntoLimpioImagenURL = "http://www.aulaclic.es/winxp/graficos/icono_papelera.gif";

    @Override
    public void onCreate() {
        super.onCreate();
        puntosLimpios.add("San José - Lasfuentes");
        puntosLimpios.add("Cogullada");
        puntosLimpios.add("Universidad - Delicias");
        puntosLimpios.add("Valdespartera");

        puntoLimpio = puntosLimpios.get(0);

    }

    public static void showLogoutDialog(final Context context) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent lanzarLoginActivity = new Intent(context, LoginActivity.class);
                        lanzarLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(lanzarLoginActivity);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Seguro qué desea desconectarse de la aplicación?").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
