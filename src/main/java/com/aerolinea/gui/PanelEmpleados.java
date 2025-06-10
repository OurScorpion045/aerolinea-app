package com.aerolinea.gui;

import com.aerolinea.dao.EmpleadoDAO;
import com.aerolinea.modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PanelEmpleados extends JPanel {
    private final EmpleadoDAO empleadoDAO;
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(10);
    private final JTextField txtApellido = new JTextField(10);
    private final JTextField txtCargo = new JTextField(10);
    private final JCheckBox chkLicencia = new JCheckBox("Licencia");
    private final JTextField txtFechaIngreso = new JTextField(15);
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable(tableModel);

    public PanelEmpleados(Connection conexion) {
        this.empleadoDAO = new EmpleadoDAO(conexion);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Columna 1
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("ID Empleado:"), gbc);
        gbc.gridx = 1; formPanel.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; formPanel.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; formPanel.add(txtCargo, gbc);

        // Columna 2
        gbc.gridx = 2; gbc.gridy = 0; formPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3; formPanel.add(txtApellido, gbc);
        gbc.gridx = 2; gbc.gridy++; formPanel.add(new JLabel("Fecha Ingreso (yyyy-MM-ddTHH:mm):"), gbc);
        gbc.gridx = 3; formPanel.add(txtFechaIngreso, gbc);
        gbc.gridx = 2; gbc.gridy++; formPanel.add(chkLicencia, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnBuscar);
        add(buttonPanel, BorderLayout.CENTER);

        // Tabla
        tableModel.setColumnIdentifiers(new String[]{
                "ID", "Nombre", "Apellido", "Cargo", "Licencia", "Fecha Ingreso"
        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 200));
        add(scrollPane, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> agregarEmpleado());
        btnActualizar.addActionListener(e -> actualizarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnBuscar.addActionListener(e -> buscarEmpleado());

        cargarEmpleados();
    }

    private void cargarEmpleados() {
        try {
            List<Empleado> empleados = empleadoDAO.obtenerTodos();
            tableModel.setRowCount(0);
            for (Empleado emp : empleados) {
                tableModel.addRow(new Object[]{
                        emp.getIdEmpleado(), emp.getNombre(), emp.getApellido(),
                        emp.getCargo(), emp.getLicencia(), emp.getFechaIngreso()
                });
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar empleados: " + ex.getMessage());
        }
    }

    private void agregarEmpleado() {
        try {
            Empleado emp = new Empleado(
                    0,
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtCargo.getText(),
                    chkLicencia.isSelected(),
                    LocalDateTime.parse(txtFechaIngreso.getText())
            );
            empleadoDAO.insertar(emp);
            cargarEmpleados();
        } catch (Exception ex) {
            mostrarError("Error al agregar: " + ex.getMessage());
        }
    }

    private void actualizarEmpleado() {
        try {
            Empleado emp = new Empleado(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtCargo.getText(),
                    chkLicencia.isSelected(),
                    LocalDateTime.parse(txtFechaIngreso.getText())
            );
            empleadoDAO.actualizar(emp);
            cargarEmpleados();
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            int id = Integer.parseInt(txtId.getText());
            empleadoDAO.eliminar(id);
            cargarEmpleados();
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
        }
    }

    private void buscarEmpleado() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Empleado emp = empleadoDAO.buscarPorId(id);
            if (emp != null) {
                txtNombre.setText(emp.getNombre());
                txtApellido.setText(emp.getApellido());
                txtCargo.setText(emp.getCargo());
                chkLicencia.setSelected(emp.getLicencia());
                txtFechaIngreso.setText(emp.getFechaIngreso().toString());
            } else {
                mostrarError("Empleado no encontrado");
            }
        } catch (Exception ex) {
            mostrarError("Error al buscar: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}