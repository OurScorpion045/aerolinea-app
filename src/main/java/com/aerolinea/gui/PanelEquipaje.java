package com.aerolinea.gui;

import com.aerolinea.dao.EquipajeDAO;
import com.aerolinea.modelo.Equipaje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class PanelEquipaje extends JPanel {
    private final EquipajeDAO equipajeDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;
    private final JTextField txtIdEquipaje, txtIdCheckin, txtPeso, txtDescripcion, txtCostoExtra;

    public PanelEquipaje(Connection conexion) {
        this.equipajeDAO = new EquipajeDAO(conexion);
        setLayout(new BorderLayout(15, 15));

        // Panel Izquierdo (formulario + botones)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BorderLayout(10, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(350, getHeight()));

        // Formulario con GridLayout
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Equipaje"));

        txtIdEquipaje = new JTextField();
        txtIdCheckin = new JTextField();
        txtPeso = new JTextField();
        txtDescripcion = new JTextField();
        txtCostoExtra = new JTextField();

        panelFormulario.add(new JLabel("ID Equipaje:"));
        panelFormulario.add(txtIdEquipaje);
        panelFormulario.add(new JLabel("ID Check-in:"));
        panelFormulario.add(txtIdCheckin);
        panelFormulario.add(new JLabel("Peso (kg):"));
        panelFormulario.add(txtPeso);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(new JLabel("Costo Extra:"));
        panelFormulario.add(txtCostoExtra);

        // Botones en BoxLayout
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        Dimension btnSize = new Dimension(120, 30);
        btnAgregar.setMaximumSize(btnSize);
        btnActualizar.setMaximumSize(btnSize);
        btnEliminar.setMaximumSize(btnSize);

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnActualizar);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnEliminar);

        panelIzquierdo.add(panelFormulario, BorderLayout.NORTH);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);

        // Tabla al lado derecho
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Check-in", "Peso", "Descripción", "Costo Extra"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado de Equipaje"));
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e -> agregarEquipaje());
        btnActualizar.addActionListener(e -> actualizarEquipaje());
        btnEliminar.addActionListener(e -> eliminarEquipaje());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtIdEquipaje.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtIdCheckin.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtPeso.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtDescripcion.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtCostoExtra.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        cargarDatos();
    }

    private void agregarEquipaje() {
        try {
            int idCheckin = Integer.parseInt(txtIdCheckin.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            String descripcion = txtDescripcion.getText();
            double costoExtra = Double.parseDouble(txtCostoExtra.getText());

            Equipaje nuevo = new Equipaje(0, idCheckin, peso, descripcion, costoExtra);
            equipajeDAO.insertar(nuevo);
            limpiarCampos();
            cargarDatos();
            mostrarMensaje("Equipaje agregado correctamente.");
        } catch (Exception ex) {
            mostrarError("Error al agregar: " + ex.getMessage());
        }
    }

    private void actualizarEquipaje() {
        try {
            int idEquipaje = Integer.parseInt(txtIdEquipaje.getText());
            int idCheckin = Integer.parseInt(txtIdCheckin.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            String descripcion = txtDescripcion.getText();
            double costoExtra = Double.parseDouble(txtCostoExtra.getText());

            Equipaje equipaje = new Equipaje(idEquipaje, idCheckin, peso, descripcion, costoExtra);
            equipajeDAO.actualizar(equipaje);
            limpiarCampos();
            cargarDatos();
            mostrarMensaje("Equipaje actualizado correctamente.");
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarEquipaje() {
        try {
            int idEquipaje = Integer.parseInt(txtIdEquipaje.getText());
            int opcion = JOptionPane.showConfirmDialog(this, "¿Eliminar el equipaje seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                equipajeDAO.eliminar(idEquipaje);
                limpiarCampos();
                cargarDatos();
                mostrarMensaje("Equipaje eliminado.");
            }
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
        }
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Equipaje> lista = equipajeDAO.obtenerTodos();
            for (Equipaje e : lista) {
                modeloTabla.addRow(new Object[]{
                        e.getIdEquipaje(),
                        e.getIdCheckin(),
                        e.getPeso(),
                        e.getDescripcion(),
                        e.getCostoExtra()
                });
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar datos: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtIdEquipaje.setText("");
        txtIdCheckin.setText("");
        txtPeso.setText("");
        txtDescripcion.setText("");
        txtCostoExtra.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}