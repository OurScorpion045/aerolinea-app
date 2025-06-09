package com.aerolinea.gui;
import com.aerolinea.dao.EmpleadoDAO;
import com.aerolinea.modelo.Empleado;
import com.aerolinea.util.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PanelEmpleados extends JFrame{
    private JTextField idEmpleado;
    private JTextField nombre;
    private JTextField apellido;
    private JTextField cargo;
    private JCheckBox licencia;
    private JSpinner fechaIngreso;
    private JButton bttnGuardar;

    public PanelEmpleados() {
        setTitle ("Registro de Empleado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize (350, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        idEmpleado = new JTextField();
        nombre = new JTextField();
        apellido = new JTextField();
        cargo = new JTextField();
        licencia = new JCheckBox();
        bttnGuardar = new JButton("Guardar");
        fechaIngreso = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorIngreso = new JSpinner.DateEditor(fechaIngreso, "yyyy-MM-dd HH:mm");
        fechaIngreso.setEditor(editorIngreso);

        panel.add(new JLabel("Id del Empleado: "));
        panel.add(idEmpleado);
        panel.add(new JLabel("Nombre: "));
        panel.add(nombre);
        panel.add(new JLabel("Apellido: "));
        panel.add(apellido);
        panel.add(new JLabel("Cargo: "));
        panel.add(cargo);
        panel.add(new JLabel("Tiene licencia: "));
        panel.add(licencia);
        panel.add(new JLabel("Fecha de Ingreso: "));
        panel.add(fechaIngreso);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bttnGuardar);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        bttnGuardar.addActionListener((ActionEvent e) -> {
            panelEmpleados();
        });

    }
    private void panelEmpleados() {
        try {
            int id = Integer.parseInt(idEmpleado.getText().trim());
            String nom = nombre.getText().trim();
            String apel = apellido.getText().trim();
            String car = cargo.getText().trim();
            boolean lic = licencia.isSelected();
            Date ingresoDate = (Date) fechaIngreso.getValue();
            LocalDateTime ingreso = ingresoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            Empleado empleado = new Empleado(id, nom, apel, car, lic, ingreso);
            Connection conexion = ConexionBD.conectar();
            EmpleadoDAO dao = new EmpleadoDAO(conexion);
            dao.insertar(empleado);

            JOptionPane.showMessageDialog(this, "Empleado guardado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelEmpleados().setVisible(true);
        });
    }
}
