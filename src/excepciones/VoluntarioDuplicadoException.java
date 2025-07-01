package excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un voluntario que ya existe en el sistema.
 */
public class VoluntarioDuplicadoException extends Exception {
    private String identificacion;

    public VoluntarioDuplicadoException(String identificacion) {
        super("Ya existe un voluntario registrado con la identificación: " + identificacion);
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    @Override
    public String toString() {
        return getMessage() + " [Identificación: " + identificacion + "]";
    }
}