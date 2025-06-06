package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Reserva {
    private int idReserva;
    private int idVuelo;
    private LocalDateTime fechaReserva;
    private String estado;

    public Reserva(int idReserva, int idVuelo, LocalDateTime fechaReserva, String estado) {
        this.idReserva = idReserva;
        this.idVuelo = idVuelo;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}