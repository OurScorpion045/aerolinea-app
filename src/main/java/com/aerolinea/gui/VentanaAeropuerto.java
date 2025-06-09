package com.aerolinea.gui;
import com.aerolinea.dao.AeropuertoDAO;
import com.aerolinea.modelo.Aeropuerto;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class VentanaAeropuerto extends JFrame{
    private JTextField idAeropuerto;
    private JTextField textNombre;
    private JTextField textCiudad;
    private JTextField textPais;
    private JButton bttnGuardar;

    public VentanaAeropuerto() {
        setTitle ("Registro de Aeropuerto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize (350, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        idAeropuerto = new JTextField();
        textNombre = new JTextField();
        textCiudad = new JTextField();
        textPais = new JTextField();
        bttnGuardar = new JButton("Guardar");

        panel.add(new JLabel("Id del Aeropuerto: "));
        panel.add(idAeropuerto);
        panel.add(new JLabel("Nombre: "));
        panel.add(textNombre);
        panel.add(new JLabel("Ciudad: "));
        panel.add(textCiudad);
        panel.add(new JLabel("País: "));
        panel.add(textPais);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            guardarAeropuerto();
        });

    }
    private void guardarAeropuerto() {
        try {
            int id = Integer.parseInt(idAeropuerto.getText().trim());
            String nombre = textNombre.getText().trim();
            String ciudad = textCiudad.getText().trim();
            String pais = textPais.getText().trim();

            Aeropuerto aeropuerto = new Aeropuerto(id, nombre, ciudad, pais);
            Connection conexion = ConexionBD.conectar();
            AeropuertoDAO dao = new AeropuertoDAO(conexion);
            dao.insertar(aeropuerto);

            JOptionPane.showMessageDialog(this, "Aeropuerto guardado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaAeropuerto().setVisible(true);
        });
    }
}

