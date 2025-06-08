package com.aerolinea.dao;

import com.aerolinea.modelo.Pago;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
    private final Connection conexion;

    public PagoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Pago pago) throws SQLException {
        String sql = "INSERT INTO pago (id_reserva, metodo_pago, monto, fecha_pago) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pago.getIdReserva());
            stmt.setString(2, pago.getMetodoPago());
            stmt.setDouble(3, pago.getMonto());
            stmt.setTimestamp(4, Timestamp.valueOf(pago.getFechaPago()));
            stmt.executeUpdate();
        }
    }

    public void actualizar(Pago pago) throws SQLException {
        String sql = "UPDATE pago SET id_reserva = ?, metodo_pago = ?, monto = ?, fecha_pago = ? WHERE id_pago = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pago.getIdReserva());
            stmt.setString(2, pago.getMetodoPago());
            stmt.setDouble(3, pago.getMonto());
            stmt.setTimestamp(4, Timestamp.valueOf(pago.getFechaPago()));
            stmt.setInt(5, pago.getIdPago());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idPago) throws SQLException {
        String sql = "DELETE FROM pago WHERE id_pago = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPago);
            stmt.executeUpdate();
        }
    }

    public Pago buscarPorId(int idPago) throws SQLException {
        String sql = "SELECT * FROM pago WHERE id_pago = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPago);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pago(
                            rs.getInt("id_pago"),
                            rs.getInt("id_reserva"),
                            rs.getString("metodo_pago"),
                            rs.getDouble("monto"),
                            rs.getTimestamp("fecha_pago").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<Pago> obtenerTodos() throws SQLException {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pago pago = new Pago(
                        rs.getInt("id_pago"),
                        rs.getInt("id_reserva"),
                        rs.getString("metodo_pago"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha_pago").toLocalDateTime()
                );
                lista.add(pago);
            }
        }
        return lista;
    }
}