package com.aerolinea.gui;

import com.aerolinea.dao.ReservaDAO;
import com.aerolinea.modelo.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelReserva extends JPanel {
    private final JTextField txtIdReserva;
    private final JTextField txtIdVuelo;
    private final JTextField txtFechaReserva;
    private final JTextField txtEstado;
    private final JTable tablaReservas;
    private final DefaultTableModel modeloTabla;
    private final ReservaDAO reservaDAO;

    public PanelReserva(Connection conexion) {
        this.reservaDAO = new ReservaDAO(conexion);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reserva"));

        txtIdReserva = new JTextField();
        txtIdVuelo = new JTextField();
        txtFechaReserva = new JTextField();
        txtEstado = new JTextField();

        panelFormulario.add(new JLabel("ID Reserva:"));
        panelFormulario.add(txtIdReserva);
        panelFormulario.add(new JLabel("ID Vuelo:"));
        panelFormulario.add(txtIdVuelo);
        panelFormulario.add(new JLabel("Fecha Reserva (yyyy-MM-dd HH:mm):"));
        panelFormulario.add(txtFechaReserva);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(txtEstado);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID Reserva", "ID Vuelo", "Fecha Reserva", "Estado"}, 0);
        tablaReservas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);

        // Eventos
        btnAgregar.addActionListener(e -> agregarReserva());
        btnActualizar.addActionListener(e -> actualizarReserva());
        btnEliminar.addActionListener(e -> eliminarReserva());

        tablaReservas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaReservas.getSelectedRow();
            if (fila != -1) {
                txtIdReserva.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtIdVuelo.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtFechaReserva.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtEstado.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        cargarReservas();
    }

    private void agregarReserva() {
        try {
            int idVuelo = Integer.parseInt(txtIdVuelo.getText());
            LocalDateTime fecha = LocalDateTime.parse(txtFechaReserva.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String estado = txtEstado.getText();

            Reserva reserva = new Reserva(0, idVuelo, fecha, estado);
            reservaDAO.insertar(reserva);
            cargarReservas();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al agregar reserva: " + ex.getMessage());
        }
    }

    private void actualizarReserva() {
        try {
            int idReserva = Integer.parseInt(txtIdReserva.getText());
            int idVuelo = Integer.parseInt(txtIdVuelo.getText());
            LocalDateTime fecha = LocalDateTime.parse(txtFechaReserva.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String estado = txtEstado.getText();

            Reserva reserva = new Reserva(idReserva, idVuelo, fecha, estado);
            reservaDAO.actualizar(reserva);
            cargarReservas();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al actualizar reserva: " + ex.getMessage());
        }
    }

    private void eliminarReserva() {
        try {
            int idReserva = Integer.parseInt(txtIdReserva.getText());
            reservaDAO.eliminar(idReserva);
            cargarReservas();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError("Error al eliminar reserva: " + ex.getMessage());
        }
    }

    private void cargarReservas() {
        try {
            modeloTabla.setRowCount(0);
            List<Reserva> reservas = reservaDAO.obtenerTodos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Reserva r : reservas) {
                modeloTabla.addRow(new Object[]{
                        r.getIdReserva(),
                        r.getIdVuelo(),
                        r.getFechaReserva().format(formatter),
                        r.getEstado()
                });
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar reservas: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtIdReserva.setText("");
        txtIdVuelo.setText("");
        txtFechaReserva.setText("");
        txtEstado.setText("");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}