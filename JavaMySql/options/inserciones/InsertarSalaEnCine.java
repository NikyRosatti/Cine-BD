package options.inserciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexion.Conexion;

public class InsertarSalaEnCine {
    public static void insertarSalaEnCine(Conexion conexion, Scanner entryScanner) {
        // Pedida para el archivo Java
        System.out.println("· Opción 2: Insertar una sala en un cine");
        System.out.println("Opcion pedida para este archivo Java");

        String checkCine = "SELECT nombre FROM cine";
        try (PreparedStatement queryCines = conexion.prepareStatement(checkCine)) {
            ResultSet resultSet = queryCines.executeQuery();
            if (!resultSet.next()) {
                System.out.println("No hay cines cargados como para insertar salas");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cines: " + e.getMessage());
            return;
        }

        System.out.println("Insertar id de la sala: ");
        int idSala = entryScanner.nextInt();
        entryScanner.nextLine();
        System.out.println("Insertar cantidad de butacas: ");
        int cantButacas = entryScanner.nextInt();
        entryScanner.nextLine();
        System.out.println("Insertar nombre del cine: ");
        String nombreCine = entryScanner.nextLine();

        String consulta = "INSERT INTO sala (id, cant_butacas, nombre_cine) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, idSala);
            statement.setInt(2, cantButacas);
            statement.setString(3, nombreCine);
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Se ha insertado la sala correctamente.");
            } else {
                System.out.println("No se ha podido insertar ninguna sala");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }
}
