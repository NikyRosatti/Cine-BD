package options.inserciones;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexion.Conexion;

public class InsertarCine {
    public static void insertarCine(Conexion conexion, Scanner entryScanner) {
        // Pedida para el archivo Java
        System.out.println("· Opción 1: Insertar un cine");
        System.out.println("Opcion pedida para este archivo Java");

        // Solicita el nombre del cine
        System.out.println("Insertar nombre del cine: ");
        String nombreCine = entryScanner.nextLine();

        // Solicita la direccion del cine
        System.out.println("Insertar direccion del cine: ");
        String direccionCine = entryScanner.nextLine();

        // Solicita un telefono del cine
        System.out.println("Insertar telefono del cine: ");
        String telefonoCine = entryScanner.nextLine();

        // Preparamos la consulta SQL
        String consulta = "INSERT INTO cine (nombre, direccion, telefono) VALUES (?, ?, ?)";

        // Preparamos la consulta de inserción de datos
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            // Establecer los valores de los parámetros
            statement.setString(1, nombreCine);
            statement.setString(2, direccionCine);
            statement.setString(3, telefonoCine);

            // Ejecuta la consulta de inserción
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Se ha insertado el cine correctamente.");
            } else {
                System.out.println("No se ha podido insertar ningún cine");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }
}
