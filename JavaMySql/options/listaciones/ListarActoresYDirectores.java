package options.listaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;

public class ListarActoresYDirectores {
    public static void listarActoresYDirectores(Conexion conexion) {
        // Resolucion de consultas (No necesariamente Java)
        System.out.println("· Opción 5: Listar las personas que han sido actores y directores");
        System.out.println("Opcion no obligatoria para este archivo Java");

        String consulta = "SELECT nombre FROM persona WHERE nombre IN (" +
                "SELECT nombre FROM actor) AND nombre IN (" +
                "SELECT nombre FROM director)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    String nombre = resultSet.getString("nombre");
                    System.out.println("Persona: " + nombre);
                } while (resultSet.next());
            } else {
                System.out.println("No hay personas cargadas que hayan sido actores y directores en peliculas");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }
}
