package com.aerolinea.gui;

import com.aerolinea.dao.VueloDAO;
import com.aerolinea.modelo.Vuelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelVuelos extends JPanel {
    private final VueloDAO vueloDAO;
    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final JTextField txtIdRuta = new JTextField();
    private final JTextField txtIdAvion = new JTextField();
    private final JTextField txtFechaSalida = new JTextField();
    private final JTextField txtFechaLlegada = new JTextField();
    private final JTextField txtEstado = new JTextField();

    public PanelVuelos(Connection conexion) {
        this.vueloDAO = new VueloDAO(conexion);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Ruta", "Avion", "Fecha Salida", "Fecha llegada", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel formulario = new JPanel(new GridLayout(5, 2));
        formulario.add(new JLabel("Ruta")); formulario.add(txtIdRuta);
        formulario.add(new JLabel("Avion")); formulario.add(txtIdAvion);
        formulario.add(new JLabel("Fecha salida (yyyy-MM-dd HH:mm)")); formulario.add(txtFechaSalida);
        formulario.add(new JLabel("Fecha llegada (yyyy-MM-dd HH:mm)")); formulario.add(txtFechaLlegada);
        formulario.add(new JLabel("Estado del vuelo")); formulario.add(txtEstado);
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

        cargarVuelos();
    }

    private void cargarVuelos() {
        try {
            modeloTabla.setRowCount(0);
            List<Vuelo> lista = vueloDAO.obtenerTodos();
            for (Vuelo a : lista) {
                modeloTabla.addRow(new Object[]{a.getIdVuelo(), a.getIdRuta(), a.getIdAvion(), a.getFechaSalida(), a.getFechaLlegada(), a.getEstado()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar vuelos: " + ex.getMessage());
        }
    }

    private void agregar() {
        try {
            int idRuta = Integer.parseInt(txtIdRuta.getText());
            int idAvion = Integer.parseInt(txtIdAvion.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaSalida = LocalDateTime.parse(txtFechaSalida.getText(), formatter);
            LocalDateTime fechaLlegada = LocalDateTime.parse(txtFechaLlegada.getText(), formatter);


            Vuelo a = new Vuelo(0, idRuta, idAvion, fechaSalida, fechaLlegada, txtEstado.getText());
            vueloDAO.insertar(a);
            cargarVuelos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar aeropuertos " + ex.getMessage());
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {

                int idRuta = Integer.parseInt(txtIdRuta.getText());
                int idAvion = Integer.parseInt(txtIdAvion.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime fechaSalida = LocalDateTime.parse(txtFechaSalida.getText(), formatter);
                LocalDateTime fechaLlegada = LocalDateTime.parse(txtFechaLlegada.getText(), formatter);

                int id = (int) modeloTabla.getValueAt(fila, 0);
                Vuelo a = new Vuelo(id, idRuta, idAvion, fechaSalida, fechaLlegada, txtEstado.getText());
                vueloDAO.actualizar(a);
                cargarVuelos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar vuelo: " + ex.getMessage());
            }
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                vueloDAO.eliminar(id);
                cargarVuelos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar vuelo " + ex.getMessage());
            }
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtIdRuta.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtIdAvion.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtFechaSalida.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtFechaLlegada.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtEstado.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }
}
