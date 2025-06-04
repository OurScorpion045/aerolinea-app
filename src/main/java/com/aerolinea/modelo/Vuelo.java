package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Vuelo {
    private int idVuelo;
    private int idRuta;
    private int idAvion;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLLegada;
    private String estado;

    public Vuelo(int idVuelo, int idRuta, int idAvion, LocalDateTime fechaSalida, LocalDateTime fechaLLegada) {
        this.idVuelo = idVuelo;
        this.idRuta = idRuta;
        this.idAvion = idAvion;
        this.fechaSalida = fechaSalida;
        this.fechaLLegada = fechaLLegada;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalDateTime getFechaLLegada() {
        return fechaLLegada;
    }

    public void setFechaLLegada(LocalDateTime fechaLLegada) {
        this.fechaLLegada = fechaLLegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}