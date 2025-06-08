package com.aerolinea.modelo;

public class Avion  {
    private int idAvion;
    private String modelo;
    private String fabricante;
    private int capacidad;
    private int anioFabricacion;

    public Avion(int idAvion, String modelo, String fabricante, int capacidad, int anioFabricacion) {
        this.idAvion = idAvion;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.capacidad = capacidad;
        this.anioFabricacion = anioFabricacion;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }
}