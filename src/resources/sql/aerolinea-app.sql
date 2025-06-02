CREATE DATABASE IF NOT EXISTS aerolinea;
USE aerolinea;

CREATE TABLE IF NOT EXISTS pasajero (
	id_pasajero INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    fecha_nacimiento DATETIME,
    nacionalidad VARCHAR(40),
    pasaporte VARCHAR(40),
    email VARCHAR(50),
    telefono BIGINT
);

CREATE TABLE IF NOT EXISTS aeropuerto (
	id_aeropuerto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(40),
    ciudad VARCHAR(40),
    pais VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS ruta (
	id_ruta INT PRIMARY KEY AUTO_INCREMENT,
    origen INT,
    destino INT,
    duracion INT,
    FOREIGN KEY (origen) REFERENCES aeropuerto(id_aeropuerto),
    FOREIGN KEY (destino) REFERENCES aeropuerto(id_aeropuerto)
);

CREATE TABLE IF NOT EXISTS avion (
	id_avion INT PRIMARY KEY AUTO_INCREMENT,
    modelo VARCHAR(20),
    fabricante VARCHAR(40),
    capacidad INT,
    año_fabricacion DATETIME
);

CREATE TABLE IF NOT EXISTS vuelo (
	id_vuelo INT PRIMARY KEY AUTO_INCREMENT,
    id_ruta INT,
    id_avion INT,
    fecha_salida DATETIME,
    fecha_llegada DATETIME,
    estado VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS empleado (
	id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    cargo VARCHAR(40),
    licencia BOOLEAN,
    fecha_ingreso DATETIME
);

CREATE TABLE IF NOT EXISTS tripulacion (
	id_vuelo INT,
    id_empleado INT,
    rol VARCHAR(50),
    PRIMARY KEY (id_vuelo, id_empleado),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
)

