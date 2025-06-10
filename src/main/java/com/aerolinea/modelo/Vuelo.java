package com.aerolinea.modelo;

import java.time.LocalDateTime;

/**
 * Clase modelo que representa un Vuelo.
 */
public class Vuelo {
    private int idVuelo;
    private int idRuta;
    private int idAvion;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private String estado;

    // Constructor completo
    public Vuelo(int idVuelo, int idRuta, int idAvion, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, String estado) {
        this.idVuelo = idVuelo;
        this.idRuta = idRuta;
        this.idAvion = idAvion;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
    }

    // Getters y Setters
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

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "idVuelo=" + idVuelo +
                ", idRuta=" + idRuta +
                ", idAvion=" + idAvion +
                ", fechaSalida=" + fechaSalida +
                ", fechaLlegada=" + fechaLlegada +
                ", estado='" + estado + '\'' +
                '}';
    }
}
