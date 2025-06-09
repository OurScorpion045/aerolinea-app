package com.aerolinea.gui;

import com.aerolinea.dao.PagoDAO;
import com.aerolinea.modelo.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelPagos extends JPanel {
    private final PagoDAO pagoDAO;
    private final DefaultTableModel tableModel;
    private final JTable tabla;

    private final JTextField txtIdPago;
    private final JTextField txtIdReserva;
    private final JTextField txtMetodoPago;
    private final JTextField txtMonto;
    private final JTextField txtFechaPago;

    public PanelPagos(Connection conexion) {
        this.pagoDAO = new PagoDAO(conexion);
        setLayout(new BorderLayout());

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{"ID Pago", "ID Reserva", "Método Pago", "Monto", "Fecha Pago"}, 0);
        tabla = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de formulario
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del Pago"));

        txtIdPago = new JTextField();
        txtIdReserva = new JTextField();
        txtMetodoPago = new JTextField();
        txtMonto = new JTextField();
        txtFechaPago = new JTextField();

        formulario.add(new JLabel("ID Pago:"));
        formulario.add(txtIdPago);
        formulario.add(new JLabel("ID Reserva:"));
        formulario.add(txtIdReserva);
        formulario.add(new JLabel("Método de Pago:"));
        formulario.add(txtMetodoPago);
        formulario.add(new JLabel("Monto:"));
        formulario.add(txtMonto);
        formulario.add(new JLabel("Fecha de Pago (yyyy-MM-dd HH:mm):"));
        formulario.add(txtFechaPago);

        // Panel de botones
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        // Agregar listeners
        btnAgregar.addActionListener(this::agregarPago);
        btnActualizar.addActionListener(this::actualizarPago);
        btnEliminar.addActionListener(this::eliminarPago);

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.add(formulario, BorderLayout.CENTER);
        inferior.add(botones, BorderLayout.SOUTH);

        add(inferior, BorderLayout.SOUTH);

        cargarPagos();
    }

    private void cargarPagos() {
        try {
            tableModel.setRowCount(0);
            List<Pago> pagos = pagoDAO.obtenerTodos();
            for (Pago p : pagos) {
                tableModel.addRow(new Object[]{
                        p.getIdPago(),
                        p.getIdReserva(),
                        p.getMetodoPago(),
                        p.getMonto(),
                        p.getFechaPago().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                });
            }
        } catch (Exception e) {
            mostrarError("Error al cargar pagos: " + e.getMessage());
        }
    }

    private void agregarPago(ActionEvent e) {
        try {
            int idReserva = Integer.parseInt(txtIdReserva.getText());
            String metodoPago = txtMetodoPago.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            LocalDateTime fechaPago = LocalDateTime.parse(txtFechaPago.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Pago pago = new Pago(0, idReserva, metodoPago, monto, fechaPago);
            pagoDAO.insertar(pago);
            cargarPagos();
        } catch (Exception ex) {
            mostrarError("Error al agregar pago: " + ex.getMessage());
        }
    }

    private void actualizarPago(ActionEvent e) {
        try {
            int idPago = Integer.parseInt(txtIdPago.getText());
            int idReserva = Integer.parseInt(txtIdReserva.getText());
            String metodoPago = txtMetodoPago.getText();
            double monto = Double.parseDouble(txtMonto.getText());
            LocalDateTime fechaPago = LocalDateTime.parse(txtFechaPago.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Pago pago = new Pago(idPago, idReserva, metodoPago, monto, fechaPago);
            pagoDAO.actualizar(pago);
            cargarPagos();
        } catch (Exception ex) {
            mostrarError("Error al actualizar pago: " + ex.getMessage());
        }
    }

    private void eliminarPago(ActionEvent e) {
        try {
            int idPago = Integer.parseInt(txtIdPago.getText());
            pagoDAO.eliminar(idPago);
            cargarPagos();
        } catch (Exception ex) {
            mostrarError("Error al eliminar pago: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}