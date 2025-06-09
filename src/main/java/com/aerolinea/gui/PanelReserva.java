package com.aerolinea.gui;
import com.aerolinea.dao.ReservaDAO;
import com.aerolinea.modelo.Reserva;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PanelReserva extends JFrame{
    private JTextField idReserva;
    private JTextField idVuelo;
    private JSpinner fechaReserva;
    private JTextField estado;
    private JButton bttnGuardar;

    public PanelReserva() {
        setTitle ("Registro de Reserva");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize (350, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        idReserva = new JTextField();
        idVuelo = new JTextField();
        fechaReserva = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorSalida = new JSpinner.DateEditor(fechaReserva, "yyyy-MM-dd HH:mm");
        fechaReserva.setEditor(editorSalida);
        estado = new JTextField();
        bttnGuardar = new JButton("Guardar");

        panel.add(new JLabel("Id de la Reserva: "));
        panel.add(idReserva);
        panel.add(new JLabel("Id el Vuelo: "));
        panel.add(idVuelo);
        panel.add(new JLabel("Fecha de la Reserva: "));
        panel.add(fechaReserva);
        panel.add(new JLabel("Estado de la Reserva: "));
        panel.add(estado);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            guardarReserva();
        });

    }
    private void guardarReserva() {
        try {
            int id_reserva = Integer.parseInt(idReserva.getText().trim());
            int id_vuelo = Integer.parseInt(idVuelo.getText().trim());
            Date fechres = (Date) fechaReserva.getValue();
            LocalDateTime fecha_reserva = fechres.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String est = estado.getText().trim();

            Reserva reserva = new Reserva(id_reserva, id_vuelo, fecha_reserva, est);
            Connection conexion = ConexionBD.conectar();
            ReservaDAO dao = new ReservaDAO(conexion);
            dao.insertar(reserva);

            JOptionPane.showMessageDialog(this, "Reserva guardada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelReserva().setVisible(true);
        });
    }
}

