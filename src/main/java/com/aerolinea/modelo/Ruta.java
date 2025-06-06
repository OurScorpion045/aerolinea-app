package com.aerolinea.modelo;

public class Ruta {
    private int idRuta;
    private int idAeropuertoOrigen;
    private int idAeropuertoDestino;
    private int duracion;

    public Ruta(int idRuta, int idAeropuertoOrigen, int idAeropuertoDestino, int duracion) {
        this.idRuta = idRuta;
        this.idAeropuertoOrigen = idAeropuertoOrigen;
        this.idAeropuertoDestino = idAeropuertoDestino;
        this.duracion = duracion;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdAeropuertoOrigen() {
        return idAeropuertoOrigen;
    }

    public void setIdAeropuertoOrigen(int idAeropuertoOrigen) {
        this.idAeropuertoOrigen = idAeropuertoOrigen;
    }

    public int getIdAeropuertoDestino() {
        return idAeropuertoDestino;
    }

    public void setIdAeropuertoDestino(int idAeropuertoDestino) {
        this.idAeropuertoDestino = idAeropuertoDestino;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion){
        this.duracion = duracion;
    }
}
