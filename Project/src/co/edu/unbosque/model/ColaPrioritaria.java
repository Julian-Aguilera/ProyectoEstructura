package co.edu.unbosque.model;

public class ColaPrioritaria <T>{
    private Cola<T> alta;
    private Cola<T> media;
    private Cola<T> baja;

    public ColaPrioritaria() {
        alta = new Cola<>();
        media = new Cola<>();
        baja = new Cola<>();
    }

    public void enqueue(T dato, Prioridad prioridad) {

        if (prioridad == Prioridad.ALTA) {
            alta.enqueue(dato);
        } else if (prioridad == Prioridad.MEDIA) {
            media.enqueue(dato);
        } else {
            baja.enqueue(dato);
        }
    }

    public T dequeue() {
        if (!alta.estaVacia()) {
            return alta.dequeue();
        } else if (!media.estaVacia()) {
            return media.dequeue();
        } else if (!baja.estaVacia()) {
            return baja.dequeue();
        } else {
            return null;
        }
    }

    public boolean estaVacia() {
        return alta.estaVacia() && media.estaVacia() && baja.estaVacia();
    }

    public String mostrar() {
        return "=== PRIORIDAD ALTA ===\n" + alta.mostrar() +
               "\n=== PRIORIDAD MEDIA ===\n" + media.mostrar() +
               "\n=== PRIORIDAD BAJA ===\n" + baja.mostrar();
    }
}
