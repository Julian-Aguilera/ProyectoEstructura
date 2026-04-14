package co.edu.unbosque.model.fachada;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.Cola;
import co.edu.unbosque.model.ColaPrioritaria;
import co.edu.unbosque.model.EstadoSolicitud;
import co.edu.unbosque.model.Pila;
import co.edu.unbosque.model.Prioridad;
import co.edu.unbosque.model.SolicitudServicio;
import co.edu.unbosque.model.Tecnico;
import co.edu.unbosque.model.TipoServicio;
import co.edu.unbosque.model.UnidadServicio;

public class SistemaFachada {
    private Cola<SolicitudServicio> colaSolicitudes;
    private ColaPrioritaria<SolicitudServicio> colaPrioritaria;
    private Pila<String> historialAcciones;

    private SolicitudServicio solicitudActual;

    public SistemaFachada() {
        colaSolicitudes = new Cola<>();
        colaPrioritaria = new ColaPrioritaria<>();
        historialAcciones = new Pila<>();
        solicitudActual = null;
    }

    public boolean registrarSolicitud(Cliente cliente, TipoServicio tipoServicio, Prioridad prioridad) {

        if (cliente == null || tipoServicio == null || prioridad == null) {
            return false;
        }

        SolicitudServicio nueva = new SolicitudServicio(cliente, tipoServicio, prioridad);

        if (prioridad == Prioridad.ALTA) {
            colaPrioritaria.enqueue(nueva, prioridad);
        } else {
            colaSolicitudes.enqueue(nueva);
        }

        historialAcciones.push("Se registró solicitud " + nueva.getId());

        return true;
    }

    public SolicitudServicio atenderSiguienteSolicitud() {

        if (solicitudActual != null &&
            solicitudActual.getEstado() != EstadoSolicitud.FINALIZADO) {
            return null;
        }

        if (!colaPrioritaria.estaVacia()) {
            solicitudActual = colaPrioritaria.dequeue();
        } else if (!colaSolicitudes.estaVacia()) {
            solicitudActual = colaSolicitudes.dequeue();
        } else {
            solicitudActual = null;
        }

        if (solicitudActual != null) {
            historialAcciones.push("Se atendió solicitud " + solicitudActual.getId());
        }

        return solicitudActual;
    }

    public boolean asignarRecursos(UnidadServicio unidad, Tecnico tecnico) {

        if (solicitudActual == null) {
            return false;
        }

        try {
            solicitudActual.asignarRecursos(unidad, tecnico);
            historialAcciones.push("Se asignaron recursos a " + solicitudActual.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean finalizarSolicitudActual() {

        if (solicitudActual == null) {
            return false;
        }

        try {
            solicitudActual.finalizarServicio();
            historialAcciones.push("Se finalizó solicitud " + solicitudActual.getId());

            solicitudActual = null;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String deshacerUltimaAccion() {

        if (historialAcciones.estaVacia()) {
            return "No hay acciones para deshacer.";
        }

        return "Acción deshecha: " + historialAcciones.pop();
    }

    public SolicitudServicio getSolicitudActual() {
        return solicitudActual;
    }

    public String verColaNormal() {
        return colaSolicitudes.mostrar();
    }

    public String verColaPrioritaria() {
        return colaPrioritaria.mostrar();
    }

    public String verHistorial() {
        return historialAcciones.mostrar();
    }

    public boolean hayPendientes() {
        return !colaSolicitudes.estaVacia() || !colaPrioritaria.estaVacia();
    }
}
