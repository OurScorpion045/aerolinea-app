package com.aerolinea.gui;

import com.aerolinea.dao.PasajeroDAO;
import com.aerolinea.modelo.Pasajero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelPasajeros extends JPanel {
    private final PasajeroDAO pasajeroDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtFechaNacimiento = new JTextField(); // formato yyyy-MM-ddTHH:mm
    private final JTextField txtNacionalidad = new JTextField();
    private final JTextField txtPasaporte = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtTelefono = new JTextField();

    public PanelPasajeros(Connection conexion) {
        this.pasajeroDAO = new PasajeroDAO(conexion);
        setLayout(new BorderLayout());

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{
                "ID", "Nombre", "Apellido", "Fecha Nac.", "Nacionalidad", "Pasaporte", "Email", "Teléfono"
        }, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(8, 2));
        formulario.add(new JLabel("Nombre:")); formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:")); formulario.add(txtApellido);
        formulario.add(new JLabel("Fecha Nacimiento (yyyy-MM-ddTHH:mm):")); formulario.add(txtFechaNacimiento);
        formulario.add(new JLabel("Nacionalidad:")); formulario.add(txtNacionalidad);
        formulario.add(new JLabel("Pasaporte:")); formulario.add(txtPasaporte);
        formulario.add(new JLabel("Email:")); formulario.add(txtEmail);
        formulario.add(new JLabel("Teléfono:")); formulario.add(txtTelefono);
        add(formulario, BorderLayout.NORTH);

        // Botones
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        add(botones, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> agregarPasajero());
        btnActualizar.addActionListener(e -> actualizarPasajero());
        btnEliminar.addActionListener(e -> eliminarPasajero());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        cargarPasajeros();
    }

    private void cargarPasajeros() {
        try {
            modeloTabla.setRowCount(0);
            List<Pasajero> pasajeros = pasajeroDAO.obtenerTodos();
            for (Pasajero p : pasajeros) {
                modeloTabla.addRow(new Object[]{
                        p.getIdPasajero(), p.getNombre(), p.getApellido(),
                        p.getFechaNacimiento(), p.getNacionalidad(), p.getPasaporte(),
                        p.getEmail(), p.getTelefono()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar pasajeros: " + ex.getMessage());
        }
    }

    private void agregarPasajero() {
        try {
            Pasajero p = new Pasajero(0,
                    txtNombre.getText(),
                    txtApellido.getText(),
                    LocalDateTime.parse(txtFechaNacimiento.getText()),
                    txtNacionalidad.getText(),
                    txtPasaporte.getText(),
                    txtEmail.getText(),
                    Integer.parseInt(txtTelefono.getText())
            );
            pasajeroDAO.insertar(p);
            cargarPasajeros();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar pasajero: " + ex.getMessage());
        }
    }

    private void actualizarPasajero() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                Pasajero p = new Pasajero(id,
                        txtNombre.getText(),
                        txtApellido.getText(),
                        LocalDateTime.parse(txtFechaNacimiento.getText()),
                        txtNacionalidad.getText(),
                        txtPasaporte.getText(),
                        txtEmail.getText(),
                        Integer.parseInt(txtTelefono.getText())
                );
                pasajeroDAO.actualizar(p);
                cargarPasajeros();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar pasajero: " + ex.getMessage());
            }
        }
    }

    private void eliminarPasajero() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                pasajeroDAO.eliminar(id);
                cargarPasajeros();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pasajero: " + ex.getMessage());
            }
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtFechaNacimiento.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtNacionalidad.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtPasaporte.setText(modeloTabla.getValueAt(fila, 5).toString());
            txtEmail.setText(modeloTabla.getValueAt(fila, 6).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 7).toString());
        }
    }
}