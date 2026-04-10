package co.edu.unbosque.model;

import java.util.UUID;

public class Kit {

    private UUID id;
    private boolean completo;
    private boolean enRevision;

    public Kit() {
        this.id = UUID.randomUUID();
        this.completo = true;
        this.enRevision = false;
    }

    public void enviarRevision() {
        this.enRevision = true;
    }

    public void marcarCompleto(boolean estado) {
        this.completo = estado;
    }

    public boolean isDisponible() {
        return completo && !enRevision;
    }

    public UUID getId() {
        return id;
    }
}
