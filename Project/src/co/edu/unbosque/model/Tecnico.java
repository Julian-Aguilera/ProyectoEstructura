package co.edu.unbosque.model;

import java.util.UUID;

public class Tecnico {
    private UUID id;
    private String nombre;
    private Especialidad especialidad;
    private EstadoTecnico estado;

    public Tecnico(String nombre, Especialidad especialidad) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.estado = EstadoTecnico.DISPONIBLE;
    }

    public void asignar() {
        if (estado == EstadoTecnico.OCUPADO) {
            throw new IllegalStateException("El técnico ya está ocupado");
        }
        this.estado = EstadoTecnico.OCUPADO;
    }

    public void liberar() {
        this.estado = EstadoTecnico.DISPONIBLE;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public EstadoTecnico getEstado() {
        return estado;
    }
}
