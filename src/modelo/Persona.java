package modelo;

import java.util.Objects;

/**
 * Clase abstracta que representa a una persona en el sistema.
 * Implementa los atributos y métodos comunes para todas las personas.
 */
public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String identificacion;
    protected String correo;

    // Constructor
    public Persona(int id, String nombre, String identificacion, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
    }

    // Métodos abstractos que deben ser implementados por las subclases
    public abstract String obtenerTipo();
    public abstract String getInformacionEspecifica();

    // Métodos concretos comunes a todas las personas
    public String getInformacionBasica() {
        return "ID: " + id +
                "\nNombre: " + nombre +
                "\nIdentificación: " + identificacion +
                "\nCorreo: " + correo;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Métodos equals y hashCode para comparación de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id == persona.id &&
                Objects.equals(identificacion, persona.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identificacion);
    }
}