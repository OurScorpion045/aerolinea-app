package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Boleto {
    private int idBoleto;
    private int idReserva;
    private int idPasajero;
    private int idVuelo;
    private Integer idCheckin;
    private Integer idPago;
    private String clase;
    private double precioTotal;
    private LocalDateTime fechaEmision;

    public Boleto(int idBoleto, int idReserva, int idPasajero, int idVuelo, Integer idCheckin, Integer idPago, String clase, double precioTotal, LocalDateTime fechaEmision) {
        this.idBoleto = idBoleto;
        this.idReserva = idReserva;
        this.idPasajero = idPasajero;
        this.idVuelo = idVuelo;
        this.idCheckin = idCheckin;
        this.idPago = idPago;
        this.clase = clase;
        this.precioTotal = precioTotal;
        this.fechaEmision = fechaEmision;
    }

    public int getIdBoleto() { return idBoleto; }
    public void setIdBoleto(int idBoleto) { this.idBoleto = idBoleto; }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdPasajero() { return idPasajero; }
    public void setIdPasajero(int idPasajero) { this.idPasajero = idPasajero; }

    public int getIdVuelo() { return idVuelo; }
    public void setIdVuelo(int idVuelo) { this.idVuelo = idVuelo; }

    public Integer getIdCheckin() { return idCheckin; }
    public void setIdCheckin(Integer idCheckin) { this.idCheckin = idCheckin; }

    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }

    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
}