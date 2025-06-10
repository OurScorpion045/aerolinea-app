package com.aerolinea.gui;

import com.aerolinea.dao.BoletoDAO;
import com.aerolinea.modelo.Boleto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class PanelBoletos extends JPanel {
    private final BoletoDAO boletoDAO;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField idField = new JTextField();
    private final JTextField idReservaField = new JTextField();
    private final JTextField idPasajeroField = new JTextField();
    private final JTextField idVueloField = new JTextField();
    private final JTextField idCheckinField = new JTextField();
    private final JTextField idPagoField = new JTextField();
    private final JTextField claseField = new JTextField();
    private final JTextField precioField = new JTextField();
    private final JTextField fechaField = new JTextField();

    public PanelBoletos(Connection conexion) {
        this.boletoDAO = new BoletoDAO(conexion);
        setLayout(new BorderLayout());

        // Tabla
        String[] columnas = {"ID", "Reserva", "Pasajero", "Vuelo", "Checkin", "Pago", "Clase", "Precio", "Fecha"};
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Boleto"));
        formPanel.add(new JLabel("ID Boleto:")); formPanel.add(idField);
        formPanel.add(new JLabel("ID Reserva:")); formPanel.add(idReservaField);
        formPanel.add(new JLabel("ID Pasajero:")); formPanel.add(idPasajeroField);
        formPanel.add(new JLabel("ID Vuelo:")); formPanel.add(idVueloField);
        formPanel.add(new JLabel("ID Check-in:")); formPanel.add(idCheckinField);
        formPanel.add(new JLabel("ID Pago:")); formPanel.add(idPagoField);
        formPanel.add(new JLabel("Clase:")); formPanel.add(claseField);
        formPanel.add(new JLabel("Precio Total:")); formPanel.add(precioField);
        formPanel.add(new JLabel("Fecha Emisión (YYYY-MM-DDTHH:MM):")); formPanel.add(fechaField);
        add(formPanel, BorderLayout.EAST);

        // Botones
        JPanel buttonPanel = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        add(buttonPanel, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarBoleto());
        btnActualizar.addActionListener(e -> actualizarBoleto());
        btnEliminar.addActionListener(e -> eliminarBoleto());
        table.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        cargarBoletos();
    }

    private void cargarBoletos() {
        try {
            tableModel.setRowCount(0);
            List<Boleto> lista = boletoDAO.obtenerTodos();
            for (Boleto b : lista) {
                tableModel.addRow(new Object[]{
                        b.getIdBoleto(),
                        b.getIdReserva(),
                        b.getIdPasajero(),
                        b.getIdVuelo(),
                        b.getIdCheckin(),
                        b.getIdPago(),
                        b.getClase(),
                        b.getPrecioTotal(),
                        b.getFechaEmision()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar boletos: " + e.getMessage());
        }
    }

    private void guardarBoleto() {
        try {
            Boleto b = new Boleto(
                    0,
                    Integer.parseInt(idReservaField.getText()),
                    Integer.parseInt(idPasajeroField.getText()),
                    Integer.parseInt(idVueloField.getText()),
                    parseNullableInt(idCheckinField.getText()),
                    parseNullableInt(idPagoField.getText()),
                    claseField.getText(),
                    Double.parseDouble(precioField.getText()),
                    LocalDateTime.parse(fechaField.getText())
            );
            boletoDAO.insertar(b);
            cargarBoletos();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizarBoleto() {
        try {
            Boleto b = new Boleto(
                    Integer.parseInt(idField.getText()),
                    Integer.parseInt(idReservaField.getText()),
                    Integer.parseInt(idPasajeroField.getText()),
                    Integer.parseInt(idVueloField.getText()),
                    parseNullableInt(idCheckinField.getText()),
                    parseNullableInt(idPagoField.getText()),
                    claseField.getText(),
                    Double.parseDouble(precioField.getText()),
                    LocalDateTime.parse(fechaField.getText())
            );
            boletoDAO.actualizar(b);
            cargarBoletos();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarBoleto() {
        try {
            int id = Integer.parseInt(idField.getText());
            boletoDAO.eliminar(id);
            cargarBoletos();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void cargarSeleccion() {
        int fila = table.getSelectedRow();
        if (fila >= 0) {
            idField.setText(table.getValueAt(fila, 0).toString());
            idReservaField.setText(table.getValueAt(fila, 1).toString());
            idPasajeroField.setText(table.getValueAt(fila, 2).toString());
            idVueloField.setText(table.getValueAt(fila, 3).toString());
            idCheckinField.setText(valueOrEmpty(table.getValueAt(fila, 4)));
            idPagoField.setText(valueOrEmpty(table.getValueAt(fila, 5)));
            claseField.setText(table.getValueAt(fila, 6).toString());
            precioField.setText(table.getValueAt(fila, 7).toString());
            fechaField.setText(table.getValueAt(fila, 8).toString());
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        idReservaField.setText("");
        idPasajeroField.setText("");
        idVueloField.setText("");
        idCheckinField.setText("");
        idPagoField.setText("");
        claseField.setText("");
        precioField.setText("");
        fechaField.setText("");
    }

    private Integer parseNullableInt(String s) {
        return s.isBlank() ? null : Integer.parseInt(s);
    }

    private String valueOrEmpty(Object o) {
        return o == null ? "" : o.toString();
    }
}