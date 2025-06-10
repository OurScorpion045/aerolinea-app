package com.aerolinea.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void inicializarBaseDeDatos(Connection conn, String rutaScriptSQL) {
        try {
            InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream(rutaScriptSQL);
            if (inputStream == null) {
                throw new IllegalArgumentException("No se encontró el archivo: " + rutaScriptSQL);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder script = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                script.append(linea).append("\n");
            }

            String[] instrucciones = script.toString().split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String instruccion : instrucciones) {
                    instruccion = instruccion.trim();
                    if (!instruccion.isEmpty()) {
                        stmt.execute(instruccion);
                    }
                }
            }

            System.out.println("Base de datos inicializada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
