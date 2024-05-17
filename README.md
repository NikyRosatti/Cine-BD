# 📁 Sistema de Gestión de Cine - Trabajo Práctico Integrador de Base de Datos - UNRC

## Descripción
Este proyecto es un sistema de gestión de cines desarrollado en Java como parte de un trabajo práctico integrador de la asignatura de Base de Datos en la UNRC. El sistema está diseñado para gestionar las operaciones básicas de un cine, la administración de películas y salas, y otras gestiones relacionadas.

## Funcionalidades Principales
* **Gestión de Cines:** Permite agregar, modificar, listar y eliminar información sobre cines, incluyendo nombre, dirección y teléfono.
* **Gestión de Salas:** Permite administrar las salas de cine, asignando películas a proyectarse en cada una y configurando la capacidad de la sala.
* **Gestión de Funciones:** Permite gestionar las funciones de cine, incluyendo programación de horarios y asignación de salas.
* **Gestión de Películas:** Permite administrar información sobre las películas, incluyendo género, idioma, duración, calificación y resumen.
* **Gestión de Personal:** Permite administrar información sobre actores, directores y su participación en películas.

## Tecnologías Utilizadas
* **Lenguaje de Programación:** Java
* **Base de Datos:** MySQL y PostgreSQL
* **Herramientas de Desarrollo:** MySQL Workbench y pgAdmin

## Profesores
El trabajo fue realizado bajo la supervisión de los profesores:
* **Angeli, Sandra Edith**
* **Fraschetti, Guillermo**
* **Frutos, Mariana**
* **Zorzan, Fabio Andrés**

## Integrantes del Proyecto
* **Rosatti, Nicolle Yazmín**
* **Tissera, Joaquín Pablo**
* **Rodeghiero, Tomás**



## Año de Creación
El proyecto fue creado en el año 2024.

## Instrucciones de Uso

### Instalación
1. Clonar el repositorio del proyecto:
   ```bash
   git clone <url del repositorio>
   ```

2. Navegar al directorio del proyecto y actualizar el repositorio:
   ```bash
   cd <directorio-del-proyecto>
   git pull
   ```

### Configuración de la Base de Datos

#### MySQL
1. Crea la base de datos y las tablas necesarias:
   ```sql
   -- Crear la base de datos
   CREATE DATABASE IF NOT EXISTS complejo_cines;

   -- Usar la base de datos
   USE complejo_cines;

   -- Crear las tablas
   -- (Agrega aquí las definiciones de las tablas)
   ```

2. Configura la conexión en tu archivo Java:
   ```java
   // Ejemplo de configuración de conexión para MySQL
   String url = "jdbc:mysql://localhost:3306/complejo_cines";
   String user = "root";
   String password = "password";
   ```

#### PostgreSQL
1. Crea la base de datos y las tablas necesarias:
   ```sql
   -- Crear la base de datos
   CREATE DATABASE complejo_cines;

   -- Conectarse a la base de datos
   \c complejo_cines

   -- Crear las tablas
   -- (Agrega aquí las definiciones de las tablas)
   ```

2. Configura la conexión en tu archivo Java:
   ```java
   // Ejemplo de configuración de conexión para PostgreSQL
   String url = "jdbc:postgresql://localhost:5432/complejo_cines";
   String user = "postgres";
   String password = "password";
   ```

### Compilación
1. Compila los archivos Java:
   ```bash
   javac -cp .:<path-a-mysql-connector> Main.java
   javac -cp .:<path-a-postgresql-connector> Main.java
   ```

### Ejecución
1. Ejecuta la aplicación:
   ```bash
   java -cp .:<path-a-mysql-connector> Main
   java -cp .:<path-a-postgresql-connector> Main
   ```

### Ejemplo de Comandos de Git
1. Agregar archivos modificados:
   ```bash
   git add <archivo-modificado>
   ```

2. Realizar commit con un mensaje representativo:
   ```bash
   git commit -m "mensaje representativo"
   ```

3. Subir cambios al repositorio remoto:
   ```bash
   git push
   ```

## Contribuciones
Este proyecto es de código abierto y acepta contribuciones. Para contribuir, por favor sigue las guías de contribución y las mejores prácticas de desarrollo colaborativo.

## Contactos
## Integrantes del Proyecto
* **Rosatti, Nicolle Yazmín:** nicollerosatti@gmail.com
* **Tissera, Joaquín Pablo**: joaco.tissera@gmail.com
* **Rodeghiero, Tomás:** tomyrodeghiero@gmail.com
