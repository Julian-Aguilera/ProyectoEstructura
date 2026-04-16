package co.edu.unbosque.controller;

import java.util.Scanner;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.EstadoUnidad;
import co.edu.unbosque.model.Especialidad;
import co.edu.unbosque.model.Prioridad;
import co.edu.unbosque.model.TipoCliente;
import co.edu.unbosque.model.TipoServicio;
import co.edu.unbosque.model.TipoUnidad;
import co.edu.unbosque.model.Tecnico;
import co.edu.unbosque.model.UnidadServicio;
import co.edu.unbosque.model.fachada.SistemaFachada;
import co.edu.unbosque.model.SolicitudServicio;

public class Controller {

    private SistemaFachada sistema;
    private UnidadServicio[] unidades;
    private Tecnico[] tecnicos;
    private Scanner scanner;

    public Controller() {
        sistema = new SistemaFachada();
        scanner = new Scanner(System.in);
        inicializarDatosPrueba();
    }

    public void run() {
        System.out.println("=======================================");
        System.out.println(" AutoRescate 24/7 - Consola de prueba ");
        System.out.println("=======================================\n");

        mostrarResumenInicial();
        mostrarMenu();

        System.out.println("Saliendo del sistema. ¡Hasta luego!");
    }

    private void inicializarDatosPrueba() {
        unidades = new UnidadServicio[] {
            new UnidadServicio(TipoUnidad.GRUA, "Norte"),
            new UnidadServicio(TipoUnidad.MOTO, "Centro"),
            new UnidadServicio(TipoUnidad.CAMIONETA, "Sur"),
            new UnidadServicio(TipoUnidad.VEHICULO_LIVIANO, "Este")
        };

        tecnicos = new Tecnico[] {
            new Tecnico("Carlos", Especialidad.GENERAL),
            new Tecnico("Valeria", Especialidad.ELECTRICIDAD),
            new Tecnico("Santiago", Especialidad.MECANICA)
        };
    }

    private void mostrarResumenInicial() {
        System.out.println("Recursos cargados:");
        System.out.println("- Unidades de servicio disponibles: " + unidades.length);
        System.out.println("- Técnicos registrados: " + tecnicos.length);
        System.out.println("- Solicitudes pendientes: " + (sistema.hayPendientes() ? "Sí" : "No"));
        System.out.println();
    }

    private void mostrarMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menú principal");
            System.out.println("1. Registrar solicitud");
            System.out.println("2. Atender siguiente solicitud");
            System.out.println("3. Asignar recursos a la solicitud actual");
            System.out.println("4. Finalizar solicitud actual");
            System.out.println("5. Ver estado de solicitudes" );
            System.out.println("6. Ver recursos disponibles" );
            System.out.println("7. Deshacer última acción");
            System.out.println("8. Ver historial de acciones");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();
            System.out.println();

