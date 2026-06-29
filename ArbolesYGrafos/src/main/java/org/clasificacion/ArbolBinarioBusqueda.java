package org.clasificacion;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioBusqueda {
    private NodoArbol raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }
    // Insertar asignatura de forma recursiva
    public boolean insertarAsignatura(Asignatura nueva) {
        if (buscarPorCodigo(nueva.getCodigoAsignatura()) != null) {
            return false; // Código duplicado
        }
        raiz = insertarRecursivo(raiz, nueva);
        return true;
    }
    private NodoArbol insertarRecursivo(NodoArbol nodo, Asignatura nueva) {
        if (nodo == null) {
            return new NodoArbol(nueva);
        }
        if (nueva.getCodigoAsignatura() < nodo.getAsignatura().getCodigoAsignatura()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), nueva));
        } else if (nueva.getCodigoAsignatura() > nodo.getAsignatura().getCodigoAsignatura()) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), nueva));
        }
        return nodo;
    }
    // Buscar por código
    public Asignatura buscarPorCodigo(int codigo) {
        return buscarRecursivo(raiz, codigo);
    }
    private Asignatura buscarRecursivo(NodoArbol nodo, int codigo) {
        if (nodo == null) return null;

        if (codigo == nodo.getAsignatura().getCodigoAsignatura()) {
            return nodo.getAsignatura();
        } else if (codigo < nodo.getAsignatura().getCodigoAsignatura()) {
            return buscarRecursivo(nodo.getIzquierdo(), codigo);
        } else {
            return buscarRecursivo(nodo.getDerecho(), codigo);
        }
    }
    // Recorridos
    public List<Asignatura> recorrerInorden() {
        List<Asignatura> lista = new ArrayList<>();
        inordenRecursivo(raiz, lista);
        return lista;
    }
    private void inordenRecursivo(NodoArbol nodo, List<Asignatura> lista) {
        if (nodo != null) {
            inordenRecursivo(nodo.getIzquierdo(), lista);
            lista.add(nodo.getAsignatura());
            inordenRecursivo(nodo.getDerecho(), lista);
        }
    }
    public List<Asignatura> recorrerPreorden() {
        List<Asignatura> lista = new ArrayList<>();
        preordenRecursivo(raiz, lista);
        return lista;
    }
    private void preordenRecursivo(NodoArbol nodo, List<Asignatura> lista) {
        if (nodo != null) {
            lista.add(nodo.getAsignatura());
            preordenRecursivo(nodo.getIzquierdo(), lista);
            preordenRecursivo(nodo.getDerecho(), lista);
        }
    }
    public List<Asignatura> recorrerPostorden() {
        List<Asignatura> lista = new ArrayList<>();
        postordenRecursivo(raiz, lista);
        return lista;
    }
    private void postordenRecursivo(NodoArbol nodo, List<Asignatura> lista) {
        if (nodo != null) {
            postordenRecursivo(nodo.getIzquierdo(), lista);
            postordenRecursivo(nodo.getDerecho(), lista);
            lista.add(nodo.getAsignatura());
        }
    }
    // Método para obtener todas las asignaturas
    public List<Asignatura> obtenerTodas() {
        return recorrerInorden();
    }
}