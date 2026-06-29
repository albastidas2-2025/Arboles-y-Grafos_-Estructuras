package org.clasificacion;

public class NodoArbol {
    private Asignatura asignatura;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Asignatura asignatura) {
        this.asignatura = asignatura;
        this.izquierdo = null;
        this.derecho = null;
    }
    // Getters y Setters
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    public NodoArbol getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol izquierdo) { this.izquierdo = izquierdo; }
    public NodoArbol getDerecho() { return derecho; }
    public void setDerecho(NodoArbol derecho) { this.derecho = derecho; }
}
