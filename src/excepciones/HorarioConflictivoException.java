package excepciones;

import java.util.Date;

/**
 * Excepción lanzada cuando un voluntario tiene conflicto de horarios con una actividad.
 */
public class HorarioConflictivoException extends Exception {
    private int idVoluntario;
    private Date fechaActividad;
    private String nombreParque;

    public HorarioConflictivoException(int idVoluntario, Date fechaActividad, String nombreParque) {
        super("El voluntario con ID " + idVoluntario + " ya tiene una actividad programada para " +
                fechaActividad + " en el parque " + nombreParque);
        this.idVoluntario = idVoluntario;
        this.fechaActividad = fechaActividad;
        this.nombreParque = nombreParque;
    }

    public int getIdVoluntario() {
        return idVoluntario;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public String getNombreParque() {
        return nombreParque;
    }

    @Override
    public String toString() {
        return getMessage() + " [Voluntario ID: " + idVoluntario +
                ", Fecha: " + fechaActividad +
                ", Parque: " + nombreParque + "]";
    }
}