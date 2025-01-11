package JavaMySql.options.listaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;

public class ListarCinesInfoSalas {
    public static List<String> listarCinesInfoSalas(Conexion conexion) {
        List<String> cinesySalas = new ArrayList<>();
        // Pedida para el archivo Java
        System.out.println("· Opción 3: Listar todos los cines con la información de sus salas");
        System.out.println("Opcion pedida para este archivo Java");

        String consulta = "SELECT cine.nombre, cine.direccion, cine.telefono, sala.id, sala.cant_butacas " +
                "FROM cine " +
                "LEFT JOIN sala ON cine.nombre = sala.nombre_cine";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    String nombreCine = resultSet.getString("nombre");
                    String direccion = resultSet.getString("direccion");
                    String telefono = resultSet.getString("telefono");
                    int idSala = resultSet.getInt("id");
                    int cantButacas = resultSet.getInt("cant_butacas");

                    cinesySalas.add("Cine: " + nombreCine + ", Dirección: " + direccion + ", Teléfono: " + telefono 
                                    + "  Sala ID: " + idSala + ", Cantidad de Butacas: " + cantButacas + "\n");
                } while (resultSet.next());
            } else {
                System.out.println("No hay cines cargados para listar");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }

        return cinesySalas;
    }
}
