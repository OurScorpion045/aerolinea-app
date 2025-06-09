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

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PanelVuelos(Connection conexion) {
        this.vueloDAO = new VueloDAO(conexion);
        setLayout(new BorderLayout());

        // Configurar tabla con modelo
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Ruta", "Avion", "Fecha Salida", "Fecha Llegada", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa en la tabla
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel formulario con etiquetas y campos
        JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del Vuelo"));
        formulario.add(new JLabel("ID Ruta:"));
        formulario.add(txtIdRuta);
        formulario.add(new JLabel("ID Avion:"));
        formulario.add(txtIdAvion);
        formulario.add(new JLabel("Fecha Salida (yyyy-MM-dd HH:mm):"));
        formulario.add(txtFechaSalida);
        formulario.add(new JLabel("Fecha Llegada (yyyy-MM-dd HH:mm):"));
        formulario.add(txtFechaLlegada);
        formulario.add(new JLabel("Estado:"));
        formulario.add(txtEstado);
        add(formulario, BorderLayout.NORTH);

        // Panel botones
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        add(botones, BorderLayout.SOUTH);

        // Eventos botones
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        // Cargar datos cuando se selecciona una fila
        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        // Carga inicial de vuelos
        cargarVuelos();
    }

    private void cargarVuelos() {
        try {
            modeloTabla.setRowCount(0);
            List<Vuelo> lista = vueloDAO.obtenerTodos();
            for (Vuelo vuelo : lista) {
                modeloTabla.addRow(new Object[]{
                        vuelo.getIdVuelo(),
                        vuelo.getIdRuta(),
                        vuelo.getIdAvion(),
                        vuelo.getFechaSalida().format(FORMATO_FECHA),
                        vuelo.getFechaLlegada().format(FORMATO_FECHA),
                        vuelo.getEstado()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar vuelos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregar() {
        try {
            int idRuta = Integer.parseInt(txtIdRuta.getText().trim());
            int idAvion = Integer.parseInt(txtIdAvion.getText().trim());
            LocalDateTime fechaSalida = LocalDateTime.parse(txtFechaSalida.getText().trim(), FORMATO_FECHA);
            LocalDateTime fechaLlegada = LocalDateTime.parse(txtFechaLlegada.getText().trim(), FORMATO_FECHA);
            String estado = txtEstado.getText().trim();

            Vuelo vuelo = new Vuelo(0, idRuta, idAvion, fechaSalida, fechaLlegada, estado);
            vueloDAO.insertar(vuelo);
            limpiarCampos();
            cargarVuelos();
            JOptionPane.showMessageDialog(this, "Vuelo agregado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar vuelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int idVuelo = (int) modeloTabla.getValueAt(fila, 0);
                int idRuta = Integer.parseInt(txtIdRuta.getText().trim());
                int idAvion = Integer.parseInt(txtIdAvion.getText().trim());
                LocalDateTime fechaSalida = LocalDateTime.parse(txtFechaSalida.getText().trim(), FORMATO_FECHA);
                LocalDateTime fechaLlegada = LocalDateTime.parse(txtFechaLlegada.getText().trim(), FORMATO_FECHA);
                String estado = txtEstado.getText().trim();

                Vuelo vuelo = new Vuelo(idVuelo, idRuta, idAvion, fechaSalida, fechaLlegada, estado);
                vueloDAO.actualizar(vuelo);
                limpiarCampos();
                cargarVuelos();
                JOptionPane.showMessageDialog(this, "Vuelo actualizado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar vuelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vuelo para actualizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int idVuelo = (int) modeloTabla.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro que desea eliminar el vuelo seleccionado?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    vueloDAO.eliminar(idVuelo);
                    limpiarCampos();
                    cargarVuelos();
                    JOptionPane.showMessageDialog(this, "Vuelo eliminado correctamente.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar vuelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vuelo para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
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

    private void limpiarCampos() {
        txtIdRuta.setText("");
        txtIdAvion.setText("");
        txtFechaSalida.setText("");
        txtFechaLlegada.setText("");
        txtEstado.setText("");
    }
}
