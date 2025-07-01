package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa una actividad de limpieza en el sistemas.
 */
public class ActividadLimpieza {
    private int id;
    private Date fecha;
    private Parque parque;
    private List<Voluntario> voluntarios;
    private double duracionHoras;
    private String descripcion;

    public ActividadLimpieza(int id, Date fecha, Parque parque, double duracionHoras, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.parque = parque;
        this.duracionHoras = duracionHoras;
        this.descripcion = descripcion;
        this.voluntarios = new ArrayList<>();
    }

    // Métodos para gestionar voluntarios
    public void agregarVoluntario(Voluntario voluntario) {
        if (voluntario != null && !voluntarios.contains(voluntario)) {
            voluntarios.add(voluntario);
            voluntario.agregarHoras((int) duracionHoras);
        }
    }

    public List<Voluntario> getVoluntarios() {
        return new ArrayList<>(voluntarios); // Devuelve una copia para proteger la encapsulación
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Parque getParque() {
        return parque;
    }

    public void setParque(Parque parque) {
        this.parque = parque;
    }

    public double getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(double duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ID Actividad: " + id +
                "\nFecha: " + fecha +
                "\nParque: " + parque.getNombre() +
                "\nDuración (horas): " + duracionHoras +
                "\nDescripción: " + descripcion +
                "\nVoluntarios participantes: " + voluntarios.size();
    }
}