package com.aerolinea.dao;

import com.aerolinea.modelo.Tripulacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripulacionDAO {
    private final Connection conexion;

    public TripulacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Tripulacion tripulacion) throws SQLException {
        String sql = "INSERT INTO tripulacion (id_vuelo, id_empleado, rol) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tripulacion.getIdVuelo());
            stmt.setInt(2, tripulacion.getIdEmpleado());
            stmt.setString(3, tripulacion.getRol());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Tripulacion tripulacion) throws SQLException {
        String sql = "UPDATE tripulacion SET rol = ? WHERE id_vuelo = ? AND id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tripulacion.getRol());
            stmt.setInt(2, tripulacion.getIdVuelo());
            stmt.setInt(3, tripulacion.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idVuelo, int idEmpleado) throws SQLException {
        String sql = "DELETE FROM tripulacion WHERE id_vuelo = ? AND id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVuelo);
            stmt.setInt(2, idEmpleado);
            stmt.executeUpdate();
        }
    }

    public Tripulacion buscarPorId(int idVuelo, int idEmpleado) throws SQLException {
        String sql = "SELECT * FROM tripulacion WHERE id_vuelo = ? AND id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVuelo);
            stmt.setInt(2, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Tripulacion(
                            rs.getInt("id_vuelo"),
                            rs.getInt("id_empleado"),
                            rs.getString("rol")
                    );
                }
            }
        }
        return null;
    }

    public List<Tripulacion> obtenerTodos() throws SQLException {
        List<Tripulacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tripulacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tripulacion tripulacion = new Tripulacion(
                        rs.getInt("id_vuelo"),
                        rs.getInt("id_empleado"),
                        rs.getString("rol")
                );
                lista.add(tripulacion);
            }
        }
        return lista;
    }
}