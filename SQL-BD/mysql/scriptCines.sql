-- Comentar o descomentar segun fuese necesario para hacer pruebas
-- DROP DATABASE complejo_cines;

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS complejo_cines;

-- Usar la base de datos
USE complejo_cines;

CREATE TABLE IF NOT EXISTS pelicula (
    id INT NOT NULL PRIMARY KEY,
    genero ENUM('Acción', 'Aventura', 'Comedia', 'Drama', 'Ciencia ficción', 'Fantasía', 
                'Terror', 'Suspense', 'Romance', 'Animación', 'Crimen', 'Misterio', 'Documental',
                'Musical', 'Guerra', 'Biografía', 'Histórica', 'Western', 'Cine negro', 'Superhéroes',
                'Deportes', 'Familiar', 'Infantil', 'Experimental', 'Independiente', 'Road movie', 
                'Cine de arte', 'Cine del Oeste', 'Cine erótico', 'Cine político', 'Cine social', 
                'Cine de autor', 'Cine experimental', 'Cine de culto', 'Cine de animación para adultos'),
    idioma_original VARCHAR(255) NOT NULL,
    url TEXT,
    duracion TIME,
    calificacion DECIMAL(2,1), -- calificacion del 0.0 a 5.0
    fecha_estreno_Argentina DATE,
    resumen TEXT,
    titulo_distribucion TEXT,
    titulo_original TEXT,
    titulo_espaniol TEXT,
    CONSTRAINT id_positiva_peliculas CHECK (id > 0),
    CONSTRAINT titulo_original_uppercase CHECK (titulo_original = UPPER(titulo_original))
);

CREATE TABLE IF NOT EXISTS cine (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    telefono INT NOT NULL
);

CREATE TABLE IF NOT EXISTS sala (
    id INT NOT NULL PRIMARY KEY,
    cant_Butacas INT NOT NULL,
    nombre_cine VARCHAR(255) NOT NULL,
    FOREIGN KEY (nombre_cine) REFERENCES cine(nombre),
    CONSTRAINT id_positiva_salas CHECK (id > 0)
);

CREATE TABLE IF NOT EXISTS funcion (
    codigo INT NOT NULL PRIMARY KEY,
    fecha DATE,
    hora_comienzo TIME,
    id_sala INT, 
    FOREIGN KEY (id_sala) REFERENCES sala(id),
    CONSTRAINT codigo_positivo CHECK (codigo > 0)
);

CREATE TABLE IF NOT EXISTS pais (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS persona (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    nacionalidad VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS director (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    reg_historico INT DEFAULT 0,
    FOREIGN KEY (nombre) REFERENCES persona(nombre)
);

CREATE TABLE IF NOT EXISTS actor (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    reg_historico INT DEFAULT 0,
    FOREIGN KEY (nombre) REFERENCES persona(nombre)
);

CREATE TABLE IF NOT EXISTS protagonista (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    FOREIGN KEY (nombre) REFERENCES actor(nombre)
);

CREATE TABLE IF NOT EXISTS reparto (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    FOREIGN KEY (nombre) REFERENCES actor(nombre)
);

CREATE TABLE IF NOT EXISTS origen_produccion (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    anio INT CHECK (anio > 1894 AND anio < 2025),
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES pais(nombre),
    CONSTRAINT id_positiva_produccion CHECK (id > 0)
);

CREATE TABLE IF NOT EXISTS dirige (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES director(nombre),
    CONSTRAINT id_positiva_dirige CHECK (id > 0)
);

CREATE TABLE IF NOT EXISTS proyecta (
    id INT NOT NULL,
    codigo_funcion INT NOT NULL,
    PRIMARY KEY (id, codigo_funcion),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (codigo_funcion) REFERENCES funcion(codigo),
    CONSTRAINT id_positiva_proyecta CHECK (id > 0)
);

CREATE TABLE IF NOT EXISTS es_reparto (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES reparto(nombre),
    CONSTRAINT id_positiva_reparto CHECK (id > 0)
);

CREATE TABLE IF NOT EXISTS es_protagonista (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES protagonista(nombre),
    CONSTRAINT id_positiva_protagonista CHECK (id > 0)
);

-- Crear el trigger
DELIMITER //
CREATE TRIGGER after_dirigir
AFTER INSERT ON dirige
FOR EACH ROW
BEGIN
    -- Actualizar la participacion del director
    UPDATE director
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
END;
//
DELIMITER ;

-- Crear el trigger
DELIMITER //
CREATE TRIGGER after_protaginizar
AFTER INSERT ON es_protagonista
FOR EACH ROW
BEGIN
    -- Actualizar la participacion del actor
    UPDATE actor
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
END;
//
DELIMITER ;


-- Crear el trigger
DELIMITER //
CREATE TRIGGER after_extra
AFTER INSERT ON es_reparto
FOR EACH ROW
BEGIN
    -- Actualizar la participacion del actor
    UPDATE actor
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
END;
//
DELIMITER ;

-- Una de las consideraciones es usar dominio para las calificaciones de las peliculas
-- MySQL no soporta creaciones de dominios, pero en postgre se haria de una manera tal que
-- CREATE DOMAIN dominioCalificaciones AS DECIMAL(2,1) DEFAULT 0 NOT NULL;

CREATE TABLE IF NOT EXISTS auditorias (
	id INT PRIMARY KEY AUTO_INCREMENT,
    author VARCHAR(100),
    table_name_altered VARCHAR(100),
    when_modified TIMESTAMP,
    oldest VARCHAR(100),
    newest VARCHAR(100)
);

-- Trigger para insercion en peliculas
DELIMITER //
CREATE TRIGGER auditoria_insert_peliculas
AFTER INSERT ON pelicula
FOR EACH ROW
BEGIN
    INSERT INTO auditorias 
    (author, table_name_altered, when_modified, oldest, newest)
    VALUES
    (CURRENT_USER(), 'fecha_estreno_Argentina', NOW(), null, NEW.fecha_estreno_Argentina);
END;
//
DELIMITER ;

-- Trigger para remover en peliculas
DELIMITER //
CREATE TRIGGER auditoria_remove_peliculas
AFTER DELETE ON pelicula
FOR EACH ROW
BEGIN
    INSERT INTO auditorias 
    (author, table_name_altered, when_modified, oldest, newest)
    VALUES
    (CURRENT_USER(), 'fecha_estreno_Argentina', NOW(), OLD.fecha_estreno_Argentina, null);
END;
//
DELIMITER ;

-- Trigger para insercion en peliculas
DELIMITER //
CREATE TRIGGER auditoria_update_peliculas
AFTER UPDATE ON pelicula
FOR EACH ROW
BEGIN
    INSERT INTO auditorias 
    (author, table_name_altered, when_modified, oldest, newest)
    VALUES
    (CURRENT_USER(), 'fecha_estreno_Argentina', NOW(), OLD.fecha_estreno_Argentina, NEW.fecha_estreno_Argentina);
END;
//
DELIMITER ;
