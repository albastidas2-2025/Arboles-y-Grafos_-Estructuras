package org.clasificacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SistemaAcademicoGUI extends JFrame {
    private ArbolBinarioBusqueda arbol;
    private GrafoDirigido grafo;

    // Componentes para ABB
    private JTextField txtCodigo, txtNombre, txtCreditos;
    private JTextArea txtInorden, txtPreorden, txtPostorden;
    private JButton btnInsertar;

    // Componentes para Grafo
    private JTextField txtVertice, txtOrigen, txtDestino, txtPeso;
    private JTable tablaMatriz;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarVertice, btnAgregarArista, btnVerificarConexion;
    private JTextField txtOrigenConexion, txtDestinoConexion;

    public SistemaAcademicoGUI() {
        arbol = new ArbolBinarioBusqueda();
        grafo = new GrafoDirigido();

        setTitle("Sistema de Simulación de Enrutamiento Académico");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Gestión de Árbol (ABB)", crearPanelArbol());
        tabbedPane.addTab("Simulación de Grafo Dirigido", crearPanelGrafo());

        add(tabbedPane);
    }

    private JPanel crearPanelArbol() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos de Asignatura"));

        panelEntrada.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelEntrada.add(txtCodigo);

        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Créditos:"));
        txtCreditos = new JTextField();
        panelEntrada.add(txtCreditos);

        btnInsertar = new JButton("Insertar Asignatura");
        panelEntrada.add(btnInsertar);

        JButton btnLimpiar = new JButton("Limpiar Árbol");
        panelEntrada.add(btnLimpiar);

        // Panel de recorridos
        JPanel panelRecorridos = new JPanel(new GridLayout(1, 3, 10, 10));
        panelRecorridos.setBorder(BorderFactory.createTitledBorder("Recorridos del Árbol"));

        txtInorden = new JTextArea(10, 20);
        txtInorden.setEditable(false);
        txtInorden.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollInorden = new JScrollPane(txtInorden);
        scrollInorden.setBorder(BorderFactory.createTitledBorder("Inorden"));

        txtPreorden = new JTextArea(10, 20);
        txtPreorden.setEditable(false);
        txtPreorden.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPreorden = new JScrollPane(txtPreorden);
        scrollPreorden.setBorder(BorderFactory.createTitledBorder("Preorden"));

        txtPostorden = new JTextArea(10, 20);
        txtPostorden.setEditable(false);
        txtPostorden.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPostorden = new JScrollPane(txtPostorden);
        scrollPostorden.setBorder(BorderFactory.createTitledBorder("Postorden"));

        panelRecorridos.add(scrollInorden);
        panelRecorridos.add(scrollPreorden);
        panelRecorridos.add(scrollPostorden);

        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(panelRecorridos, BorderLayout.CENTER);

        // Eventos
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarAsignatura();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarArbol();
            }
        });

        return panel;
    }

    private JPanel crearPanelGrafo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel de control superior
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));

        // Panel de vértices
        JPanel panelVertices = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVertices.setBorder(BorderFactory.createTitledBorder("Agregar Vértice"));
        panelVertices.add(new JLabel("Nombre:"));
        txtVertice = new JTextField(15);
        panelVertices.add(txtVertice);
        btnAgregarVertice = new JButton("Agregar Vértice");
        panelVertices.add(btnAgregarVertice);

        // Panel de aristas
        JPanel panelAristas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAristas.setBorder(BorderFactory.createTitledBorder("Agregar Arista"));
        panelAristas.add(new JLabel("Origen:"));
        txtOrigen = new JTextField(10);
        panelAristas.add(txtOrigen);
        panelAristas.add(new JLabel("Destino:"));
        txtDestino = new JTextField(10);
        panelAristas.add(txtDestino);
        panelAristas.add(new JLabel("Peso:"));
        txtPeso = new JTextField(5);
        panelAristas.add(txtPeso);
        btnAgregarArista = new JButton("Agregar Arista");
        panelAristas.add(btnAgregarArista);

        panelSuperior.add(panelVertices);
        panelSuperior.add(panelAristas);

        // Matriz de adyacencia
        modeloTabla = new DefaultTableModel();
        tablaMatriz = new JTable(modeloTabla);
        JScrollPane scrollMatriz = new JScrollPane(tablaMatriz);
        scrollMatriz.setBorder(BorderFactory.createTitledBorder("Matriz de Adyacencia"));

        // Panel de consulta
        JPanel panelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConsulta.setBorder(BorderFactory.createTitledBorder("Verificar Conectividad"));
        panelConsulta.add(new JLabel("Origen:"));
        txtOrigenConexion = new JTextField(10);
        panelConsulta.add(txtOrigenConexion);
        panelConsulta.add(new JLabel("Destino:"));
        txtDestinoConexion = new JTextField(10);
        panelConsulta.add(txtDestinoConexion);
        btnVerificarConexion = new JButton("Verificar Conexión");
        panelConsulta.add(btnVerificarConexion);

        JButton btnLimpiarGrafo = new JButton("Limpiar Grafo");
        panelConsulta.add(btnLimpiarGrafo);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollMatriz, BorderLayout.CENTER);
        panel.add(panelConsulta, BorderLayout.SOUTH);

        // Eventos
        btnAgregarVertice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVertice();
            }
        });

        btnAgregarArista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarArista();
            }
        });

        btnVerificarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarConexion();
            }
        });

        btnLimpiarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarGrafo();
            }
        });

        return panel;
    }

    // Métodos para ABB
    private void insertarAsignatura() {
        try {
            String codigoStr = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String creditosStr = txtCreditos.getText().trim();

            if (codigoStr.isEmpty() || nombre.isEmpty() || creditosStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(codigoStr);
            int creditos = Integer.parseInt(creditosStr);

            Asignatura nueva = new Asignatura(codigo, nombre, creditos);

            if (arbol.insertarAsignatura(nueva)) {
                actualizarRecorridos();
                limpiarCamposABB();
                JOptionPane.showMessageDialog(this,
                        "Asignatura insertada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ya existe una asignatura con el código " + codigo,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El código y los créditos deben ser números válidos",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarRecorridos() {
        // Inorden
        List<Asignatura> inorden = arbol.recorrerInorden();
        txtInorden.setText(formatearLista(inorden));

        // Preorden
        List<Asignatura> preorden = arbol.recorrerPreorden();
        txtPreorden.setText(formatearLista(preorden));

        // Postorden
        List<Asignatura> postorden = arbol.recorrerPostorden();
        txtPostorden.setText(formatearLista(postorden));
    }

    private String formatearLista(List<Asignatura> lista) {
        if (lista.isEmpty()) return "Árbol vacío";

        StringBuilder sb = new StringBuilder();
        for (Asignatura a : lista) {
            sb.append(a.toString()).append("\n");
        }
        return sb.toString();
    }

    private void limpiarCamposABB() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCreditos.setText("");
        txtCodigo.requestFocus();
    }

    private void limpiarArbol() {
        arbol = new ArbolBinarioBusqueda();
        actualizarRecorridos();
        JOptionPane.showMessageDialog(this,
                "Árbol limpiado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Métodos para Grafo
    private void agregarVertice() {
        String nombre = txtVertice.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre del vértice es obligatorio",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (grafo.agregarVertice(nombre)) {
            actualizarMatriz();
            txtVertice.setText("");
            JOptionPane.showMessageDialog(this,
                    "Vértice agregado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "El vértice '" + nombre + "' ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarArista() {
        try {
            String origen = txtOrigen.getText().trim();
            String destino = txtDestino.getText().trim();
            String pesoStr = txtPeso.getText().trim();

            if (origen.isEmpty() || destino.isEmpty() || pesoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int peso = Integer.parseInt(pesoStr);

            if (grafo.agregarAristaDirigida(origen, destino, peso)) {
                actualizarMatriz();
                txtOrigen.setText("");
                txtDestino.setText("");
                txtPeso.setText("");
                JOptionPane.showMessageDialog(this,
                        "Arista agregada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Los vértices origen y/o destino no existen",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El peso debe ser un número válido",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarConexion() {
        String origen = txtOrigenConexion.getText().trim();
        String destino = txtDestinoConexion.getText().trim();

        if (origen.isEmpty() || destino.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese ambos vértices para verificar la conexión",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!grafo.existeVertice(origen)) {
            JOptionPane.showMessageDialog(this,
                    "El vértice origen '" + origen + "' no existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!grafo.existeVertice(destino)) {
            JOptionPane.showMessageDialog(this,
                    "El vértice destino '" + destino + "' no existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean conectado = grafo.verificarConectividad(origen, destino);
        if (conectado) {
            JOptionPane.showMessageDialog(this,
                    "✓ EXISTE conexión entre '" + origen + "' y '" + destino + "'",
                    "Conexión Encontrada",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "✗ NO EXISTE conexión entre '" + origen + "' y '" + destino + "'",
                    "Sin Conexión",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    private void actualizarMatriz() {
        int tamaño = grafo.getTamaño();
        List<String> vertices = grafo.getVertices();
        int[][] matriz = grafo.getMatrizAdyacencia();
        // Limpiar modelo
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        if (tamaño == 0) {
            return;
        }
        // Configurar columnas
        modeloTabla.addColumn("Origen/Destino");
        for (String v : vertices) {
            modeloTabla.addColumn(v);
        }
        // Agregar filas
        for (int i = 0; i < tamaño; i++) {
            Object[] fila = new Object[tamaño + 1];
            fila[0] = vertices.get(i);
            for (int j = 0; j < tamaño; j++) {
                fila[j + 1] = matriz[i][j] > 0 ? matriz[i][j] : "-";
            }
            modeloTabla.addRow(fila);
        }
    }
    private void limpiarGrafo() {
        grafo.limpiar();
        actualizarMatriz();
        txtVertice.setText("");
        txtOrigen.setText("");
        txtDestino.setText("");
        txtPeso.setText("");
        txtOrigenConexion.setText("");
        txtDestinoConexion.setText("");

        JOptionPane.showMessageDialog(this,
                "Grafo limpiado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }
}