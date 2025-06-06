package com.aerolinea.dao;

import com.aerolinea.modelo.Aeropuerto;
import com.aerolinea.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoDAO {
    private final Connection conexion;

    public AeropuertoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Aeropuerto aeropuerto) throws SQLException {
        String sql = "INSERT INTO aeropuerto (nombre, ciudad, pais) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, aeropuerto.getNombre());
            stmt.setString(2, aeropuerto.getCiudad());
            stmt.setString(3, aeropuerto.getPais());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Aeropuerto aeropuerto) throws SQLException {
        String sql = "UPDATE pasajero SET nombre = ?, ciudad = ?, pais = ? WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, aeropuerto.getNombre());
            stmt.setString(2, aeropuerto.getCiudad());
            stmt.setString(3, aeropuerto.getPais());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idAeropuerto) throws SQLException {
        String sql = "DELETE FROM aeropuerto WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAeropuerto);
            stmt.executeUpdate();
        }
    }

    public Aeropuerto buscarPorId(int idAeropuerto) throws SQLException {
        String sql = "SELECT * FROM aeropuerto WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAeropuerto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aeropuerto(
                            rs.getInt("id_aeropuerto"),
                            rs.getString("nombre"),
                            rs.getString("ciudad"),
                            rs.getString("pais")
                    );
                }
            }
        }
        return null;
    }

    public List<Aeropuerto> obtenerTodos() throws SQLException {
        List<Aeropuerto> lista = new ArrayList<>();
        String sql = "SELECT * FROM aeropuerto";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aeropuerto aeropuerto = new Aeropuerto(
                        rs.getInt("id_aeropuerto"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("pais")
                );
                lista.add(aeropuerto);
            }
        }
        return lista;
    }
}
