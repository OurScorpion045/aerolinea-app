CREATE DATABASE IF NOT EXISTS aerolinea;
USE aerolinea;

DROP TABLE IF EXISTS boleto, equipaje, checkin, pago, detalle_reserva, reserva, tripulacion, empleado, vuelo, avion, ruta, aeropuerto, pasajero;

CREATE TABLE pasajero (
	id_pasajero INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    fecha_nacimiento DATETIME,
    nacionalidad VARCHAR(100),
    pasaporte VARCHAR(100),
    email VARCHAR(100),
    telefono VARCHAR(100)
);

INSERT INTO pasajero (nombre, apellido, fecha_nacimiento, nacionalidad, pasaporte, email, telefono) VALUES
('Juan', 'Pérez', '1985-03-10', 'Mexicana', 'A123456', 'juan@example.com', '5551234567'),
('Ana', 'Gómez', '1990-07-22', 'Mexicana', 'B234567', 'ana@example.com', '5552345678'),
('Carlos', 'López', '1988-01-15', 'Colombiana', 'C345678', 'carlos@example.com', '5553456789'),
('Luisa', 'Martínez', '1992-12-05', 'Peruana', 'D456789', 'luisa@example.com', '5554567890'),
('Pedro', 'Ramírez', '1979-09-30', 'Argentina', 'E567890', 'pedro@example.com', '5555678901');

CREATE TABLE aeropuerto (
	id_aeropuerto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    ciudad VARCHAR(100),
    pais VARCHAR(100)
);

INSERT INTO aeropuerto (nombre, ciudad, pais) VALUES
('AICM', 'Ciudad de México', 'México'),
('Aeropuerto de Cancún', 'Cancún', 'México'),
('Aeropuerto de Monterrey', 'Monterrey', 'México'),
('Aeropuerto de Guadalajara', 'Guadalajara', 'México'),
('Aeropuerto de Tijuana', 'Tijuana', 'México');

CREATE TABLE ruta (
	id_ruta INT PRIMARY KEY AUTO_INCREMENT,
    id_aeropuerto_origen INT,
    id_aeropuerto_destino INT,
    duracion INT,
    FOREIGN KEY (id_aeropuerto_origen) REFERENCES aeropuerto(id_aeropuerto),
    FOREIGN KEY (id_aeropuerto_destino) REFERENCES aeropuerto(id_aeropuerto)
);

INSERT INTO ruta (id_aeropuerto_origen, id_aeropuerto_destino, duracion) VALUES
(1, 2, 120),
(1, 3, 90),
(2, 4, 150),
(3, 5, 180),
(4, 1, 110);

CREATE TABLE avion (
	id_avion INT PRIMARY KEY AUTO_INCREMENT,
    modelo VARCHAR(100),
    fabricante VARCHAR(100),
    capacidad INT,
    año_fabricacion YEAR
);

INSERT INTO avion (modelo, fabricante, capacidad, año_fabricacion) VALUES
('737', 'Boeing', 180, 2015),
('A320', 'Airbus', 160, 2017),
('Embraer 190', 'Embraer', 100, 2012),
('737 MAX', 'Boeing', 190, 2020),
('A321', 'Airbus', 220, 2019);

CREATE TABLE vuelo (
	id_vuelo INT PRIMARY KEY AUTO_INCREMENT,
    id_ruta INT,
    id_avion INT,
    fecha_salida DATETIME,
    fecha_llegada DATETIME,
    estado VARCHAR(100),
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
    FOREIGN KEY (id_avion) REFERENCES avion(id_avion)
);

INSERT INTO vuelo (id_ruta, id_avion, fecha_salida, fecha_llegada, estado) VALUES
(1, 1, '2025-06-15 08:00:00', '2025-06-15 10:00:00', 'Programado'),
(2, 2, '2025-06-16 09:30:00', '2025-06-16 11:00:00', 'En vuelo'),
(3, 3, '2025-06-17 14:00:00', '2025-06-17 16:30:00', 'Retrasado'),
(4, 4, '2025-06-18 07:45:00', '2025-06-18 10:45:00', 'Cancelado'),
(5, 5, '2025-06-19 12:00:00', '2025-06-19 13:50:00', 'Programado');

CREATE TABLE empleado (
	id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    cargo VARCHAR(100),
    licencia BOOLEAN,
    fecha_ingreso DATETIME
);

