package com.aerolinea.modelo;

public class Equipaje {
    private int idEquipaje;
    private int idCheckin;
    private double peso;
    private String descripcion;
    private double costoExtra;

    public Equipaje(int idEquipaje, int idCheckin, Double peso, String descripcion, Double costoExtra) {
        this.idEquipaje = idEquipaje;
        this.idCheckin = idCheckin;
        this.peso = peso;
        this.descripcion = descripcion;
        this.costoExtra = costoExtra;
    }

    public int getIdEquipaje() {
        return idEquipaje;
    }

    public void setIdEquipaje(int idEquipaje) {
        this.idEquipaje = idEquipaje;
    }

    public int getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(int idCheckin) {
        this.idCheckin = idCheckin;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoExtra() {
        return costoExtra;
    }

    public void setCostoExtra(double costoExtra) {
        this.costoExtra = costoExtra;
    }
}
