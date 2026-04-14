package co.edu.unbosque.model;

public class Pila <T> {
    private Nodo<T> cima;

    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
    }

    public T pop() {
        if (estaVacia()) return null;

        T dato = cima.getDato();
        cima = cima.getSiguiente();
        return dato;
    }

    public T peek() {
        if (cima == null) {
        return null;
    }
    return cima.getDato();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public String mostrar() {
        if (cima == null) {
        return "Pila vacía";
    }

    String salida = "";
    Nodo<T> aux = cima;

    while (aux != null) {
        salida += aux.getDato() + "\n";
        aux = aux.getSiguiente();
    }

    return salida;
    }
}
