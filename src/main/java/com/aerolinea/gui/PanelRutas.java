package com.aerolinea.gui;
import com.aerolinea.dao.RutaDAO;
import com.aerolinea.modelo.Ruta;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class PanelRutas extends JFrame{
    private JTextField idRuta;
    private JTextField idAeropuertoOrigen;
    private JTextField idAeropuertoDestino;
    private JTextField duracion;
    private JButton bttnGuardar;

    public PanelRutas() {
        setTitle ("Registro de Ruta");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize (450, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        idRuta = new JTextField();
        idAeropuertoOrigen = new JTextField();
        idAeropuertoDestino = new JTextField();
        duracion = new JTextField();
        bttnGuardar = new JButton("Guardar");

        panel.add(new JLabel("Id de la ruta: "));
        panel.add(idRuta);
        panel.add(new JLabel("Id del Aeropuerto de Origen: "));
        panel.add(idAeropuertoOrigen);
        panel.add(new JLabel("Id del Aeropuerto de Destino: "));
        panel.add(idAeropuertoDestino);
        panel.add(new JLabel("Duracion del Vuelo (en minutos): "));
        panel.add(duracion);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            guardarRutas();
        });

    }
    private void guardarRutas() {
        try {
            int id_ruta = Integer.parseInt(idRuta.getText().trim());
            int id_origen = Integer.parseInt(idAeropuertoOrigen.getText().trim());
            int id_destino = Integer.parseInt(idAeropuertoDestino.getText().trim());
            int dur = Integer.parseInt(duracion.getText().trim());

            Ruta ruta = new Ruta(id_ruta, id_origen, id_destino, dur);
            Connection conexion = ConexionBD.conectar();
            RutaDAO dao = new RutaDAO(conexion);
            dao.insertar(ruta);

            JOptionPane.showMessageDialog(this, "Ruta guardada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelRutas().setVisible(true);
        });
    }
}
