package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un parque en el sistema.
 */
public class Parque {
    private int id;
    private String nombre;
    private String ubicacion;
    private double tamanio; // en metros cuadrados
    private List<ActividadLimpieza> actividades;

    public Parque(int id, String nombre, String ubicacion, double tamanio) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tamanio = tamanio;
        this.actividades = new ArrayList<>();
    }

    // Métodos para gestionar actividades
    public void agregarActividad(ActividadLimpieza actividad) {
        if (actividad != null) {
            this.actividades.add(actividad);
        }
    }

    public List<ActividadLimpieza> getActividades() {
        return new ArrayList<>(actividades); // Devuelve una copia para proteger la encapsulación
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNombre: " + nombre +
                "\nUbicación: " + ubicacion +
                "\nTamaño (m²): " + tamanio +
                "\nActividades registradas: " + actividades.size();
    }
}