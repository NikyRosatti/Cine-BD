package options.listaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;

public class ActoresUnaPelicula {
    public static void actoresUnaPelícula(Conexion conexion) {
        // Resolucion de consultas (No necesariamente Java)
        System.out.println("· Opción 4: Devolver actores que solo figuran en una sola película");
        System.out.println("Opcion no obligatoria para este archivo Java");

        String consulta = "SELECT nombre FROM actor WHERE nombre IN (" +
                "SELECT nombre FROM es_protagonista GROUP BY nombre HAVING COUNT(id) = 1" +
                " UNION " +
                "SELECT nombre FROM es_reparto GROUP BY nombre HAVING COUNT(id) = 1)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    String nombre = resultSet.getString("nombre");
                    System.out.println("Actor: " + nombre);
                } while (resultSet.next());
            } else {
                System.out.println("No hay actores cargados que actuen en una sola pelicula");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }
}
