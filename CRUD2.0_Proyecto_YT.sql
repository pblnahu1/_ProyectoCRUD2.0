-- CRUD 2.0 (JAVA)
-- Proyecto CRUD de Youtube
CREATE DATABASE PRODUCTOS_CRUD;
USE PRODUCTOS_CRUD;

CREATE TABLE PRODUCTOS(
	producto_id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	precio FLOAT NOT NULL,
	tipo VARCHAR(30) NOT NULL,
	clave VARCHAR(5) NOT NULL,
	cantidad INT NOT NULL,
	PRIMARY KEY(producto_id)
);

SELECT * FROM productos;

INSERT INTO productos(nombre, precio, tipo, clave, cantidad)
VALUES ("Vino Tinto", '15.99', "Bebida", "VT", 15);

TRUNCATE TABLE productos;

DROP TABLE productos;

DROP DATABASE PRODUCTOS_CRUD;