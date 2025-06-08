package com.aerolinea.gui;

import com.aerolinea.dao.PasajeroDAO;
import com.aerolinea.modelo.Pasajero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PanelPasajeros extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellido, txtDocumento;
    private PasajeroDAO pasajeroDAO;

    public PanelPasajeros() {
        pasajeroDAO = new PasajeroDAO();

        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Documento"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(new JLabel("Nombre: "));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido"));
        txtApellido = new JTextField();

    }
}
