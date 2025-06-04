package com.aerolinea.modelo;

public class Aeropuerto {
    private int idAeropuerto;
    private String nombre;
    private String ciudad;
    private String pais;

    public Aeropuerto(int idAeropuerto, String nombre, String ciudad, String pais) {
        this.idAeropuerto = idAeropuerto;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public int getIdAeropuerto() {
        return idAeropuerto;
    }

    public void setIdAeropuerto(int idAeropuerto) {
        this.idAeropuerto = idAeropuerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
