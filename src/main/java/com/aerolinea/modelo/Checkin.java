package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Checkin {
    private int idCheckin;
    private int idPasajero;
    private int idReserva;
    private LocalDateTime fechaCheckin;
    private String asiento;
    private int puertaEmbarque;

    public Checkin(int idCheckin, int idPasajero, int idReserva, LocalDateTime fechaCheckin, String asiento, int puertaEmbarque) {
        this.idCheckin = idCheckin;
        this.idPasajero = idPasajero;
        this.idReserva = idReserva;
        this.fechaCheckin = fechaCheckin;
        this.asiento = asiento;
        this.puertaEmbarque = puertaEmbarque;
    }

    public int getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(int idCheckin) {
        this.idCheckin = idCheckin;
    }

    public int getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getFechaCheckin() {
        return fechaCheckin;
    }

    public void setFechaCheckin(LocalDateTime fechaCheckin) {
        this.fechaCheckin = fechaCheckin;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public int getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(int puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }
}
