package com.aerolinea.gui;

import com.aerolinea.dao.AeropuertoDAO;
import com.aerolinea.modelo.Aeropuerto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class PanelAeropuertos extends JPanel {
    private final AeropuertoDAO aeropuertoDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtCiudad = new JTextField();
    private final JTextField txtPais = new JTextField();

    public PanelAeropuertos(Connection conexion) {
        this.aeropuertoDAO = new AeropuertoDAO(conexion);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Ciudad", "País"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel formulario = new JPanel(new GridLayout(3, 2));
        formulario.add(new JLabel("Nombre:")); formulario.add(txtNombre);
        formulario.add(new JLabel("Ciudad:")); formulario.add(txtCiudad);
        formulario.add(new JLabel("País:")); formulario.add(txtPais);
        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        add(botones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        cargarAeropuertos();
    }

    private void cargarAeropuertos() {
        try {
            modeloTabla.setRowCount(0);
            List<Aeropuerto> lista = aeropuertoDAO.obtenerTodos();
            for (Aeropuerto a : lista) {
                modeloTabla.addRow(new Object[]{a.getIdAeropuerto(), a.getNombre(), a.getCiudad(), a.getPais()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar aeropuertos: " + ex.getMessage());
        }
    }

    private void agregar() {
        try {
            Aeropuerto a = new Aeropuerto(0, txtNombre.getText(), txtCiudad.getText(), txtPais.getText());
            aeropuertoDAO.insertar(a);
            cargarAeropuertos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar aeropuerto: " + ex.getMessage());
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                Aeropuerto a = new Aeropuerto(id, txtNombre.getText(), txtCiudad.getText(), txtPais.getText());
                aeropuertoDAO.actualizar(a);
                cargarAeropuertos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar aeropuerto: " + ex.getMessage());
            }
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                aeropuertoDAO.eliminar(id);
                cargarAeropuertos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar aeropuerto: " + ex.getMessage());
            }
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtCiudad.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtPais.setText(modeloTabla.getValueAt(fila, 3).toString());
        }
    }
}