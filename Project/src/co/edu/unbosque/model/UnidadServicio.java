package co.edu.unbosque.model;

import java.util.UUID;

public class UnidadServicio {

    private UUID id;
    private TipoUnidad tipo;
    private EstadoUnidad estado;
    private String zona;

    public UnidadServicio(TipoUnidad tipo, String zona) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.zona = zona;
        this.estado = EstadoUnidad.ACTIVA;
    }

    public void asignar() {
        if (estado != EstadoUnidad.ACTIVA) {
            throw new IllegalStateException("Unidad no disponible");
        }
        estado = EstadoUnidad.OCUPADA;
    }

    public void liberar() {
        estado = EstadoUnidad.ACTIVA;
    }

    public void enviarMantenimiento() {
        estado = EstadoUnidad.MANTENIMIENTO;
    }

    public boolean isDisponible() {
        return estado == EstadoUnidad.ACTIVA;
    }

    public UUID getId() { return id; }
    public TipoUnidad getTipo() { return tipo; }
    public EstadoUnidad getEstado() { return estado; }
    public String getZona() { return zona; }

    @Override
    public String toString() {
        return tipo + " - " + zona + " (" + estado + ")";
    }
}
