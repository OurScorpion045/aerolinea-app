package com.aerolinea.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainWindow() {
        setTitle("Sistema de Gestión de Aerolínea");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem itemPasajeros = new JMenuItem("Pasajeros");
        JMenuItem itemVuelos = new JMenuItem("Vuelos");
        JMenuItem itemReservas = new JMenuItem("Reservas");

        itemPasajeros.addActionListener(e -> mostrarPanel("pasajeros"));
        itemVuelos.addActionListener(e -> mostrarPanel("vuelos"));
        itemReservas.addActionListener(e -> mostrarPanel("reservas"));

        menuGestion.add(itemPasajeros);
        menuGestion.add(itemVuelos);
        menuGestion.add(itemReservas);
        menuBar.add(menuGestion);

        setJMenuBar(menuBar);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // mainPanel.add(new PanelPasajeros(), "pasajeros");
        mainPanel.add(new JLabel("Panel de Vuelos en construcción"), "vuelos");
        mainPanel.add(new JLabel("Panel de Reservas en construcción"), "reservas");

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(mainPanel, nombrePanel);
    }

}
