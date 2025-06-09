package com.aerolinea.gui;
import com.aerolinea.dao.AvionDAO;
import com.aerolinea.modelo.Avion;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class PanelAvion extends JFrame{
    private JTextField idAvion;
    private JTextField modelo;
    private JTextField fabricante;
    private JTextField capacidad;
    private JTextField anioFabricacion;
    private JButton bttnGuardar;

    public PanelAvion() {
        setTitle ("Registro de Avión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize (350, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        idAvion = new JTextField();
        modelo = new JTextField();
        fabricante = new JTextField();
        capacidad = new JTextField();
        anioFabricacion = new JTextField();
        bttnGuardar = new JButton("Guardar");

        panel.add(new JLabel("Id del Avión: "));
        panel.add(idAvion);
        panel.add(new JLabel("Modelo: "));
        panel.add(modelo);
        panel.add(new JLabel("Fabricante: "));
        panel.add(fabricante);
        panel.add(new JLabel("Capacidad: "));
        panel.add(capacidad);
        panel.add(new JLabel("Año de fabricación: "));
        panel.add(anioFabricacion);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            guardarAvion();
        });

    }
    private void guardarAvion() {
        try {
            int id_avion = Integer.parseInt(idAvion.getText().trim());
            String mod = modelo.getText().trim();
            String fabr = fabricante.getText().trim();
            int cap = Integer.parseInt(capacidad.getText().trim());
            int anioF = Integer.parseInt(anioFabricacion.getText().trim());

            Avion avion = new Avion(id_avion, mod, fabr, cap, anioF);
            Connection conexion = ConexionBD.conectar();
            AvionDAO dao = new AvionDAO(conexion);
            dao.insertar(avion);

            JOptionPane.showMessageDialog(this, "Detalles del avión guardados");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelAvion().setVisible(true);
        });
    }
}