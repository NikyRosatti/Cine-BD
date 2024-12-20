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
* **Base de Datos:** MySQL
* **Herramientas de Desarrollo:** MySQL Workbench

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
   git clone https://github.com/NikyRosatti/Cine-BD.git
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
   Podemos utilizar el archivo <b>scriptCines.sql</b> para hacer este paso
   y con el archivo <b>Query_cines.sql</b> introducimos datos genericos a las tablas.

   Una idea es copiar el contenido de los archivos y correr la query desde MySQL Workbench por ejemplo.

2. Configura la conexión en tu archivo <b>configuration.properties</b>:
   ```.properties
    # Configuracion de la base de datos
    db.url=jdbc:mysql://localhost/
    db.user=ElUsuarioDefinidoEnMysqlWorkbench
    db.password=LaContraseniaElegidaParaLaInstanciaDeMySqlWorkbench
   ```

### Compilación y Ejecución en Linux
Compila los archivos Java y ejecuta la aplicación:
   ```bash
   cd <directorio-del-repo>/JavaMySql
   # Compilar
   javac Main.java
   # Ejecutar
   java -cp .:lib/mysql-connector-j-8.4.0.jar Main
   ```

### Compilar y Ejecutar en Windows(cmd)
   ```bash
   cd <directorio-del-repo>/JavaMySql
   # Compilar
   javac Main.java 
   # de otra manera: 
   javac -cp .;lib/mysql-connector-j-8.4.0.jar Main.java conexion/Conexion.java

   #Ejecutar
   java -cp .;lib/mysql-connector-j-8.4.0.jar Main
   ```

## Contribuciones
Este proyecto es de código abierto y acepta contribuciones. Para contribuir, por favor sigue las guías de contribución y las mejores prácticas de desarrollo colaborativo.

## Contactos
## Integrantes del Proyecto
* **Rosatti, Nicolle Yazmín:** nicollerosatti@gmail.com
* **Tissera, Joaquín Pablo**: joaco.tissera@gmail.com
* **Rodeghiero, Tomás:** tomyrodeghiero@gmail.com
