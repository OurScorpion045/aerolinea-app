package com.aerolinea.dao;

import com.aerolinea.modelo.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private final Connection conexion;

    public ReservaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reserva (id_vuelo, fecha_reserva, estado) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdVuelo());
            stmt.setTimestamp(2, Timestamp.valueOf(reserva.getFechaReserva()));
            stmt.setString(3, reserva.getEstado());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reserva SET id_vuelo = ?, fecha_reserva = ?, estado = ? WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdVuelo());
            stmt.setTimestamp(2, Timestamp.valueOf(reserva.getFechaReserva()));
            stmt.setString(3, reserva.getEstado());
            stmt.setInt(4, reserva.getIdReserva());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idReserva) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        }
    }

    public Reserva buscarPorId(int idReserva) throws SQLException {
        String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reserva(
                            rs.getInt("id_reserva"),
                            rs.getInt("id_vuelo"),
                            rs.getTimestamp("fecha_reserva").toLocalDateTime(),
                            rs.getString("estado")
                    );
                }
            }
        }
        return null;
    }

    public List<Reserva> obtenerTodos() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_vuelo"),
                        rs.getTimestamp("fecha_reserva").toLocalDateTime(),
                        rs.getString("estado")
                );
                lista.add(reserva);
            }
        }
        return lista;
    }
}