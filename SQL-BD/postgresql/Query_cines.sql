-- Insertar datos en las tablas
-- Tabla de cines
INSERT INTO cine (nombre, direccion, telefono) VALUES
('CineCity', 'Calle Principal 123', '123456789'),
('CineMundo', 'Avenida Central 456', '987654321'),
('CineMax', 'Paseo del Cine 789', '456123789');

-- Tabla de salas
INSERT INTO sala (id, cant_butacas, nombre_cine) VALUES
(1, 100, 'CineCity'),
(2, 120, 'CineCity'),
(3, 90, 'CineMundo'),
(4, 110, 'CineMax');

-- Tabla de funciones
INSERT INTO funcion (codigo, fecha, hora_comienzo, id_sala) VALUES
(1, '2024-05-10', '15:00:00', 1),
(2, '2024-05-11', '16:30:00', 1),
(3, '2024-05-10', '14:00:00', 2),
(4, '2024-05-10', '18:00:00', 3),
(5, '2024-05-11', '19:00:00', 4);

-- Tabla de películas
INSERT INTO pelicula (id, genero, idioma_original, url, duracion, calificacion, fecha_estreno_Argentina, resumen) VALUES
(1, 'Aventura', 'Inglés', 'https://www.ejemplo.com/pelicula1', '02:30:00', 4.5, '2024-05-10', 'Una película de aventuras'),
(2, 'Comedia', 'Español', 'https://www.ejemplo.com/pelicula2', '01:45:00', 3.8, '2024-05-11', 'Una comedia divertida'),
(3, 'Drama', 'Francés', 'https://www.ejemplo.com/pelicula3', '02:15:00', 4.2, '2024-05-10', 'Un drama emocionante');

-- Tabla de países
INSERT INTO pais (nombre) VALUES
('Estados Unidos'),
('España'),
('Francia');

-- Tabla de personas
INSERT INTO persona (nombre, nacionalidad) VALUES
('Steven Spielberg', 'Estadounidense'),
('Pedro Almodóvar', 'Español'),
('Jean-Luc Godard', 'Francés'),
('Tom Hanks', 'Estadounidense'),
('Penélope Cruz', 'Español'),
('Jean-Paul Belmondo', 'Francés');

-- Tabla de directores
INSERT INTO director (nombre) VALUES
('Steven Spielberg'),
('Pedro Almodóvar'),
('Jean-Luc Godard');

-- Tabla de actores
INSERT INTO actor (nombre) VALUES
('Tom Hanks'),
('Penélope Cruz'),
('Jean-Paul Belmondo');

-- Tabla de protagonistas
INSERT INTO protagonista (nombre) VALUES
('Tom Hanks'),
('Penélope Cruz');

-- Tabla de reparto
INSERT INTO reparto (nombre) VALUES
('Jean-Paul Belmondo');

-- Tabla de origen de producción
INSERT INTO origen_produccion (id, nombre) VALUES
(1, 'Estados Unidos'),
(2, 'España'),
(3, 'Francia');

-- Tabla de dirección
INSERT INTO dirige (id, nombre) VALUES
(1, 'Steven Spielberg'),
(2, 'Pedro Almodóvar'),
(3, 'Jean-Luc Godard');

-- Tabla de protagonismo
INSERT INTO es_protagonista (id, nombre) VALUES
(1, 'Tom Hanks'),
(2, 'Penélope Cruz'),
(3, 'Tom Hanks');

-- Tabla de reparto
INSERT INTO es_reparto (id, nombre) VALUES
(3, 'Jean-Paul Belmondo');

-- Tabla de proyecciones
INSERT INTO proyecta (id, codigo_funcion) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5);