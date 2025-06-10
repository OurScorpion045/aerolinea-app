package com.aerolinea.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String HOST = Config.get("db.host");
    private static final String PORT = Config.get("db.port");
    private static final String DB_NAME = Config.get("db.name");
    private static final String USER = Config.get("db.user");
    private static final String PASSWORD = Config.get("db.password");

    // Conexión SIN base de datos (solo host y puerto)
    public static Connection conectarSinDB() {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/";
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión sin DB: " + e.getMessage());
            return null;
        }
    }

    // Conexión CON base de datos (ya creada)
    public static Connection conectar() {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión con DB: " + e.getMessage());
            return null;
        }
    }
}
