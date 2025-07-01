package servicios;

import interfaces.Gestionable;
import modelo.ActividadLimpieza;
import modelo.Parque;
import modelo.Voluntario;
import excepciones.HorarioConflictivoException;
import excepciones.ParqueNoEncontradoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GestorActividades implements Gestionable<ActividadLimpieza> {
    private List<ActividadLimpieza> actividades = new ArrayList<>();
    private final GestorParques gestorParques;
    private final GestorVoluntarios gestorVoluntarios;

    public GestorActividades(GestorParques gestorParques, GestorVoluntarios gestorVoluntarios) {
        this.gestorParques = gestorParques;
        this.gestorVoluntarios = gestorVoluntarios;
    }

    @Override
    public void registrar(ActividadLimpieza actividad) {
        actividades.add(actividad);
    }

    public void registrarActividadConVoluntarios(ActividadLimpieza actividad, List<Integer> idsVoluntarios)
            throws ParqueNoEncontradoException, HorarioConflictivoException {

        Parque parque = gestorParques.obtenerPorId(actividad.getParque().getId());
        actividad.setParque(parque);

        for (int idVoluntario : idsVoluntarios) {
            Voluntario voluntario = gestorVoluntarios.obtenerPorId(idVoluntario);
            if (tieneConflictoHorario(voluntario, actividad.getFecha())) {
                throw new HorarioConflictivoException(
                        voluntario.getId(),
                        actividad.getFecha(),
                        actividad.getParque().getNombre()
                );
            }
            actividad.agregarVoluntario(voluntario);
        }

        actividades.add(actividad);
        parque.agregarActividad(actividad);
    }

    @Override
    public ActividadLimpieza obtenerPorId(int id) throws ParqueNoEncontradoException {
        return actividades.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ParqueNoEncontradoException(id));
    }

    @Override
    public boolean actualizar(ActividadLimpieza actividad) {
        try {
            ActividadLimpieza existente = obtenerPorId(actividad.getId());
            existente.setFecha(actividad.getFecha());
            existente.setDuracionHoras(actividad.getDuracionHoras());
            existente.setDescripcion(actividad.getDescripcion());
            return true;
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            ActividadLimpieza actividad = obtenerPorId(id);
            return actividades.remove(actividad);
        } catch (ParqueNoEncontradoException e) {
            return false;
        }
    }

    @Override
    public List<ActividadLimpieza> obtenerTodos() {
        return new ArrayList<>(actividades);
    }

    public List<ActividadLimpieza> obtenerPorParque(int idParque) {
        return actividades.stream()
                .filter(a -> a.getParque().getId() == idParque)
                .collect(Collectors.toList());
    }

    public List<ActividadLimpieza> obtenerPorFecha(Date fecha) {
        return actividades.stream()
                .filter(a -> a.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    private boolean tieneConflictoHorario(Voluntario voluntario, Date fecha) {
        return actividades.stream()
                .anyMatch(a -> a.getVoluntarios().contains(voluntario) &&
                        a.getFecha().equals(fecha));
    }
}