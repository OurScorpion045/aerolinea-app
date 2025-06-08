package com.aerolinea.dao;

import com.aerolinea.modelo.Ruta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {
    private final Connection conexion;

    public RutaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Ruta ruta) throws SQLException {
        String sql = "INSERT INTO ruta (id_aeropuerto_origen, id_aeropuerto_destino, duracion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ruta.getIdAeropuertoOrigen());
            stmt.setInt(2, ruta.getIdAeropuertoDestino());
            stmt.setInt(3, ruta.getDuracion());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Ruta ruta) throws SQLException {
        String sql = "UPDATE ruta SET id_aeropuerto_origen = ?, id_aeropuerto_destino = ?, duracion = ? WHERE id_ruta = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ruta.getIdAeropuertoOrigen());
            stmt.setInt(2, ruta.getIdAeropuertoDestino());
            stmt.setInt(3, ruta.getDuracion());
            stmt.setInt(4, ruta.getIdRuta());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idRuta) throws SQLException {
        String sql = "DELETE FROM ruta WHERE id_ruta = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idRuta);
            stmt.executeUpdate();
        }
    }

    public Ruta buscarPorId(int idRuta) throws SQLException {
        String sql = "SELECT * FROM ruta WHERE id_ruta = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idRuta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ruta(
                            rs.getInt("id_ruta"),
                            rs.getInt("id_aeropuerto_origen"),
                            rs.getInt("id_aeropuerto_destino"),
                            rs.getInt("duracion")
                    );
                }
            }
        }
        return null;
    }

    public List<Ruta> obtenerTodos() throws SQLException {
        List<Ruta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ruta";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ruta ruta = new Ruta(
                        rs.getInt("id_ruta"),
                        rs.getInt("id_aeropuerto_origen"),
                        rs.getInt("id_aeropuerto_destino"),
                        rs.getInt("duracion")
                );
                lista.add(ruta);
            }
        }
        return lista;
    }
}