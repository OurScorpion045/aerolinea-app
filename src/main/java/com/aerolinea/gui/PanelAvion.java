package com.aerolinea.gui;

import com.aerolinea.dao.AvionDAO;
import com.aerolinea.modelo.Avion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PanelAvion extends JPanel {
    private final AvionDAO avionDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final JTextField txtIdAvion = new JTextField(10);
    private final JTextField txtModelo = new JTextField(10);
    private final JTextField txtFabricante = new JTextField(10);
    private final JTextField txtCapacidad = new JTextField(10);
    private final JTextField txtAnioFabricacion = new JTextField(10);

    public PanelAvion(Connection conexion) {
        this.avionDAO = new AvionDAO(conexion);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Avión"));

        panelFormulario.add(new JLabel("ID Avión:"));
        panelFormulario.add(txtIdAvion);
        txtIdAvion.setEditable(false);  // Solo para visualización

        panelFormulario.add(new JLabel("Modelo:"));
        panelFormulario.add(txtModelo);

        panelFormulario.add(new JLabel("Fabricante:"));
        panelFormulario.add(txtFabricante);

        panelFormulario.add(new JLabel("Capacidad:"));
        panelFormulario.add(txtCapacidad);

        panelFormulario.add(new JLabel("Año Fabricación:"));
        panelFormulario.add(txtAnioFabricacion);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Modelo", "Fabricante", "Capacidad", "Año"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Agregar componentes al panel principal
        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Cargar datos
        cargarDatos();

        // Listeners
        btnAgregar.addActionListener(e -> agregarAvion());
        btnActualizar.addActionListener(e -> actualizarAvion());
        btnEliminar.addActionListener(e -> eliminarAvion());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarDatosSeleccionados();
        });
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Avion> lista = avionDAO.obtenerTodos();
            for (Avion a : lista) {
                modeloTabla.addRow(new Object[]{
                        a.getIdAvion(),
                        a.getModelo(),
                        a.getFabricante(),
                        a.getCapacidad(),
                        a.getAnioFabricacion()
                });
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar los aviones: " + ex.getMessage());
        }
    }

    private void agregarAvion() {
        try {
            String modelo = txtModelo.getText();
            String fabricante = txtFabricante.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            int anio = Integer.parseInt(txtAnioFabricacion.getText());

            Avion avion = new Avion(0, modelo, fabricante, capacidad, anio);
            avionDAO.insertar(avion);
            cargarDatos();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al agregar avión: " + ex.getMessage());
        }
    }

    private void actualizarAvion() {
        try {
            int id = Integer.parseInt(txtIdAvion.getText());
            String modelo = txtModelo.getText();
            String fabricante = txtFabricante.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            int anio = Integer.parseInt(txtAnioFabricacion.getText());

            Avion avion = new Avion(id, modelo, fabricante, capacidad, anio);
            avionDAO.actualizar(avion);
            cargarDatos();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al actualizar avión: " + ex.getMessage());
        }
    }

    private void eliminarAvion() {
        try {
            int id = Integer.parseInt(txtIdAvion.getText());
            avionDAO.eliminar(id);
            cargarDatos();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al eliminar avión: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIdAvion.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtModelo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtFabricante.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCapacidad.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtAnioFabricacion.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtIdAvion.setText("");
        txtModelo.setText("");
        txtFabricante.setText("");
        txtCapacidad.setText("");
        txtAnioFabricacion.setText("");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}