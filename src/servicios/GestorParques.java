package servicios;

import interfaces.Gestionable;
import modelo.Parque;
import excepciones.ParqueNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorParques implements Gestionable<Parque> {
    private List<Parque> parques = new ArrayList<>();

    @Override
    public void registrar(Parque parque) {
        if (!existeParque(parque.getId())) {
            parques.add(parque);
        }
    }

    @Override
    public Parque obtenerPorId(int id) throws ParqueNoEncontradoException {
        return parques.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ParqueNoEncontradoException(id));
    }

    public Parque obtenerPorNombre(String nombre) throws ParqueNoEncontradoException {
        return parques.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new ParqueNoEncontradoException(nombre));
    }

    @Override
    public boolean actualizar(Parque parque) {
        try {
            Parque existente = obtenerPorId(parque.getId());
            existente.setNombre(parque.getNombre());
            existente.setUbicacion(parque.getUbicacion());
            existente.setTamanio(parque.getTamanio());
            return true;
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            Parque parque = obtenerPorId(id);
            return parques.remove(parque);
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public List<Parque> obtenerTodos() {
        return new ArrayList<>(parques);
    }

    public List<Parque> buscarPorUbicacion(String ubicacion) {
        return parques.stream()
                .filter(p -> p.getUbicacion().contains(ubicacion))
                .collect(Collectors.toList());
    }

    private boolean existeParque(int id) {
        return parques.stream()
                .anyMatch(p -> p.getId() == id);
    }
}