package interfaces;

import excepciones.VoluntarioDuplicadoException;
import excepciones.ParqueNoEncontradoException;
import java.util.List;

/**
 * Interface para operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * @param <T> Tipo de entidad a gestionar
 */
public interface Gestionable<T> {
    /**
     * Registra un nuevo elemento en el sistema
     * @param elemento Elemento a registrar
     * @throws VoluntarioDuplicadoException Si se intenta registrar un duplicado
     */
    void registrar(T elemento) throws VoluntarioDuplicadoException;

    /**
     * Obtiene un elemento por su ID
     * @param id Identificador único
     * @return Elemento encontrado
     * @throws ParqueNoEncontradoException Si no se encuentra el elemento
     */
    T obtenerPorId(int id) throws ParqueNoEncontradoException;

    /**
     * Actualiza un elemento existente
     * @param elemento Elemento con los datos actualizados
     * @return true si la actualización fue exitosa
     */
    boolean actualizar(T elemento);

    /**
     * Elimina un elemento del sistema
     * @param id Identificador único del elemento a eliminar
     * @return true si la eliminación fue exitosa
     */
    boolean eliminar(int id);

    /**
     * Obtiene todos los elementos registrados
     * @return Lista de elementos
     */
    List<T> obtenerTodos();
}