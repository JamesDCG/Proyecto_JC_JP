package servicios;

import interfaces.Gestionable;
import modelo.Voluntario;
import excepciones.VoluntarioDuplicadoException;
import excepciones.ParqueNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorVoluntarios implements Gestionable<Voluntario> {
    private List<Voluntario> voluntarios = new ArrayList<>();

    @Override
    public void registrar(Voluntario voluntario) throws VoluntarioDuplicadoException {
        if (existeVoluntario(voluntario.getIdentificacion())) {
            throw new VoluntarioDuplicadoException(voluntario.getIdentificacion());
        }
        voluntarios.add(voluntario);
    }

    @Override
    public Voluntario obtenerPorId(int id) throws ParqueNoEncontradoException {
        return voluntarios.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ParqueNoEncontradoException(id));
    }

    public Voluntario obtenerPorIdentificacion(String identificacion) throws ParqueNoEncontradoException {
        return voluntarios.stream()
                .filter(v -> v.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElseThrow(() -> new ParqueNoEncontradoException(identificacion));
    }

    @Override
    public boolean actualizar(Voluntario voluntario) {
        try {
            Voluntario existente = obtenerPorId(voluntario.getId());
            existente.setNombre(voluntario.getNombre());
            existente.setCorreo(voluntario.getCorreo());
            existente.setDisponibilidad(voluntario.getDisponibilidad());
            return true;
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            Voluntario voluntario = obtenerPorId(id);
            return voluntarios.remove(voluntario);
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public List<Voluntario> obtenerTodos() {
        return new ArrayList<>(voluntarios);
    }

    public List<Voluntario> buscarPorDisponibilidad(String disponibilidad) {
        return voluntarios.stream()
                .filter(v -> v.getDisponibilidad().contains(disponibilidad))
                .collect(Collectors.toList());
    }

    private boolean existeVoluntario(String identificacion) {
        return voluntarios.stream()
                .anyMatch(v -> v.getIdentificacion().equals(identificacion));
    }
}