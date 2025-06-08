package com.aerolinea.dao;

import com.aerolinea.modelo.Boleto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAO {
    private final Connection conexion;

    public BoletoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Boleto boleto) throws SQLException {
        String sql = "INSERT INTO boleto (id_reserva, id_pasajero, id_vuelo, id_checkin, id_pago, clase, precio_total, fecha_emision) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, boleto.getIdReserva());
            stmt.setInt(2, boleto.getIdPasajero());
            stmt.setInt(3, boleto.getIdVuelo());
            stmt.setObject(4, boleto.getIdCheckin(), Types.INTEGER);
            stmt.setObject(5, boleto.getIdPago(), Types.INTEGER);
            stmt.setString(6, boleto.getClase());
            stmt.setDouble(7, boleto.getPrecioTotal());
            stmt.setTimestamp(8, Timestamp.valueOf(boleto.getFechaEmision()));
            stmt.executeUpdate();
        }
    }

    public void actualizar(Boleto boleto) throws SQLException {
        String sql = "UPDATE boleto SET id_reserva=?, id_pasajero=?, id_vuelo=?, id_checkin=?, id_pago=?, clase=?, precio_total=?, fecha_emision=? WHERE id_boleto=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, boleto.getIdReserva());
            stmt.setInt(2, boleto.getIdPasajero());
            stmt.setInt(3, boleto.getIdVuelo());
            stmt.setObject(4, boleto.getIdCheckin(), Types.INTEGER);
            stmt.setObject(5, boleto.getIdPago(), Types.INTEGER);
            stmt.setString(6, boleto.getClase());
            stmt.setDouble(7, boleto.getPrecioTotal());
            stmt.setTimestamp(8, Timestamp.valueOf(boleto.getFechaEmision()));
            stmt.setInt(9, boleto.getIdBoleto());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idBoleto) throws SQLException {
        String sql = "DELETE FROM boleto WHERE id_boleto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idBoleto);
            stmt.executeUpdate();
        }
    }

    public Boleto buscarPorId(int idBoleto) throws SQLException {
        String sql = "SELECT * FROM boleto WHERE id_boleto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idBoleto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Boleto(
                            rs.getInt("id_boleto"),
                            rs.getInt("id_reserva"),
                            rs.getInt("id_pasajero"),
                            rs.getInt("id_vuelo"),
                            (Integer) rs.getObject("id_checkin"),
                            (Integer) rs.getObject("id_pago"),
                            rs.getString("clase"),
                            rs.getDouble("precio_total"),
                            rs.getTimestamp("fecha_emision").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<Boleto> obtenerTodos() throws SQLException {
        List<Boleto> lista = new ArrayList<>();
        String sql = "SELECT * FROM boleto";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Boleto boleto = new Boleto(
                        rs.getInt("id_boleto"),
                        rs.getInt("id_reserva"),
                        rs.getInt("id_pasajero"),
                        rs.getInt("id_vuelo"),
                        (Integer) rs.getObject("id_checkin"),
                        (Integer) rs.getObject("id_pago"),
                        rs.getString("clase"),
                        rs.getDouble("precio_total"),
                        rs.getTimestamp("fecha_emision").toLocalDateTime()
                );
                lista.add(boleto);
            }
        }
        return lista;
    }
}