            switch (opcion) {
                case 1:
                    registrarSolicitud();
                    break;
                case 2:
                    atenderSiguienteSolicitud();
                    break;
                case 3:
                    asignarRecursos();
                    break;
                case 4:
                    finalizarSolicitudActual();
                    break;
                case 5:
                    mostrarEstadoSolicitudes();
                    break;
                case 6:
                    mostrarEstadoRecursos();
                    break;
                case 7:
                    deshacerUltimaAccion();
                    break;
                case 8:
                    mostrarHistorial();
                    break;
                case 9:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

            System.out.println();
        }
    }

    private void registrarSolicitud() {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine().trim();

        TipoCliente tipoCliente = seleccionarTipoCliente();
        TipoServicio tipoServicio = seleccionarTipoServicio();
        Prioridad prioridad = seleccionarPrioridad();

        Cliente cliente = new Cliente(nombre, tipoCliente);
        boolean registrado = sistema.registrarSolicitud(cliente, tipoServicio, prioridad);

        if (registrado) {
            System.out.println("Solicitud registrada correctamente para " + cliente.getNombre());
        } else {
            System.out.println("Error al registrar la solicitud. Revise los datos e intente de nuevo.");
        }
    }

    private TipoCliente seleccionarTipoCliente() {
        TipoCliente[] valores = TipoCliente.values();
        return seleccionarEnum("Tipo de cliente", valores);
    }

    private TipoServicio seleccionarTipoServicio() {
        TipoServicio[] valores = TipoServicio.values();
        return seleccionarEnum("Tipo de servicio", valores);
    }

    private Prioridad seleccionarPrioridad() {
        Prioridad[] valores = Prioridad.values();
        return seleccionarEnum("Prioridad", valores);
    }

    private <E extends Enum<E>> E seleccionarEnum(String etiqueta, E[] opciones) {
        System.out.println(etiqueta + ":");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        System.out.print("Selecciona una opción: ");
        int opcion = leerEntero();
        System.out.println();

        if (opcion < 1 || opcion > opciones.length) {
            System.out.println("Selección inválida. Usando opción por defecto.");
            return opciones[0];
        }
        return opciones[opcion - 1];
    }

    private void atenderSiguienteSolicitud() {
        SolicitudServicio siguiente = sistema.atenderSiguienteSolicitud();

        if (siguiente == null) {
            System.out.println("No hay solicitudes disponibles o la solicitud actual aún no ha sido finalizada.");
        } else {
            System.out.println("Atendiendo solicitud: " + siguiente);
        }
    }

    private void asignarRecursos() {
        SolicitudServicio actual = sistema.getSolicitudActual();
        if (actual == null) {
            System.out.println("No hay solicitud en curso para asignar recursos.");
            return;
        }

        UnidadServicio unidad = seleccionarUnidadDisponible();
        Tecnico tecnico = seleccionarTecnicoDisponible();

        if (unidad == null || tecnico == null) {
            System.out.println("No se pudo asignar recursos. Debe haber unidad y técnico disponibles.");
            return;
        }

        boolean asignado = sistema.asignarRecursos(unidad, tecnico);
        if (asignado) {
            System.out.println("Recursos asignados a la solicitud actual.");
        } else {
            System.out.println("Error al asignar recursos. Verifique el estado de unidad y técnico.");
        }
    }

    private UnidadServicio seleccionarUnidadDisponible() {
        System.out.println("Unidades disponibles:");
        int contador = 0;
        for (int i = 0; i < unidades.length; i++) {
            if (unidades[i].isDisponible()) {
                contador++;
                System.out.println(contador + ". " + unidades[i]);
            }
        }

        if (contador == 0) {
            System.out.println("No hay unidades disponibles.");
            return null;
        }

        System.out.print("Seleccione una unidad por número: ");
        int opcion = leerEntero();
        if (opcion < 1 || opcion > contador) {
            System.out.println("Selección inválida.");
            return null;
        }

        int indice = 0;
        for (int i = 0; i < unidades.length; i++) {
            if (unidades[i].isDisponible()) {
                indice++;
                if (indice == opcion) {
                    return unidades[i];
                }
            }
        }
        return null;
    }

    private Tecnico seleccionarTecnicoDisponible() {
        System.out.println("Técnicos disponibles:");
        int contador = 0;
        for (int i = 0; i < tecnicos.length; i++) {
            if (tecnicos[i].getEstado().name().equals("DISPONIBLE")) {
                contador++;
                System.out.println(contador + ". " + tecnicos[i].getNombre() + " - " + tecnicos[i].getEspecialidad());
            }
        }

        if (contador == 0) {
            System.out.println("No hay técnicos disponibles.");
            return null;
        }

        System.out.print("Seleccione un técnico por número: ");
        int opcion = leerEntero();
        if (opcion < 1 || opcion > contador) {
            System.out.println("Selección inválida.");
            return null;
        }

        int indice = 0;
        for (int i = 0; i < tecnicos.length; i++) {
            if (tecnicos[i].getEstado().name().equals("DISPONIBLE")) {
                indice++;
                if (indice == opcion) {
                    return tecnicos[i];
                }
            }
        }
        return null;
    }

    private void finalizarSolicitudActual() {
        boolean finalizado = sistema.finalizarSolicitudActual();
        if (finalizado) {
            System.out.println("La solicitud actual se finalizó correctamente.");
        } else {
            System.out.println("No hay solicitud actual o no se puede finalizar.");
        }
    }

    private void mostrarEstadoSolicitudes() {
        System.out.println("Solicitudes pendientes normales:");
        System.out.println(sistema.verColaNormal());
        System.out.println("Solicitudes prioritarias:");
        System.out.println(sistema.verColaPrioritaria());
        System.out.println("Solicitud actual en curso:");
        SolicitudServicio actual = sistema.getSolicitudActual();
        System.out.println(actual != null ? actual : "Ninguna");
    }

    private void mostrarEstadoRecursos() {
        System.out.println("Unidades de servicio:");
        for (UnidadServicio unidad : unidades) {
            System.out.println("- " + unidad);
        }
        System.out.println("Técnicos:");
        for (Tecnico tecnico : tecnicos) {
            System.out.println("- " + tecnico.getNombre() + " (" + tecnico.getEstado() + ")");
        }
    }

    private void deshacerUltimaAccion() {
        System.out.println(sistema.deshacerUltimaAccion());
    }

    private void mostrarHistorial() {
        System.out.println("Historial de acciones:");
        System.out.println(sistema.verHistorial());
    }

    private int leerEntero() {
        int valor = -1;
        try {
            valor = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
        }
        return valor;
    }
}
