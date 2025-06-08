package com.aerolinea.dao;

import com.aerolinea.modelo.Vuelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO {
    private final Connection conexion;

    public VueloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Vuelo vuelo) throws SQLException {
        String sql = "INSERT INTO vuelo (id_ruta, id_avion, fecha_salida, fecha_llegada, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, vuelo.getIdRuta());
            stmt.setInt(2, vuelo.getIdAvion());
            stmt.setTimestamp(3, Timestamp.valueOf(vuelo.getFechaSalida()));
            stmt.setTimestamp(4, Timestamp.valueOf(vuelo.getFechaLlegada()));
            stmt.setString(5, vuelo.getEstado());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Vuelo vuelo) throws SQLException {
        String sql = "UPDATE vuelo SET id_ruta = ?, id_avion = ?, fecha_salida = ?, fecha_llegada = ?, estado = ? WHERE id_vuelo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, vuelo.getIdRuta());
            stmt.setInt(2, vuelo.getIdAvion());
            stmt.setTimestamp(3, Timestamp.valueOf(vuelo.getFechaSalida()));
            stmt.setTimestamp(4, Timestamp.valueOf(vuelo.getFechaLlegada()));
            stmt.setString(5, vuelo.getEstado());
            stmt.setInt(6, vuelo.getIdVuelo());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idVuelo) throws SQLException {
        String sql = "DELETE FROM vuelo WHERE id_vuelo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVuelo);
            stmt.executeUpdate();
        }
    }

    public Vuelo buscarPorId(int idVuelo) throws SQLException {
        String sql = "SELECT * FROM vuelo WHERE id_vuelo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVuelo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Vuelo(
                            rs.getInt("id_vuelo"),
                            rs.getInt("id_ruta"),
                            rs.getInt("id_avion"),
                            rs.getTimestamp("fecha_salida").toLocalDateTime(),
                            rs.getTimestamp("fecha_llegada").toLocalDateTime(),
                            rs.getString("estado")
                    );
                }
            }
        }
        return null;
    }

    public List<Vuelo> obtenerTodos() throws SQLException {
        List<Vuelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vuelo";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vuelo vuelo = new Vuelo(
                        rs.getInt("id_vuelo"),
                        rs.getInt("id_ruta"),
                        rs.getInt("id_avion"),
                        rs.getTimestamp("fecha_salida").toLocalDateTime(),
                        rs.getTimestamp("fecha_llegada").toLocalDateTime(),
                        rs.getString("estado")
                );
                lista.add(vuelo);
            }
        }
        return lista;
    }
}
