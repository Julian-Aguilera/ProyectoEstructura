package co.edu.unbosque.model;

import java.util.UUID;

public class UnidadServicio {

    private UUID id;
    private TipoUnidad tipo;
    private EstadoUnidad estado;
    private String zona;
    private boolean disponible;

    public UnidadServicio(TipoUnidad tipo, String zona) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.zona = zona;
        this.estado = EstadoUnidad.ACTIVA;
        this.disponible = true;
    }

    public void asignar() {
        if (!disponible || estado == EstadoUnidad.MANTENIMIENTO) {
            throw new IllegalStateException("Unidad no disponible para asignación");
        }
        this.disponible = false;
        this.estado = EstadoUnidad.OCUPADA;
    }

    public void liberar() {
        this.disponible = true;
        this.estado = EstadoUnidad.ACTIVA;
    }

    public void enviarMantenimiento() {
        this.estado = EstadoUnidad.MANTENIMIENTO;
        this.disponible = false;
    }

    public UUID getId() {
        return id;
    }

    public TipoUnidad getTipo() {
        return tipo;
    }

    public EstadoUnidad getEstado() {
        return estado;
    }

    public String getZona() {
        return zona;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
