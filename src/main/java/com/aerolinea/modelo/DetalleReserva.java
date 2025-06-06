package com.aerolinea.modelo;

public class DetalleReserva {
    private int idReserva;
    private int idPasajero;

    public DetalleReserva(int idReserva, int idPasajero) {
        this.idReserva = idReserva;
        this.idPasajero = idPasajero;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String toString() {
        return "DetalleReserva{" +
                "idReserva=" + idReserva +
                ", idPasajero=" + idPasajero +
                "}";
    }
}
