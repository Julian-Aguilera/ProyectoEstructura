package co.edu.unbosque.model;

import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nombre;
    private TipoCliente tipo;

    public Cliente(String nombre, TipoCliente tipo) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public TipoCliente getTipo() { return tipo; }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
