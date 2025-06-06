package com.aerolinea.util;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();
        if (conn != null) {
            System.out.println("Conexión exitosa!");
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Fallo la conexión.");
        }
    }
}
