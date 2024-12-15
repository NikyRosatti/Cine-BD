package options.listaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;

public class ListarCinesCantButacas {
    public static void listarCinesCantButacas(Conexion conexion) {
        // Resolucion de consultas (No necesariamente Java)
        System.out.println("· Opción 6: Listar los cines con la cantidad total de butacas totales");
        System.out.println("Opcion no obligatoria para este archivo Java");

        String consulta = "SELECT cine.nombre, SUM(sala.cant_butacas) AS total_butacas " +
                "FROM cine " +
                "LEFT JOIN sala ON cine.nombre = sala.nombre_cine " +
                "GROUP BY cine.nombre";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    String nombreCine = resultSet.getString("nombre");
                    int totalButacas = resultSet.getInt("total_butacas");
                    System.out.println("Cine: " + nombreCine + ", Total de Butacas: " + totalButacas);
                } while (resultSet.next());
            } else {
                System.out.println("No hay cines cargados");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }
}
