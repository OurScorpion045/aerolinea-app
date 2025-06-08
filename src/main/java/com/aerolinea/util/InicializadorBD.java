package com.aerolinea.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class InicializadorBD {
    public static void inicializar() {
        String dbName = Config.get("db.name");
        String urlSinBD = "jdbc:mysql://" + Config.get("db.host") + ":" + Config.get("db.port") + "/";
        String user = Config.get("db.user");
        String pass = Config.get("db.password");
        String scriptPath = Config.get("db.sqlscript");

        try (Connection conn = DriverManager.getConnection(urlSinBD, user, pass);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            stmt.execute("USE " + dbName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    InicializadorBD.class.getClassLoader().getResourceAsStream(scriptPath)))) {

                if (br == null) {
                    throw new RuntimeException("No se encontró el archivo SQL: " + scriptPath);
                }

                StringBuilder sql = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sql.append(line).append("\n");
                }

                for (String query : sql.toString().split(";")) {
                    if (!query.trim().isEmpty()) {
                        stmt.execute(query);
                    }
                }

                System.out.println("Base de datos y tablas listas.");
            }

        } catch (Exception e) {
            System.out.println("Error al inicializar base de datos: " + e.getMessage());
        }
    }
}