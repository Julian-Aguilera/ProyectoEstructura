package co.edu.unbosque.model;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;

    public Cola() {
        frente = null;
        fin = null;
    }

    public void enqueue(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public T dequeue() {
        if (estaVacia()) {
            return null;
        }

        T dato = frente.getDato();
        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public Nodo<T> getFrente() {
        return frente;
    }

    public String mostrar() {
        if (frente == null) {
            return "Cola vacía";
        }

        String salida = "";
        Nodo<T> aux = frente;

        while (aux != null) {
            salida += aux.getDato() + "\n";
            aux = aux.getSiguiente();
        }

        return salida;
    }
}
