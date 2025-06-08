package com.aerolinea.dao;

import com.aerolinea.modelo.Checkin;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckinDAO {
    private final Connection conexion;

    public CheckinDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Checkin checkin) throws SQLException {
        String sql = "INSERT INTO checkin (id_pasajero, id_reserva, fecha_checkin, asiento, puerta_embarque) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, checkin.getIdPasajero());
            stmt.setInt(2, checkin.getIdReserva());
            stmt.setTimestamp(3, Timestamp.valueOf(checkin.getFechaCheckin()));
            stmt.setString(4, checkin.getAsiento());
            stmt.setInt(5, checkin.getPuertaEmbarque());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Checkin checkin) throws SQLException {
        String sql = "UPDATE checkin SET id_pasajero = ?, id_reserva = ?, fecha_checkin = ?, asiento = ?, puerta_embarque = ? WHERE id_checkin = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, checkin.getIdPasajero());
            stmt.setInt(2, checkin.getIdReserva());
            stmt.setTimestamp(3, Timestamp.valueOf(checkin.getFechaCheckin()));
            stmt.setString(4, checkin.getAsiento());
            stmt.setInt(5, checkin.getPuertaEmbarque());
            stmt.setInt(6, checkin.getIdCheckin());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idCheckin) throws SQLException {
        String sql = "DELETE FROM checkin WHERE id_checkin = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCheckin);
            stmt.executeUpdate();
        }
    }

    public Checkin buscarPorId(int idCheckin) throws SQLException {
        String sql = "SELECT * FROM checkin WHERE id_checkin = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCheckin);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Checkin(
                            rs.getInt("id_checkin"),
                            rs.getInt("id_pasajero"),
                            rs.getInt("id_reserva"),
                            rs.getTimestamp("fecha_checkin").toLocalDateTime(),
                            rs.getString("asiento"),
                            rs.getInt("puerta_embarque")
                    );
                }
            }
        }
        return null;
    }

    public List<Checkin> obtenerTodos() throws SQLException {
        List<Checkin> lista = new ArrayList<>();
        String sql = "SELECT * FROM checkin";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Checkin checkin = new Checkin(
                        rs.getInt("id_checkin"),
                        rs.getInt("id_pasajero"),
                        rs.getInt("id_reserva"),
                        rs.getTimestamp("fecha_checkin").toLocalDateTime(),
                        rs.getString("asiento"),
                        rs.getInt("puerta_embarque")
                );
                lista.add(checkin);
            }
        }
        return lista;
    }
}