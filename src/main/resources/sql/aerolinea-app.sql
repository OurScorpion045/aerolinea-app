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
    telefono VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS aeropuerto (
	id_aeropuerto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(40),
    ciudad VARCHAR(40),
    pais VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS ruta (
	id_ruta INT PRIMARY KEY AUTO_INCREMENT,
    id_aeropuerto_origen INT,
    id_aeropuerto_destino INT,
    duracion INT,
    FOREIGN KEY (id_aeropuerto_origen) REFERENCES aeropuerto(id_aeropuerto),
    FOREIGN KEY (id_aeropuerto_destino) REFERENCES aeropuerto(id_aeropuerto)
);

CREATE TABLE IF NOT EXISTS avion (
	id_avion INT PRIMARY KEY AUTO_INCREMENT,
    modelo VARCHAR(20),
    fabricante VARCHAR(40),
    capacidad INT,
    año_fabricacion YEAR
);

CREATE TABLE IF NOT EXISTS vuelo (
	id_vuelo INT PRIMARY KEY AUTO_INCREMENT,
    id_ruta INT,
    id_avion INT,
    fecha_salida DATETIME,
    fecha_llegada DATETIME,
    estado VARCHAR(40),
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
    FOREIGN KEY (id_ruta) REFERENCES avion(id_avion)
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
);

CREATE TABLE IF NOT EXISTS reserva (
	id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_vuelo INT,
    fecha_reserva DATETIME,
    estado VARCHAR(50),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo)
);

CREATE TABLE IF NOT EXISTS detalle_reserva (
	id_reserva INT,
    id_pasajero INT,
    PRIMARY KEY (id_reserva, id_pasajero),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero)
);

CREATE TABLE IF NOT EXISTS pago (
	id_pago INT PRIMARY KEY AUTO_INCREMENT,
    id_reserva INT,
    metodo_pago VARCHAR(50),
    monto float,
    fecha_pago DATETIME,
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

CREATE TABLE IF NOT EXISTS checkin (
	id_checkin INT PRIMARY KEY AUTO_INCREMENT,
    id_pasajero INT,
    id_reserva INT,
    fecha_checkin DATETIME,
    asiento VARCHAR(3),
    puerta_embarque INT,
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

CREATE TABLE IF NOT EXISTS equipaje (
	id_equipaje INT PRIMARY KEY AUTO_INCREMENT,
    id_checkin INT,
    peso INT,
    descripcion VARCHAR(250),
    costo_extra INT,
    FOREIGN KEY (id_checkin) REFERENCES checkin(id_checkin)
)