INSERT INTO empleado (nombre, apellido, cargo, licencia, fecha_ingreso) VALUES
('Laura', 'Hernández', 'Piloto', TRUE, '2020-01-01'),
('José', 'Torres', 'Copiloto', TRUE, '2021-02-10'),
('Elena', 'Salinas', 'Azafata', FALSE, '2022-03-15'),
('Miguel', 'Díaz', 'Azafato', FALSE, '2021-07-20'),
('Rosa', 'Nieto', 'Técnico', TRUE, '2019-11-30');

CREATE TABLE tripulacion (
	id_vuelo INT,
    id_empleado INT,
    rol VARCHAR(100),
    PRIMARY KEY (id_vuelo, id_empleado),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

INSERT INTO tripulacion (id_vuelo, id_empleado, rol) VALUES
(1, 1, 'Piloto'),
(1, 2, 'Copiloto'),
(1, 3, 'Azafata'),
(2, 1, 'Piloto'),
(2, 4, 'Azafato');

CREATE TABLE reserva (
	id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_vuelo INT,
    fecha_reserva DATETIME,
    estado VARCHAR(100),
    FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo)
);

INSERT INTO reserva (id_vuelo, fecha_reserva, estado) VALUES
(1, '2025-06-01 10:00:00', 'Confirmada'),
(2, '2025-06-02 11:30:00', 'En espera'),
(3, '2025-06-03 12:45:00', 'Cancelada'),
(4, '2025-06-04 09:20:00', 'Confirmada'),
(5, '2025-06-05 08:50:00', 'Confirmada');

CREATE TABLE detalle_reserva (
	id_reserva INT,
    id_pasajero INT,
    PRIMARY KEY (id_reserva, id_pasajero),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero)
);

INSERT INTO detalle_reserva (id_reserva, id_pasajero) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

CREATE TABLE pago (
	id_pago INT PRIMARY KEY AUTO_INCREMENT,
    id_reserva INT,
    metodo_pago VARCHAR(100),
    monto FLOAT,
    fecha_pago DATETIME,
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

INSERT INTO pago (id_reserva, metodo_pago, monto, fecha_pago) VALUES
(1, 'Tarjeta', 1200.00, '2025-06-01 11:00:00'),
(2, 'Efectivo', 850.00, '2025-06-02 12:00:00'),
(3, 'Tarjeta', 900.00, '2025-06-03 13:00:00'),
(4, 'Transferencia', 1100.00, '2025-06-04 10:00:00'),
(5, 'Efectivo', 1000.00, '2025-06-05 09:00:00');

CREATE TABLE checkin (
	id_checkin INT PRIMARY KEY AUTO_INCREMENT,
    id_pasajero INT,
    id_reserva INT,
    fecha_checkin DATETIME,
    asiento VARCHAR(100),
    puerta_embarque INT,
    FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

INSERT INTO checkin (id_pasajero, id_reserva, fecha_checkin, asiento, puerta_embarque) VALUES
(1, 1, '2025-06-14 07:00:00', '12A', 3),
(2, 2, '2025-06-15 07:10:00', '14B', 2),
(3, 3, '2025-06-16 07:20:00', '15C', 1),
(4, 4, '2025-06-17 07:30:00', '16D', 4),
(5, 5, '2025-06-18 07:40:00', '17E', 5);

CREATE TABLE equipaje (
	id_equipaje INT PRIMARY KEY AUTO_INCREMENT,
    id_checkin INT,
    peso INT,
    descripcion VARCHAR(500),
    costo_extra INT,
    FOREIGN KEY (id_checkin) REFERENCES checkin(id_checkin)
);

INSERT INTO equipaje (id_checkin, peso, descripcion, costo_extra) VALUES
(1, 23, 'Maleta grande', 0),
(2, 18, 'Mochila', 0),
(3, 27, 'Maleta extra', 50),
(4, 25, 'Equipaje especial', 100),
(5, 15, 'Bolso de mano', 0);

CREATE TABLE boleto (
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

INSERT INTO boleto (id_reserva, id_pasajero, id_vuelo, id_checkin, id_pago, clase, precio_total, fecha_emision) VALUES
(1, 1, 1, 1, 1, 'Económica', 1200.00, '2025-06-01 11:10:00'),
(2, 2, 2, 2, 2, 'Ejecutiva', 850.00, '2025-06-02 12:10:00'),
(3, 3, 3, 3, 3, 'Económica', 900.00, '2025-06-03 13:10:00'),
(4, 4, 4, 4, 4, 'Económica', 1100.00, '2025-06-04 10:10:00'),
(5, 5, 5, 5, 5, 'Ejecutiva', 1000.00, '2025-06-05 09:10:00');