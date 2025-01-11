package JavaMySql.options.listaciones;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaMySql.controller.Cine;
import conexion.Conexion;

public class ListarCinesInfo {
    public static List<Cine> listarCinesInfo(Conexion conexion) {
        List<Cine> cines = new ArrayList<>();

        String consulta = "SELECT * FROM cine";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    //int id = resultSet.getInt("id");
                    String nombreCine = resultSet.getString("nombre");
                    String direccion = resultSet.getString("direccion");
                    String telefono = resultSet.getString("telefono");

                    Cine cine = new Cine(nombreCine, direccion, telefono);
                    cines.add(cine);
                } while (resultSet.next());
            } else {
                System.out.println("No hay cines cargados para listar");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }

        return cines;
    }
}
