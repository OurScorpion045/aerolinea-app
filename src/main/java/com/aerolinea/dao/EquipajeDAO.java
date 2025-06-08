package com.aerolinea.dao;

import com.aerolinea.modelo.Equipaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipajeDAO {
    private final Connection conexion;

    public EquipajeDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Equipaje equipaje) throws SQLException {
        String sql = "INSERT INTO equipaje (id_checkin, peso, descripcion, costo_extra) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, equipaje.getIdCheckin());
            stmt.setDouble(2, equipaje.getPeso());
            stmt.setString(3, equipaje.getDescripcion());
            stmt.setDouble(4, equipaje.getCostoExtra());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Equipaje equipaje) throws SQLException {
        String sql = "UPDATE equipaje SET id_checkin = ?, peso = ?, descripcion = ?, costo_extra = ? WHERE id_equipaje = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, equipaje.getIdCheckin());
            stmt.setDouble(2, equipaje.getPeso());
            stmt.setString(3, equipaje.getDescripcion());
            stmt.setDouble(4, equipaje.getCostoExtra());
            stmt.setInt(5, equipaje.getIdEquipaje());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idEquipaje) throws SQLException {
        String sql = "DELETE FROM equipaje WHERE id_equipaje = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEquipaje);
            stmt.executeUpdate();
        }
    }

    public Equipaje buscarPorId(int idEquipaje) throws SQLException {
        String sql = "SELECT * FROM equipaje WHERE id_equipaje = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEquipaje);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Equipaje(
                            rs.getInt("id_equipaje"),
                            rs.getInt("id_checkin"),
                            rs.getDouble("peso"),
                            rs.getString("descripcion"),
                            rs.getDouble("costo_extra")
                    );
                }
            }
        }
        return null;
    }

    public List<Equipaje> obtenerTodos() throws SQLException {
        List<Equipaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipaje";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipaje equipaje = new Equipaje(
                        rs.getInt("id_equipaje"),
                        rs.getInt("id_checkin"),
                        rs.getDouble("peso"),
                        rs.getString("descripcion"),
                        rs.getDouble("costo_extra")
                );
                lista.add(equipaje);
            }
        }
        return lista;
    }
}