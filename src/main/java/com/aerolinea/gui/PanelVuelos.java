package com.aerolinea.gui;
import com.aerolinea.dao.VueloDAO;
import com.aerolinea.modelo.Vuelo;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PanelVuelos extends JFrame {
    private JTextField idVuelo;
    private JTextField idRuta;
    private JTextField idAvion;
    private JSpinner spinnerSalida;
    private JSpinner spinnerEntrada;
    private JTextField estado;
    private JButton bttnGuardar;

    public PanelVuelos() {
        setTitle("Registro de Vuelos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        idVuelo = new JTextField();
        idRuta = new JTextField();
        idAvion = new JTextField();
        spinnerSalida = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorSalida = new JSpinner.DateEditor(spinnerSalida, "yyyy-MM-dd HH:mm");
        spinnerSalida.setEditor(editorSalida);
        spinnerEntrada = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorEntrada = new JSpinner.DateEditor(spinnerEntrada, "yyyy-MM-dd HH:mm");
        spinnerEntrada.setEditor(editorEntrada);
        estado = new JTextField();
        bttnGuardar = new JButton("Guardar");

        panel.add(new JLabel("Id del Vuelo: "));
        panel.add(idVuelo);
        panel.add(new JLabel("Id de la Ruta: "));
        panel.add(idRuta);
        panel.add(new JLabel("Id del Avion: "));
        panel.add(idAvion);
        panel.add(new JLabel("Fecha de Salida: "));
        panel.add(spinnerSalida);
        panel.add(new JLabel("Fecha de Entrada: "));
        panel.add(spinnerEntrada);
        panel.add(new JLabel("Estado: "));
        panel.add(estado);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            guardarVuelos();
        });

    }
    private void guardarVuelos() {
        try {
            int idv = Integer.parseInt(idVuelo.getText().trim());
            int idr = Integer.parseInt(idRuta.getText().trim());
            int ida = Integer.parseInt(idAvion.getText().trim());
            Date salidaDate = (Date) spinnerSalida.getValue();
            LocalDateTime salida = salidaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Date entradaDate = (Date) spinnerSalida.getValue();
            LocalDateTime entrada = entradaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String est = estado.getText().trim();

            Vuelo vuelo = new Vuelo(idv, idr, ida, salida, entrada, est);
            Connection conexion = ConexionBD.conectar();
            VueloDAO dao = new VueloDAO(conexion);
            dao.insertar(vuelo);

            JOptionPane.showMessageDialog(this, "Vuelo guardado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelVuelos().setVisible(true);
        });
    }
}

