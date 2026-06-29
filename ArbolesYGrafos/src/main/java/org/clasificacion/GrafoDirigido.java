package org.clasificacion;

import java.util.*;

public class GrafoDirigido {
    private Map<String, Integer> indiceVertices;
    private List<String> vertices;
    private int[][] matrizAdyacencia;
    private int tamaño;

    public GrafoDirigido() {
        this.indiceVertices = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.matrizAdyacencia = new int[10][10]; // Tamaño inicial
        this.tamaño = 0;
    }
    public boolean agregarVertice(String nombreVertice) {
        if (indiceVertices.containsKey(nombreVertice)) {
            return false; // Vértice ya existe
        }
        if (tamaño == matrizAdyacencia.length) {
            expandirMatriz();
        }
        indiceVertices.put(nombreVertice, tamaño);
        vertices.add(nombreVertice);
        tamaño++;
        return true;
    }
    private void expandirMatriz() {
        int nuevoTamaño = matrizAdyacencia.length * 2;
        int[][] nuevaMatriz = new int[nuevoTamaño][nuevoTamaño];
        for (int i = 0; i < tamaño; i++) {
            System.arraycopy(matrizAdyacencia[i], 0, nuevaMatriz[i], 0, tamaño);
        }
        matrizAdyacencia = nuevaMatriz;
    }
    public boolean agregarAristaDirigida(String origen, String destino, int peso) {
        if (!indiceVertices.containsKey(origen) || !indiceVertices.containsKey(destino)) {
            return false; // Uno de los vértices no existe
        }
        int idxOrigen = indiceVertices.get(origen);
        int idxDestino = indiceVertices.get(destino);
        matrizAdyacencia[idxOrigen][idxDestino] = peso;
        return true;
    }
    public boolean verificarConectividad(String origen, String destino) {
        if (!indiceVertices.containsKey(origen) || !indiceVertices.containsKey(destino)) {
            return false;
        }
        int idxOrigen = indiceVertices.get(origen);
        int idxDestino = indiceVertices.get(destino);
        // BFS para verificar conectividad
        boolean[] visitados = new boolean[tamaño];
        Queue<Integer> cola = new LinkedList<>();
        visitados[idxOrigen] = true;
        cola.add(idxOrigen);
        while (!cola.isEmpty()) {
            int actual = cola.poll();

            if (actual == idxDestino) {
                return true;
            }
            for (int i = 0; i < tamaño; i++) {
                if (matrizAdyacencia[actual][i] > 0 && !visitados[i]) {
                    visitados[i] = true;
                    cola.add(i);
                }
            }
        }

        return false;
    }
    // Métodos para obtener información del grafo
    public List<String> getVertices() { return vertices; }

    public int[][] getMatrizAdyacencia() {
        int[][] copia = new int[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            System.arraycopy(matrizAdyacencia[i], 0, copia[i], 0, tamaño);
        }
        return copia;
    }
    public int getTamaño() { return tamaño; }
    public boolean existeVertice(String nombre) {
        return indiceVertices.containsKey(nombre);
    }
    // Método para limpiar el grafo
    public void limpiar() {
        indiceVertices.clear();
        vertices.clear();
        matrizAdyacencia = new int[10][10];
        tamaño = 0;
    }
}
