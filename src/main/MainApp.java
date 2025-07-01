package main;

import servicios.*;
import modelo.*;
import excepciones.*;
import interfaces.Reportable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.stream.Collectors;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static GestorVoluntarios gestorVoluntarios;
    private static GestorParques gestorParques;
    private static GestorActividades gestorActividades;
    private static ReporteadorActividades reporteador;

    public static void main(String[] args) {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    private static void inicializarSistema() {
        gestorVoluntarios = new GestorVoluntarios();
        gestorParques = new GestorParques();
        gestorActividades = new GestorActividades(gestorParques, gestorVoluntarios);
        reporteador = new ReporteadorActividades(gestorActividades, gestorVoluntarios);
        cargarDatosIniciales();
    }

    private static void cargarDatosIniciales() {
        try {
            // Voluntarios de prueba
            gestorVoluntarios.registrar(new Voluntario(1, "María González", "111111111", "maria@email.com", "Lunes,Martes"));
            gestorVoluntarios.registrar(new Voluntario(2, "Carlos Rojas", "222222222", "carlos@email.com", "Miércoles,Jueves"));

            // Parques de prueba
            gestorParques.registrar(new Parque(1, "Parque Central", "Centro ciudad", 5000));
            gestorParques.registrar(new Parque(2, "Parque Norte", "Zona norte", 3000));

        } catch (VoluntarioDuplicadoException e) {
            System.out.println("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE VOLUNTARIOS ===");
            System.out.println("1. Gestión de Voluntarios");
            System.out.println("2. Gestión de Parques");
            System.out.println("3. Gestión de Actividades");
            System.out.println("4. Generar Reportes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    mostrarMenuVoluntarios();
                    break;
                case 2:
                    mostrarMenuParques();
                    break;
                case 3:
                    mostrarMenuActividades();
                    break;
                case 4:
                    mostrarMenuReportes();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // ========== MÉTODOS PARA GESTIÓN DE VOLUNTARIOS ==========
    private static void mostrarMenuVoluntarios() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE VOLUNTARIOS ===");
            System.out.println("1. Registrar nuevo voluntario");
            System.out.println("2. Listar todos los voluntarios");
            System.out.println("3. Buscar voluntario por ID");
            System.out.println("4. Actualizar voluntario");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarVoluntario();
                    break;
                case 2:
                    listarVoluntarios();
                    break;
                case 3:
                    buscarVoluntarioPorId();
                    break;
                case 4:
                    actualizarVoluntario();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void registrarVoluntario() {
        System.out.println("\n--- REGISTRAR NUEVO VOLUNTARIO ---");

        int id = leerEnteroValidado("ID: ", 1, Integer.MAX_VALUE);

        System.out.print("Nombre: ");
        String nombre = leerStringNoVacio("nombre del voluntario");

        System.out.print("Identificación: ");
        String identificacion = leerStringNoVacio("identificación");

        System.out.print("Correo electrónico: ");
        String correo = leerStringNoVacio("correo electrónico");

        System.out.print("Disponibilidad (ej: Lunes,Martes): ");
        String disponibilidad = leerStringNoVacio("disponibilidad");

        try {
            Voluntario nuevo = new Voluntario(id, nombre, identificacion, correo, disponibilidad);
            gestorVoluntarios.registrar(nuevo);
            System.out.println("Voluntario registrado exitosamente!");
        } catch (VoluntarioDuplicadoException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    private static void listarVoluntarios() {
        System.out.println("\n--- LISTADO DE VOLUNTARIOS ---");
        List<Voluntario> voluntarios = gestorVoluntarios.obtenerTodos();

        if (voluntarios.isEmpty()) {
            System.out.println("No hay voluntarios registrados.");
        } else {
            voluntarios.forEach(System.out::println);
        }
    }

    private static void buscarVoluntarioPorId() {
        int id = leerEnteroValidado("\nIngrese ID del voluntario a buscar: ", 1, Integer.MAX_VALUE);

        try {
            Voluntario voluntario = gestorVoluntarios.obtenerPorId(id);
            System.out.println("\nInformación del voluntario:");
            System.out.println(voluntario);
        } catch (ParqueNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarVoluntario() {
        int id = leerEnteroValidado("\nIngrese ID del voluntario a actualizar: ", 1, Integer.MAX_VALUE);

        try {
            Voluntario existente = gestorVoluntarios.obtenerPorId(id);
            System.out.println("Voluntario actual:");
            System.out.println(existente);

            System.out.println("\nIngrese nuevos datos (dejar en blanco para mantener el valor actual):");

            System.out.print("Nombre (" + existente.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) existente.setNombre(nombre);

            System.out.print("Correo (" + existente.getCorreo() + "): ");
            String correo = scanner.nextLine();
            if (!correo.isEmpty()) existente.setCorreo(correo);

            System.out.print("Disponibilidad (" + existente.getDisponibilidad() + "): ");
            String disponibilidad = scanner.nextLine();
            if (!disponibilidad.isEmpty()) existente.setDisponibilidad(disponibilidad);

            if (gestorVoluntarios.actualizar(existente)) {
                System.out.println("Voluntario actualizado exitosamente!");
            } else {
                System.out.println("Error al actualizar voluntario.");
            }
        } catch (ParqueNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== MÉTODOS PARA GESTIÓN DE PARQUES ==========
    private static void mostrarMenuParques() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE PARQUES ===");
            System.out.println("1. Registrar nuevo parque");
            System.out.println("2. Listar todos los parques");
            System.out.println("3. Buscar parque por ID");
            System.out.println("4. Actualizar parque");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarParque();
                    break;
                case 2:
                    listarParques();
                    break;
                case 3:
                    buscarParquePorId();
                    break;
                case 4:
                    actualizarParque();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void registrarParque() {
        System.out.println("\n--- REGISTRAR NUEVO PARQUE ---");

        int id = leerEnteroValidado("ID: ", 1, Integer.MAX_VALUE);

        System.out.print("Nombre: ");
        String nombre = leerStringNoVacio("nombre del parque");

        System.out.print("Ubicación: ");
        String ubicacion = leerStringNoVacio("ubicación");

        double tamanio = leerDoubleValidado("Tamaño (m²): ", 0.1, 100000);

        Parque nuevo = new Parque(id, nombre, ubicacion, tamanio);
        gestorParques.registrar(nuevo);
        System.out.println("Parque registrado exitosamente!");
    }

    private static void listarParques() {
        System.out.println("\n--- LISTADO DE PARQUES ---");
        List<Parque> parques = gestorParques.obtenerTodos();

        if (parques.isEmpty()) {
            System.out.println("No hay parques registrados.");
        } else {
            parques.forEach(System.out::println);
        }
    }

    private static void buscarParquePorId() {
        int id = leerEnteroValidado("\nIngrese ID del parque a buscar: ", 1, Integer.MAX_VALUE);

        try {
            Parque parque = gestorParques.obtenerPorId(id);
            System.out.println("\nInformación del parque:");
            System.out.println(parque);
            System.out.println("\nActividades en este parque:");
            gestorActividades.obtenerPorParque(id).forEach(System.out::println);
        } catch (ParqueNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarParque() {
        int id = leerEnteroValidado("\nIngrese ID del parque a actualizar: ", 1, Integer.MAX_VALUE);

        try {
            Parque existente = gestorParques.obtenerPorId(id);
            System.out.println("Parque actual:");
            System.out.println(existente);

            System.out.println("\nIngrese nuevos datos (dejar en blanco para mantener el valor actual):");

            System.out.print("Nombre (" + existente.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) existente.setNombre(nombre);

            System.out.print("Ubicación (" + existente.getUbicacion() + "): ");
            String ubicacion = scanner.nextLine();
            if (!ubicacion.isEmpty()) existente.setUbicacion(ubicacion);

            System.out.print("Tamaño (" + existente.getTamanio() + "): ");
            String tamanioStr = scanner.nextLine();
            if (!tamanioStr.isEmpty()) {
                double tamanio = Double.parseDouble(tamanioStr);
                if (tamanio > 0) existente.setTamanio(tamanio);
            }

            if (gestorParques.actualizar(existente)) {
                System.out.println("Parque actualizado exitosamente!");
            } else {
                System.out.println("Error al actualizar parque.");
            }
        } catch (ParqueNoEncontradoException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== MÉTODOS PARA GESTIÓN DE ACTIVIDADES ==========
    private static void mostrarMenuActividades() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE ACTIVIDADES ===");
            System.out.println("1. Registrar nueva actividad");
            System.out.println("2. Listar todas las actividades");
            System.out.println("3. Buscar actividad por ID");
            System.out.println("4. Listar actividades por parque");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarActividad();
                    break;
                case 2:
                    listarActividades();
                    break;
                case 3:
                    buscarActividadPorId();
                    break;
                case 4:
                    listarActividadesPorParque();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void registrarActividad() {
        System.out.println("\n--- REGISTRAR NUEVA ACTIVIDAD ---");

        int id = leerEnteroValidado("ID de la actividad: ", 1, Integer.MAX_VALUE);

        System.out.print("Fecha (formato yyyy-MM-dd): ");
        Date fecha = leerFecha();

        int idParque = leerEnteroValidado("ID del parque: ", 1, Integer.MAX_VALUE);

        double duracion = leerDoubleValidado("Duración en horas: ", 0.5, 24);

        System.out.print("Descripción: ");
        String descripcion = leerStringNoVacio("descripción");

        System.out.print("IDs de voluntarios (separados por comas): ");
        List<Integer> idsVoluntarios = leerListaEnteros();

        try {
            Parque parque = gestorParques.obtenerPorId(idParque);
            ActividadLimpieza actividad = new ActividadLimpieza(id, fecha, parque, duracion, descripcion);

            gestorActividades.registrarActividadConVoluntarios(actividad, idsVoluntarios);
            System.out.println("Actividad registrada exitosamente!");
        } catch (ParqueNoEncontradoException | HorarioConflictivoException e) {
            System.out.println("Error al registrar actividad: " + e.getMessage());
        }
    }

    private static void listarActividades() {
        System.out.println("\n--- LISTADO DE ACTIVIDADES ---");
        List<ActividadLimpieza> actividades = gestorActividades.obtenerTodos();

        if (actividades.isEmpty()) {
            System.out.println("No hay actividades registradas.");
        } else {
            actividades.forEach(System.out::println);
        }
    }

    private static void buscarActividadPorId() {
        int id = leerEnteroValidado("\nIngrese ID de la actividad a buscar: ", 1, Integer.MAX_VALUE);

        try {
            ActividadLimpieza actividad = gestorActividades.obtenerPorId(id);
            System.out.println("\nInformación de la actividad:");
            System.out.println(actividad);
            System.out.println("\nVoluntarios participantes:");
            actividad.getVoluntarios().forEach(v -> System.out.println("- " + v.getNombre()));
        } catch (ParqueNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarActividadesPorParque() {
        int idParque = leerEnteroValidado("\nIngrese ID del parque: ", 1, Integer.MAX_VALUE);

        List<ActividadLimpieza> actividades = gestorActividades.obtenerPorParque(idParque);

        if (actividades.isEmpty()) {
            System.out.println("No hay actividades registradas para este parque.");
        } else {
            System.out.println("\nActividades en el parque:");
            actividades.forEach(System.out::println);
        }
    }

    // ========== MÉTODOS PARA GENERACIÓN DE REPORTES ==========
    private static void mostrarMenuReportes() {
        while (true) {
            System.out.println("\n=== GENERACIÓN DE REPORTES ===");
            System.out.println("1. Reporte de actividades por fecha");
            System.out.println("2. Reporte de participación");
            System.out.println("3. Reporte de horas trabajadas");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    generarReporteActividades();
                    break;
                case 2:
                    generarReporteParticipacion();
                    break;
                case 3:
                    generarReporteHorasTrabajadas();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void generarReporteActividades() {
        System.out.println("\n--- REPORTE DE ACTIVIDADES POR FECHA ---");
        System.out.print("Fecha inicio (yyyy-MM-dd): ");
        Date fechaInicio = leerFecha();

        System.out.print("Fecha fin (yyyy-MM-dd): ");
        Date fechaFin = leerFecha();

        Map<String, Object> reporte = reporteador.generarReporteActividades(fechaInicio, fechaFin);

        System.out.println("\nResultados del reporte:");
        reporte.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.print("\n¿Desea exportar a PDF? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Nombre del archivo (sin extensión): ");
            String nombreArchivo = scanner.nextLine();
            if (reporteador.exportarAPdf(reporte, nombreArchivo + ".pdf")) {
                System.out.println("Reporte exportado exitosamente!");
            }
        }
    }

    private static void generarReporteParticipacion() {
        System.out.println("\n--- REPORTE DE PARTICIPACIÓN ---");
        Map<String, Object> reporte = reporteador.generarReporteParticipacion();

        System.out.println("\nResultados del reporte:");
        reporte.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private static void generarReporteHorasTrabajadas() {
        System.out.println("\n--- REPORTE DE HORAS TRABAJADAS ---");
        System.out.print("ID del voluntario (opcional, dejar vacío para todos): ");
        String input = scanner.nextLine();

        Integer idVoluntario = input.isEmpty() ? null : Integer.parseInt(input);
        Map<String, Object> reporte = reporteador.generarReporteHorasTrabajadas(idVoluntario);

        System.out.println("\nResultados del reporte:");
        reporte.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    // ========== MÉTODOS AUXILIARES DE VALIDACIÓN ==========
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada no válida. Ingrese un número: ");
            }
        }
    }

    private static int leerEnteroValidado(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Por favor ingrese un valor entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número entero.");
            }
        }
    }

    private static double leerDoubleValidado(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Por favor ingrese un valor entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
            }
        }
    }

    private static String leerStringNoVacio(String campo) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("El campo " + campo + " no puede estar vacío. Intente nuevamente:");
        }
    }

    private static List<Integer> leerListaEnteros() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Arrays.stream(input.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingrese números separados por comas:");
            }
        }
    }

    private static Date leerFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                return sdf.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.print("Formato de fecha inválido. Use yyyy-MM-dd: ");
            }
        }
    }
}