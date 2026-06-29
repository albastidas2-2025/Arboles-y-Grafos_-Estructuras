package org.clasificacion;

public class Asignatura {
    private int codigoAsignatura;
    private String nombre;
    private int creditos;

    public Asignatura(int codigoAsignatura, String nombre, int creditos) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    // Getters y Setters
    public int getCodigoAsignatura() { return codigoAsignatura; }
    public void setCodigoAsignatura(int codigoAsignatura) { this.codigoAsignatura = codigoAsignatura; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    @Override
    public String toString() {
        return "Código: " + codigoAsignatura + ", Nombre: " + nombre + ", Créditos: " + creditos;
    }
}