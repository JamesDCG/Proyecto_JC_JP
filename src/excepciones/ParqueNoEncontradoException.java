package excepciones;

/**
 * Excepción lanzada cuando no se encuentra un parque en el sistema.
 */
public class ParqueNoEncontradoException extends Exception {
    private int idParque;

    public ParqueNoEncontradoException(int idParque) {
        super("No se encontró un parque con el ID: " + idParque);
        this.idParque = idParque;
    }

    public ParqueNoEncontradoException(String nombreParque) {
        super("No se encontró un parque con el nombre: " + nombreParque);
        this.idParque = -1; // Indica que se buscó por nombre
    }

    public int getIdParque() {
        return idParque;
    }

    public boolean isBusquedaPorNombre() {
        return idParque == -1;
    }

    @Override
    public String toString() {
        return getMessage() + " [Tipo búsqueda: " +
                (isBusquedaPorNombre() ? "Por nombre" : "Por ID") + "]";
    }
}