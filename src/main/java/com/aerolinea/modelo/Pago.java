package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Pago {
    private int idPago;
    private int idReserva;
    private String metodoPago;
    private Double monto;
    private LocalDateTime fechaPago;

    public Pago(int idPago, int idReserva, String metodoPago, Double monto, LocalDateTime fechaPago) {
        this.idPago = idPago;
        this.idReserva = idReserva;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
}
