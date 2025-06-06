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

    }
}
