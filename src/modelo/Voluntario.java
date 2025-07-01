package modelo;

/**
 * Clase que representa a un voluntario en el sistema.
 * Hereda de la clase abstracta Persona.
 */
public class Voluntario extends Persona {
    private String disponibilidad;
    private int horasAcumuladas;

    public Voluntario(int id, String nombre, String identificacion, String correo, String disponibilidad) {
        super(id, nombre, identificacion, correo);
        this.disponibilidad = disponibilidad;
        this.horasAcumuladas = 0;
    }

    // Implementación de métodos abstractos
    @Override
    public String obtenerTipo() {
        return "Voluntario";
    }

    @Override
    public String getInformacionEspecifica() {
        return "Disponibilidad: " + disponibilidad +
                "\nHoras acumuladas: " + horasAcumuladas;
    }

    // Métodos específicos de Voluntario
    public void agregarHoras(int horas) {
        if (horas > 0) {
            this.horasAcumuladas += horas;
        }
    }

    // Getters y setters específicos
    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    @Override
    public String toString() {
        return super.getInformacionBasica() + "\n" + getInformacionEspecifica();
    }
}