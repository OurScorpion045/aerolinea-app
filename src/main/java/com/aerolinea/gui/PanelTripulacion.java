package com.aerolinea.gui;

import com.aerolinea.dao.TripulacionDAO;
import com.aerolinea.modelo.Tripulacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PanelTripulacion extends JPanel {
    private final TripulacionDAO tripulacionDAO;
    private JTextField txtIdVuelo, txtIdEmpleado, txtRol;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PanelTripulacion(Connection conexion) {
        this.tripulacionDAO = new TripulacionDAO(conexion);
        setLayout(new BorderLayout());

        // Panel superior con campos de entrada
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Tripulación"));

        txtIdVuelo = new JTextField();
        txtIdEmpleado = new JTextField();
        txtRol = new JTextField();

        panelFormulario.add(new JLabel("ID Vuelo:"));
        panelFormulario.add(txtIdVuelo);
        panelFormulario.add(new JLabel("ID Empleado:"));
        panelFormulario.add(txtIdEmpleado);
        panelFormulario.add(new JLabel("Rol:"));
        panelFormulario.add(txtRol);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID Vuelo", "ID Empleado", "Rol"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        cargarDatos();

        // Eventos
        btnAgregar.addActionListener(e -> agregarTripulacion());
        btnActualizar.addActionListener(e -> actualizarTripulacion());
        btnEliminar.addActionListener(e -> eliminarTripulacion());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                txtIdVuelo.setText(tabla.getValueAt(fila, 0).toString());
                txtIdEmpleado.setText(tabla.getValueAt(fila, 1).toString());
                txtRol.setText(tabla.getValueAt(fila, 2).toString());
            }
        });
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Tripulacion> lista = tripulacionDAO.obtenerTodos();
            for (Tripulacion t : lista) {
                modeloTabla.addRow(new Object[]{t.getIdVuelo(), t.getIdEmpleado(), t.getRol()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar tripulaciones: " + ex.getMessage());
        }
    }

    private void agregarTripulacion() {
        try {
            int idVuelo = Integer.parseInt(txtIdVuelo.getText());
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            String rol = txtRol.getText();
            Tripulacion tripulacion = new Tripulacion(idVuelo, idEmpleado, rol);
            tripulacionDAO.insertar(tripulacion);
            cargarDatos();
            limpiarCampos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar tripulación: " + ex.getMessage());
        }
    }

    private void actualizarTripulacion() {
        try {
            int idVuelo = Integer.parseInt(txtIdVuelo.getText());
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            String rol = txtRol.getText();
            Tripulacion tripulacion = new Tripulacion(idVuelo, idEmpleado, rol);
            tripulacionDAO.actualizar(tripulacion);
            cargarDatos();
            limpiarCampos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar tripulación: " + ex.getMessage());
        }
    }

    private void eliminarTripulacion() {
        try {
            int idVuelo = Integer.parseInt(txtIdVuelo.getText());
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            tripulacionDAO.eliminar(idVuelo, idEmpleado);
            cargarDatos();
            limpiarCampos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar tripulación: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtIdVuelo.setText("");
        txtIdEmpleado.setText("");
        txtRol.setText("");
        tabla.clearSelection();
    }
}