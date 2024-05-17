-- Crear la base de datos
CREATE DATABASE complejo_cines;

-- Tabla de cines
CREATE TABLE IF NOT EXISTS cine (
    nombre VARCHAR(255) NOT NULL PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS sala (
    id SERIAL PRIMARY KEY,
    cant_butacas INT NOT NULL,
    nombre_cine VARCHAR(255) NOT NULL,
    FOREIGN KEY (nombre_cine) REFERENCES cine(nombre)
);

CREATE TABLE IF NOT EXISTS funcion (
    codigo SERIAL PRIMARY KEY,
    fecha DATE,
    hora_comienzo TIME,
    id_sala INT, 
    FOREIGN KEY (id_sala) REFERENCES sala(id)
);

-- PostgreSQL no tiene ENUM por defecto como así lo tiene MySQL, por lo que se utiliza un tipo de datos de texto con una restricción de verificación
CREATE TABLE IF NOT EXISTS pelicula (
    id SERIAL PRIMARY KEY,
    genero VARCHAR(50) NOT NULL CHECK (genero IN ('Acción', 'Aventura', 'Comedia', 'Drama', 'Ciencia ficción', 'Fantasía', 
                'Terror', 'Suspense', 'Romance', 'Animación', 'Crimen', 'Misterio', 'Documental',
                'Musical', 'Guerra', 'Biografía', 'Histórica', 'Western', 'Cine negro', 'Superhéroes',
                'Deportes', 'Familiar', 'Infantil', 'Experimental', 'Independiente', 'Road movie', 
                'Cine de arte', 'Cine del Oeste', 'Cine erótico', 'Cine político', 'Cine social', 
                'Cine de autor', 'Cine experimental', 'Cine de culto', 'Cine de animación para adultos')),
    idioma_original VARCHAR(255) NOT NULL,
    url TEXT,
    duracion INTERVAL, -- Duración almacenada como intervalo de tiempo
    calificacion DECIMAL(2,1), -- calificación del 0.0 a 5.0
    fecha_estreno_argentina DATE,
    resumen TEXT
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
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES pais(nombre)
);

CREATE TABLE IF NOT EXISTS dirige (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES director(nombre)
);

CREATE TABLE IF NOT EXISTS es_protagonista (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES protagonista(nombre)
);

CREATE TABLE IF NOT EXISTS es_reparto (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, nombre),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (nombre) REFERENCES reparto(nombre)
);

CREATE TABLE IF NOT EXISTS proyecta (
    id INT NOT NULL,
    codigo_funcion INT NOT NULL,
    PRIMARY KEY (id, codigo_funcion),
    FOREIGN KEY (id) REFERENCES pelicula(id),
    FOREIGN KEY (codigo_funcion) REFERENCES funcion(codigo)
);

-- Crear los triggers
CREATE OR REPLACE FUNCTION update_director_reg_historico() RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar la participación del director
    UPDATE director
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_dirigir
AFTER INSERT ON dirige
FOR EACH ROW
EXECUTE FUNCTION update_director_reg_historico();

CREATE OR REPLACE FUNCTION update_actor_reg_historico_protagonista() RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar la participación del actor
    UPDATE actor
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_protagonizar
AFTER INSERT ON es_protagonista
FOR EACH ROW
EXECUTE FUNCTION update_actor_reg_historico_protagonista();

CREATE OR REPLACE FUNCTION update_actor_reg_historico_reparto() RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar la participación del actor
    UPDATE actor
    SET reg_historico = reg_historico + 1
    WHERE nombre = NEW.nombre;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_extra
AFTER INSERT ON es_reparto
FOR EACH ROW
EXECUTE FUNCTION update_actor_reg_historico_reparto();
