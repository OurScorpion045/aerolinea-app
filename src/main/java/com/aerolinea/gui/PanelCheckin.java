package com.aerolinea.gui;

import com.aerolinea.dao.CheckinDAO;
import com.aerolinea.modelo.Checkin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelCheckin extends JPanel {
    private final JTextField txtIdCheckin = new JTextField(5);
    private final JTextField txtIdPasajero = new JTextField(5);
    private final JTextField txtIdReserva = new JTextField(5);
    private final JTextField txtFechaCheckin = new JTextField(16);
    private final JTextField txtAsiento = new JTextField(5);
    private final JTextField txtPuerta = new JTextField(5);
    private final DefaultTableModel modeloTabla = new DefaultTableModel();
    private final JTable tabla = new JTable(modeloTabla);
    private final CheckinDAO checkinDAO;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PanelCheckin(Connection conexion) {
        this.checkinDAO = new CheckinDAO(conexion);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del Check-in"));

        formulario.add(new JLabel("ID Check-in:"));
        formulario.add(txtIdCheckin);
        formulario.add(new JLabel("ID Pasajero:"));
        formulario.add(txtIdPasajero);
        formulario.add(new JLabel("ID Reserva:"));
        formulario.add(txtIdReserva);
        formulario.add(new JLabel("Fecha Check-in (yyyy-MM-dd HH:mm):"));
        formulario.add(txtFechaCheckin);
        formulario.add(new JLabel("Asiento:"));
        formulario.add(txtAsiento);
        formulario.add(new JLabel("Puerta de Embarque:"));
        formulario.add(txtPuerta);

        // Panel de botones
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        // Panel superior (formulario + botones)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(formulario, BorderLayout.CENTER);
        panelSuperior.add(botones, BorderLayout.SOUTH);

        // Tabla
        modeloTabla.addColumn("ID Check-in");
        modeloTabla.addColumn("ID Pasajero");
        modeloTabla.addColumn("ID Reserva");
        modeloTabla.addColumn("Fecha Check-in");
        modeloTabla.addColumn("Asiento");
        modeloTabla.addColumn("Puerta Embarque");

        JScrollPane scroll = new JScrollPane(tabla);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        cargarDatos();

        // Acción de la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtIdCheckin.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtIdPasajero.setText(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
                txtIdReserva.setText(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
                txtFechaCheckin.setText(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
                txtAsiento.setText(tabla.getValueAt(tabla.getSelectedRow(), 4).toString());
                txtPuerta.setText(tabla.getValueAt(tabla.getSelectedRow(), 5).toString());
            }
        });

        // Botón Agregar
        btnAgregar.addActionListener((ActionEvent e) -> {
            try {
                Checkin c = new Checkin(0,
                        Integer.parseInt(txtIdPasajero.getText()),
                        Integer.parseInt(txtIdReserva.getText()),
                        LocalDateTime.parse(txtFechaCheckin.getText(), formatter),
                        txtAsiento.getText(),
                        Integer.parseInt(txtPuerta.getText()));
                checkinDAO.insertar(c);
                cargarDatos();
            } catch (Exception ex) {
                mostrarError("Error al agregar: " + ex.getMessage());
            }
        });

        // Botón Actualizar
        btnActualizar.addActionListener((ActionEvent e) -> {
            try {
                Checkin c = new Checkin(
                        Integer.parseInt(txtIdCheckin.getText()),
                        Integer.parseInt(txtIdPasajero.getText()),
                        Integer.parseInt(txtIdReserva.getText()),
                        LocalDateTime.parse(txtFechaCheckin.getText(), formatter),
                        txtAsiento.getText(),
                        Integer.parseInt(txtPuerta.getText()));
                checkinDAO.actualizar(c);
                cargarDatos();
            } catch (Exception ex) {
                mostrarError("Error al actualizar: " + ex.getMessage());
            }
        });

        // Botón Eliminar
        btnEliminar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtIdCheckin.getText());
                checkinDAO.eliminar(id);
                cargarDatos();
            } catch (Exception ex) {
                mostrarError("Error al eliminar: " + ex.getMessage());
            }
        });
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Checkin> lista = checkinDAO.obtenerTodos();
            for (Checkin c : lista) {
                modeloTabla.addRow(new Object[]{
                        c.getIdCheckin(),
                        c.getIdPasajero(),
                        c.getIdReserva(),
                        c.getFechaCheckin().format(formatter),
                        c.getAsiento(),
                        c.getPuertaEmbarque()
                });
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}