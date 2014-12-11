package es.rpallas.usjandroidejer1.model;

/**
 * Created by rpallas on 28/11/14.
 */
public class PersonaJuridica extends Persona {

    private String nombre;
    private String telefono;
    private String email;
    private String web;


    private String area;

    public PersonaJuridica(String id) {
        super(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


}
