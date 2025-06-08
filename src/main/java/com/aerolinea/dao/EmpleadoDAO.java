package com.aerolinea.dao;

import com.aerolinea.modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private final Connection conexion;

    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleado (nombre, apellido, cargo, licencia, fecha_ingreso) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getCargo());
            stmt.setBoolean(4, empleado.getLicencia());
            stmt.setTimestamp(5, Timestamp.valueOf(empleado.getFechaIngreso()));
            stmt.executeUpdate();
        }
    }

    public void actualizar(Empleado empleado) throws SQLException {
        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, cargo = ?, licencia = ?, fecha_ingreso = ? WHERE id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getCargo());
            stmt.setBoolean(4, empleado.getLicencia());
            stmt.setTimestamp(5, Timestamp.valueOf(empleado.getFechaIngreso()));
            stmt.setInt(6, empleado.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.executeUpdate();
        }
    }

    public Empleado buscarPorId(int idEmpleado) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id_empleado"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("cargo"),
                            rs.getBoolean("licencia"),
                            rs.getTimestamp("fecha_ingreso").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<Empleado> obtenerTodos() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo"),
                        rs.getBoolean("licencia"),
                        rs.getTimestamp("fecha_ingreso").toLocalDateTime()
                );
                lista.add(empleado);
            }
        }
        return lista;
    }
}