package modelo;

/**
 * Clase que representa a un coordinador en el sistema.
 * Hereda de la clase abstracta Persona.
 */
public class Coordinador extends Persona {
    private String areaResponsabilidad;
    private int numeroActividadesCoordinadas;

    public Coordinador(int id, String nombre, String identificacion, String correo, String areaResponsabilidad) {
        super(id, nombre, identificacion, correo);
        this.areaResponsabilidad = areaResponsabilidad;
        this.numeroActividadesCoordinadas = 0;
    }

    // Implementación de métodos abstractos
    @Override
    public String obtenerTipo() {
        return "Coordinador";
    }

    @Override
    public String getInformacionEspecifica() {
        return "Área de responsabilidad: " + areaResponsabilidad +
                "\nActividades coordinadas: " + numeroActividadesCoordinadas;
    }

    // Métodos específicos de Coordinador
    public void incrementarActividadesCoordinadas() {
        this.numeroActividadesCoordinadas++;
    }

    // Getters y setters específicos
    public String getAreaResponsabilidad() {
        return areaResponsabilidad;
    }

    public void setAreaResponsabilidad(String areaResponsabilidad) {
        this.areaResponsabilidad = areaResponsabilidad;
    }

    public int getNumeroActividadesCoordinadas() {
        return numeroActividadesCoordinadas;
    }

    @Override
    public String toString() {
        return super.getInformacionBasica() + "\n" + getInformacionEspecifica();
    }
}