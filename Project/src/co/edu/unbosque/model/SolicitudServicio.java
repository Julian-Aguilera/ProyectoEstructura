package co.edu.unbosque.model;

import java.util.UUID;

public class SolicitudServicio {

    private UUID id;
    private Cliente usuario;
    private TipoServicio tipoServicio;
    private Prioridad prioridad;
    private EstadoSolicitud estado;

    private UnidadServicio unidadAsignada;
    private Tecnico tecnicoAsignado;

    public SolicitudServicio(Cliente usuario, TipoServicio tipoServicio, Prioridad prioridad) {
        this.id = UUID.randomUUID();
        this.usuario = usuario;
        this.tipoServicio = tipoServicio;
        this.prioridad = prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public void asignarRecursos(UnidadServicio unidad, Tecnico tecnico) {
        if (unidad == null || tecnico == null)
            throw new IllegalArgumentException("Debe asignar unidad y técnico");

        unidad.asignar();
        tecnico.asignar();

        this.unidadAsignada = unidad;
        this.tecnicoAsignado = tecnico;
        this.estado = EstadoSolicitud.EN_PROCESO;
    }

    public void finalizarServicio() {
        if (unidadAsignada == null || tecnicoAsignado == null)
            throw new IllegalStateException("No asignado");

        unidadAsignada.liberar();
        tecnicoAsignado.liberar();
        estado = EstadoSolicitud.FINALIZADO;
    }

    public boolean esCritica() {
        return prioridad == Prioridad.ALTA;
    }

    public UUID getId() { 
        return id; 
    }
    public Cliente getUsuario() { 
        return usuario; 
    }
    public TipoServicio getTipoServicio() { 
        return tipoServicio; 
    }
    public Prioridad getPrioridad() { 
        return prioridad; 
    }
    public EstadoSolicitud getEstado() { 
        return estado; 
    }

    @Override
    public String toString() {
        return tipoServicio + " | " + usuario.getNombre() + " | " + prioridad + " | " + estado;
    }
}
