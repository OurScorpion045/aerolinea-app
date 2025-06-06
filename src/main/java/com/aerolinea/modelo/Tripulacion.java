package com.aerolinea.modelo;

public class Tripulacion {
    private int idVuelo;
    private int idEmpleado;
    private String rol;

    public Tripulacion(int idVuelo, int idEmpleado, String rol) {
        this.idVuelo = idVuelo;
        this.idEmpleado = idEmpleado;
        this.rol = rol;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
