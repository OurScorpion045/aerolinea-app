DROP DATABASE IF EXISTS aerolinea;
CREATE DATABASE aerolinea;
USE aerolinea;

CREATE TABLE IF NOT EXISTS pasajero (
	id_pasajero INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    fecha_nacimiento DATETIME,
    nacionalidad VARCHAR(100),
    pasaporte VARCHAR(100),
    email VARCHAR(100),
    telefono VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS aeropuerto (
	id_aeropuerto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    ciudad VARCHAR(100),
    pais VARCHAR(100)
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
    modelo VARCHAR(100),
    fabricante VARCHAR(100),
    capacidad INT,
    año_fabricacion YEAR
);

CREATE TABLE IF NOT EXISTS vuelo (
	id_vuelo INT PRIMARY KEY AUTO_INCREMENT,
    id_ruta INT,
    id_avion INT,
    fecha_salida DATETIME,
    fecha_llegada DATETIME,
    estado VARCHAR(100),
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
    FOREIGN KEY (id_avion) REFERENCES avion(id_avion)
);

CREATE TABLE IF NOT EXISTS empleado (
	id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    cargo VARCHAR(100),
    licencia BOOLEAN,
    fecha_ingreso DATETIME
);

CREATE TABLE IF NOT EXISTS tripulacion (
	id_vuelo INT,
    id_empleado INT,
    rol VARCHAR(100),
    PRIMARY KEY (id_vuelo, id_empleado),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

CREATE TABLE IF NOT EXISTS reserva (
	id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_vuelo INT,
    fecha_reserva DATETIME,
    estado VARCHAR(100),
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
    metodo_pago VARCHAR(100),
    monto float,
    fecha_pago DATETIME,
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

CREATE TABLE IF NOT EXISTS checkin (
	id_checkin INT PRIMARY KEY AUTO_INCREMENT,
    id_pasajero INT,
    id_reserva INT,
    fecha_checkin DATETIME,
    asiento VARCHAR(100),
    puerta_embarque INT,
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

CREATE TABLE IF NOT EXISTS equipaje (
	id_equipaje INT PRIMARY KEY AUTO_INCREMENT,
    id_checkin INT,
    peso INT,
    descripcion VARCHAR(500),
    costo_extra INT,
    FOREIGN KEY (id_checkin) REFERENCES checkin(id_checkin)
);

CREATE TABLE IF NOT EXISTS boleto (
    id_boleto INT PRIMARY KEY AUTO_INCREMENT,
    id_reserva INT NOT NULL,
    id_pasajero INT NOT NULL,
    id_vuelo INT NOT NULL,
    id_checkin INT,
    id_pago INT,
    clase VARCHAR(100),
    precio_total DOUBLE,
    fecha_emision DATETIME,

    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo),
    FOREIGN KEY (id_checkin) REFERENCES checkin(id_checkin),
    FOREIGN KEY (id_pago) REFERENCES pago(id_pago)
);

-- Datos de prueba

INSERT INTO pasajero (nombre, apellido, fecha_nacimiento, nacionalidad, pasaporte, email, telefono)
VALUES
('Juan', 'Pérez', '1990-05-20 00:00:00', 'Mexicana', 'MX123456', 'juan.perez@example.com', '5551234567'),
('Ana', 'López', '1985-10-12 00:00:00', 'Colombiana', 'CO789101', 'ana.lopez@example.com', '5559876543');

INSERT INTO aeropuerto (nombre, ciudad, pais)
VALUES
('Aeropuerto Internacional de la CDMX', 'Ciudad de México', 'México'),
('Aeropuerto Internacional de Medellín', 'Medellín', 'Colombia');

INSERT INTO ruta (id_aeropuerto_origen, id_aeropuerto_destino, duracion)
VALUES (1, 2, 180);

INSERT INTO avion (modelo, fabricante, capacidad, año_fabricacion)
VALUES ('737 MAX', 'Boeing', 180, 2019);

INSERT INTO vuelo (id_ruta, id_avion, fecha_salida, fecha_llegada, estado)
VALUES (1, 1, '2025-06-10 08:00:00', '2025-06-10 11:00:00', 'Programado');

INSERT INTO reserva (id_vuelo, fecha_reserva, estado)
VALUES (1, '2025-06-08 14:00:00', 'Confirmada');

INSERT INTO detalle_reserva (id_reserva, id_pasajero)
VALUES (1, 1);

INSERT INTO pago (id_reserva, metodo_pago, monto, fecha_pago)
VALUES (1, 'Tarjeta de crédito', 2500.00, '2025-06-08 15:00:00');

INSERT INTO checkin (id_pasajero, id_reserva, fecha_checkin, asiento, puerta_embarque)
VALUES (1, 1, '2025-06-09 06:00:00', '12A', 3);

INSERT INTO boleto (id_reserva, id_pasajero, id_vuelo, id_checkin, id_pago, clase, precio_total, fecha_emision)
VALUES (1, 1, 1, 1, 1, 'Económica', 2500.00, '2025-06-09 06:10:00');
