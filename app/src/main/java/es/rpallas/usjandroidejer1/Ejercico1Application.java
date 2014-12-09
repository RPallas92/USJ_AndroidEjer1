package es.rpallas.usjandroidejer1;

import android.app.Application;

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
}
