package com.aerolinea.dao;

import com.aerolinea.modelo.Avion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvionDAO {
    private final Connection conexion;

    public AvionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Avion avion) throws SQLException {
        String sql = "INSERT INTO avion (modelo, fabricante, capacidad, año_fabricacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, avion.getModelo());
            stmt.setString(2, avion.getFabricante());
            stmt.setInt(3, avion.getCapacidad());
            stmt.setInt(4, avion.getAnioFabricacion());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Avion avion) throws SQLException {
        String sql = "UPDATE avion SET modelo = ?, fabricante = ?, capacidad = ?, año_fabricacion = ? WHERE id_avion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, avion.getModelo());
            stmt.setString(2, avion.getFabricante());
            stmt.setInt(3, avion.getCapacidad());
            stmt.setInt(4, avion.getAnioFabricacion());
            stmt.setInt(5, avion.getIdAvion());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idAvion) throws SQLException {
        String sql = "DELETE FROM avion WHERE id_avion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAvion);
            stmt.executeUpdate();
        }
    }

    public Avion buscarPorId(int idAvion) throws SQLException {
        String sql = "SELECT * FROM avion WHERE id_avion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAvion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Avion(
                            rs.getInt("id_avion"),
                            rs.getString("modelo"),
                            rs.getString("fabricante"),
                            rs.getInt("capacidad"),
                            rs.getInt("año_fabricacion")
                    );
                }
            }
        }
        return null;
    }

    public List<Avion> obtenerTodos() throws SQLException {
        List<Avion> lista = new ArrayList<>();
        String sql = "SELECT * FROM avion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Avion avion = new Avion(
                        rs.getInt("id_avion"),
                        rs.getString("modelo"),
                        rs.getString("fabricante"),
                        rs.getInt("capacidad"),
                        rs.getInt("año_fabricacion")
                );
                lista.add(avion);
            }
        }
        return lista;
    }
}