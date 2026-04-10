package co.edu.unbosque.model;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nombre;
    private TipoCliente tipo;

    public Usuario(String nombre, TipoCliente tipo) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
