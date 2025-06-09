package com.aerolinea.gui;

import com.aerolinea.util.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Connection conexion;

    public MainWindow() {
        setTitle("Sistema de Gestión de Aerolínea");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Conexión a la base de datos
        conexion = ConexionBD.conectar();

        // Menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGestion = new JMenu("Gestión");

        JMenuItem itemPasajeros = new JMenuItem("Pasajeros");
        JMenuItem itemVuelos = new JMenuItem("Vuelos");
        JMenuItem itemReservas = new JMenuItem("Reservas");
        JMenuItem itemBoletos = new JMenuItem("Boletos");
        JMenuItem itemAeropuertos = new JMenuItem("Aeropuertos");
        JMenuItem itemRutas = new JMenuItem("Rutas");
        JMenuItem itemAviones = new JMenuItem("Aviones");
        JMenuItem itemEmpleados = new JMenuItem("Empleados");
        JMenuItem itemTripulacion = new JMenuItem("Tripulacion");
        JMenuItem itemPagos = new JMenuItem("Pagos");
        JMenuItem itemCheckin = new JMenuItem("Checkin");
        JMenuItem itemEquipaje = new JMenuItem("Equipaje");



        itemPasajeros.addActionListener(e -> mostrarPanel("pasajeros"));
        itemVuelos.addActionListener(e -> mostrarPanel("vuelos"));
        itemReservas.addActionListener(e -> mostrarPanel("reservas"));
        itemBoletos.addActionListener(e -> mostrarPanel("boletos"));
        itemAeropuertos.addActionListener(e -> mostrarPanel("aeropuertos"));
        itemRutas.addActionListener(e -> mostrarPanel("Rutas"));
        itemAviones.addActionListener(e -> mostrarPanel("Aviones"));
        itemEmpleados.addActionListener(e -> mostrarPanel("Empleados"));
        itemTripulacion.addActionListener(e -> mostrarPanel("Tripulacion"));
        itemPagos.addActionListener(e -> mostrarPanel("Pagos"));
        itemCheckin.addActionListener(e -> mostrarPanel("Checkin"));
        itemEquipaje.addActionListener(e -> mostrarPanel("Equipaje"));






        menuGestion.add(itemPasajeros);
        menuGestion.add(itemVuelos);
        menuGestion.add(itemReservas);
        menuGestion.add(itemBoletos);
        menuGestion.add(itemAeropuertos);
        menuGestion.add(itemRutas);
        menuGestion.add(itemAviones);
        menuGestion.add(itemEmpleados);
        menuGestion.add(itemTripulacion);
        menuGestion.add(itemPagos);
        menuGestion.add(itemCheckin);
        menuGestion.add(itemEquipaje);
        menuBar.add(menuGestion);
        setJMenuBar(menuBar);

        // Panel principal con CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Agregar paneles
        mainPanel.add(new PanelPasajeros(conexion), "pasajeros");
        mainPanel.add(new PanelVuelos(conexion), "vuelos");           // Puedes crear este panel después
        mainPanel.add(new PanelReserva(conexion), "reservas");       // Puedes crear este panel después
        mainPanel.add(new PanelBoletos(conexion), "boletos");
        mainPanel.add(new PanelAeropuertos(conexion), "aeropuertos");
        mainPanel.add(new PanelRuta(conexion), "Rutas");
        mainPanel.add(new PanelAvion(conexion), "Aviones");
        mainPanel.add(new PanelEmpleados(conexion), "Empleados");
        mainPanel.add(new PanelTripulacion(conexion), "Tripulacion");
        mainPanel.add(new PanelPagos(conexion), "Pagos");
        mainPanel.add(new PanelCheckin(conexion), "Checkin");
        mainPanel.add(new PanelEquipaje(conexion), "Equipaje");




        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(mainPanel, nombrePanel);
        setTitle("Sistema de Gestión de Aerolínea - " + nombrePanel);
    }
}