package com.aerolinea.modelo;

import java.time.LocalDateTime;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String cargo;
    private Boolean licencia;
    private LocalDateTime fechaIngreso;

    public Empleado(int idEmpleado, String nombre, String apellido, String cargo, Boolean licencia, LocalDateTime fechaIngreso) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.licencia = licencia;
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getLicencia() {
        return licencia;
    }

    public void setLicencia(Boolean licencia) {
        this.licencia = licencia;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
