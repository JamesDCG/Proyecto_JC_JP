package interfaces;

import java.util.Date;
import java.util.Map;

/**
 * Interface para generación de reportes
 */
public interface Reportable {
    /**
     * Genera un reporte de actividades por rango de fechas
     * @param fechaInicio Fecha de inicio del reporte
     * @param fechaFin Fecha de fin del reporte
     * @return Mapa con estadísticas de actividades
     */
    Map<String, Object> generarReporteActividades(Date fechaInicio, Date fechaFin);

    /**
     * Genera un reporte de participación de voluntarios
     * @return Mapa con estadísticas de participación
     */
    Map<String, Object> generarReporteParticipacion();

    /**
     * Genera un reporte de horas trabajadas por voluntario
     * @param idVoluntario Identificador del voluntario (opcional)
     * @return Mapa con las horas trabajadas
     */
    Map<String, Object> generarReporteHorasTrabajadas(Integer idVoluntario);

    /**
     * Exporta un reporte a formato PDF
     * @param datosReporte Datos a exportar
     * @param nombreArchivo Nombre del archivo de salida
     * @return true si la exportación fue exitosa
     */
    boolean exportarAPdf(Map<String, Object> datosReporte, String nombreArchivo);
}