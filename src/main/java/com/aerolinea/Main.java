package com.aerolinea;

import com.aerolinea.gui.MainWindow;
import com.aerolinea.util.ConexionBD;
import com.aerolinea.util.DatabaseInitializer;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar base de datos (crear si no existe)
        try (Connection conn = ConexionBD.conectarSinDB()) {
            DatabaseInitializer.inicializarBaseDeDatos(conn, "sql/aerolinea-app.sql");
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos:");
            e.printStackTrace();
            return; // No continúa si falla
        }

        // 2. Conectarse ya a la base de datos creada
        try (Connection conn = ConexionBD.conectar()) {
            // Lógica de aplicación principal
            new MainWindow();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación:");
            e.printStackTrace();
        }
    }
}
