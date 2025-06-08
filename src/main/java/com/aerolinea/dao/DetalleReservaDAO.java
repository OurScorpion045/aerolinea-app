package com.aerolinea.dao;

import com.aerolinea.modelo.DetalleReserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleReservaDAO {
    private final Connection conexion;

    public DetalleReservaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(DetalleReserva detalle) throws SQLException {
        String sql = "INSERT INTO detalle_reserva (id_reserva, id_pasajero) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdReserva());
            stmt.setInt(2, detalle.getIdPasajero());
            stmt.executeUpdate();
        }
    }

    public void actualizar(DetalleReserva detalle) throws SQLException {
        String sql = "UPDATE detalle_reserva SET id_pasajero = ? WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdPasajero());
            stmt.setInt(2, detalle.getIdReserva());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idReserva) throws SQLException {
        String sql = "DELETE FROM detalle_reserva WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        }
    }

    public DetalleReserva buscarPorId(int idReserva) throws SQLException {
        String sql = "SELECT * FROM detalle_reserva WHERE id_reserva = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DetalleReserva(
                            rs.getInt("id_reserva"),
                            rs.getInt("id_pasajero")
                    );
                }
            }
        }
        return null;
    }

    public List<DetalleReserva> obtenerTodos() throws SQLException {
        List<DetalleReserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_reserva";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DetalleReserva detalle = new DetalleReserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_pasajero")
                );
                lista.add(detalle);
            }
        }
        return lista;
    }
}