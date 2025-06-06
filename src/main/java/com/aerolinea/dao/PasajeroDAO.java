package com.aerolinea.dao;

import com.aerolinea.modelo.Pasajero;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDAO {
    private final Connection conexion;

    public PasajeroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Pasajero pasajero) throws SQLException {
        String sql = "INSERT INTO pasajero (nombre, apellido, fecha_nacimiento, nacionalidad, pasaporte, email, telefono) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, pasajero.getNombre());
            stmt.setString(2, pasajero.getApellido());
            stmt.setTimestamp(3, Timestamp.valueOf(pasajero.getFechaNacimiento()));
            stmt.setString(4, pasajero.getNacionalidad());
            stmt.setString(5, pasajero.getPasaporte());
            stmt.setString(6, pasajero.getEmail());
            stmt.setInt(7, pasajero.getTelefono());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Pasajero pasajero) throws SQLException {
        String sql = "UPDATE pasajero SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, pasaporte = ?, email = ?, telefono = ? WHERE id_pasajero = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, pasajero.getNombre());
            stmt.setString(2, pasajero.getApellido());
            stmt.setTimestamp(3, Timestamp.valueOf(pasajero.getFechaNacimiento()));
            stmt.setString(4, pasajero.getNacionalidad());
            stmt.setString(5, pasajero.getPasaporte());
            stmt.setString(6, pasajero.getEmail());
            stmt.setInt(7, pasajero.getTelefono());
            stmt.setInt(8, pasajero.getIdPasajero());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idPasajero) throws SQLException {
        String sql = "DELETE FROM pasajero WHERE id_pasajero = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPasajero);
            stmt.executeUpdate();
        }
    }

    public Pasajero buscarPorId(int idPasajero) throws SQLException {
        String sql = "SELECT * FROM pasajero WHERE id_pasajero = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPasajero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pasajero(
                            rs.getInt("id_pasajero"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getTimestamp("fecha_nacimiento").toLocalDateTime(),
                            rs.getString("nacionalidad"),
                            rs.getString("pasaporte"),
                            rs.getString("email"),
                            rs.getInt("telefono")
                    );
                }
            }
        }
        return null;
    }

    public List<Pasajero> obtenerTodos() throws SQLException {
        List<Pasajero> lista = new ArrayList<>();
        String sql = "SELECT * FROM pasajero";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pasajero pasajero = new Pasajero(
                        rs.getInt("id_pasajero"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getTimestamp("fecha_nacimiento").toLocalDateTime(),
                        rs.getString("nacionalidad"),
                        rs.getString("pasaporte"),
                        rs.getString("email"),
                        rs.getInt("telefono")
                );
                lista.add(pasajero);
            }
        }
        return lista;
    }
}
