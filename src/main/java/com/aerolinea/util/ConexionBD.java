package com.aerolinea.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://" + Config.get("db.host") + ":" + Config.get("db.port") + "/" + Config.get("db.name");
    private static final String USER = Config.get("db.user");
    private static final String PASSWORD = Config.get("db.password");

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}