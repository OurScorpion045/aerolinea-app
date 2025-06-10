package com.aerolinea.gui;

import com.aerolinea.dao.RutaDAO;
import com.aerolinea.modelo.Ruta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class PanelRuta extends JPanel {
    private final RutaDAO rutaDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final JTextField txtIdAeropuertoOrigen = new JTextField();
    private final JTextField txtIdAeropuertoDestino = new JTextField();
    private final JTextField txtDuracion = new JTextField(); // duración en minutos

    public PanelRuta(Connection conexion) {
        this.rutaDAO = new RutaDAO(conexion);
        setLayout(new BorderLayout());

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{
                "ID Ruta", "Aeropuerto Origen", "Aeropuerto Destino", "Duración"
        }, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(3, 2));
        formulario.add(new JLabel("ID Aeropuerto Origen:")); formulario.add(txtIdAeropuertoOrigen);
        formulario.add(new JLabel("ID Aeropuerto Destino:")); formulario.add(txtIdAeropuertoDestino);
        formulario.add(new JLabel("Duración (minutos):")); formulario.add(txtDuracion);
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
        btnAgregar.addActionListener(e -> agregarRuta());
        btnActualizar.addActionListener(e -> actualizarRuta());
        btnEliminar.addActionListener(e -> eliminarRuta());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        cargarRutas();
    }

    private void cargarRutas() {
        try {
            modeloTabla.setRowCount(0);
            List<Ruta> rutas = rutaDAO.obtenerTodos();
            for (Ruta r : rutas) {
                modeloTabla.addRow(new Object[]{
                        r.getIdRuta(), r.getIdAeropuertoOrigen(), r.getIdAeropuertoDestino(), r.getDuracion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar rutas: " + ex.getMessage());
        }
    }

    private void agregarRuta() {
        try {
            Ruta r = new Ruta(0,
                    Integer.parseInt(txtIdAeropuertoOrigen.getText()),
                    Integer.parseInt(txtIdAeropuertoDestino.getText()),
                    Integer.parseInt(txtDuracion.getText())
            );
            rutaDAO.insertar(r);
            cargarRutas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar ruta: " + ex.getMessage());
        }
    }

    private void actualizarRuta() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                Ruta r = new Ruta(id,
                        Integer.parseInt(txtIdAeropuertoOrigen.getText()),
                        Integer.parseInt(txtIdAeropuertoDestino.getText()),
                        Integer.parseInt(txtDuracion.getText())
                );
                rutaDAO.actualizar(r);
                cargarRutas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar ruta: " + ex.getMessage());
            }
        }
    }

    private void eliminarRuta() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                rutaDAO.eliminar(id);
                cargarRutas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar ruta: " + ex.getMessage());
            }
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIdAeropuertoOrigen.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtIdAeropuertoDestino.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtDuracion.setText(modeloTabla.getValueAt(fila, 3).toString());
        }
    }
}