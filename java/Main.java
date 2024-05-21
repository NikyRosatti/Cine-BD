import java.sql.*;
import java.util.Scanner;
import java.math.BigDecimal;

import conexion.Conexion;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream("configuration.properties"));
        // Conexion pasando el archivo .properties con la configuracion respectiva.
        Conexion conexion = new Conexion("complejo_cines", prop);
        conexion.conectar();
        principalDriver(conexion);
        conexion.desconectar();
    }

    private static void principalDriver(Conexion conexion) {
        int resEntry;
        do {
            mostrarMenu();
            resEntry = pedirEntrada();
            if (resEntry < 1 || resEntry > 8) {
                System.out.println("Debe ser una opción del 1 al 8");
            } else {
                opcionSeleccionada(resEntry);
                switch (resEntry) {
                case 1:
                  insertarCine(conexion);
                  break;
                case 2:
                  insertarSalaEnCine(conexion);
                  break;
                case 3:
                  listarCinesInfoPeliculas(conexion);
                  break;
                case 4:
                  actoresUnaPelícula(conexion);
                  break;
                case 5:
                  listarActoresYDirectores(conexion);
                  break;
                case 6:
                  listarCinesCantButacas(conexion);
                  break;
                case 7:
                  consultasPropias(conexion);
                  break;
                }
            }
        } while (resEntry != 8);
    }

    private static void mostrarMenu() {
      System.out.println("# Menú principal #");
      System.out.println("·     Opción 1: Insertar un cine");
      System.out.println("·     Opción 2: Insertar una sala en un cine");
      System.out.println("·     Opción 3: Listar todos los cines con la información de sus salas");
      System.out.println("·     Opción 4: Devolver actores que solo figuran en una sola película");
      System.out.println("·     Opción 5: Listar las personas que han sido actores y directores");
      System.out.println("·     Opción 6: Listar los cines con la cantidad total de butacas totales");
      System.out.println("·     Opción 7: Resultado de consultas propias");
      System.out.println("·     Opción 8: Salir");
    }

    private static int pedirEntrada() {
      System.out.print("Seleccione una opción: ");
      Scanner entryScanner = new Scanner(System.in);
      String entry = entryScanner.nextLine();
      return Integer.valueOf(entry);
    }

    private static void opcionSeleccionada(int entry) {
      switch (entry) {
        case 1:
          System.out.println("· Opción 1: Insertar un cine");
          break;
        case 2:
          System.out.println("· Opción 2: Insertar una sala en un cine");
          break;
        case 3:
          System.out.println("· Opción 3: Listar todos los cines con la información de sus salas");
          break;
        case 4:
          System.out.println("· Opción 4: Devolver actores que solo figuran en una sola película");
          break;
        case 5:
          System.out.println("· Opción 5: Listar las personas que han sido actores y directores");
          break;
        case 6:
          System.out.println("· Opción 6: Listar los cines con la cantidad total de butacas totales");
          break;
        case 7:
          System.out.println("· Opción 7: Resultado de consultas propias");
          break;
        default:
          System.out.println("· Opción 8: Salir");
          break;
      }
    }
    private static void insertarCine(Conexion conexion) {
        // Solicita el nombre del cine
        System.out.println("Insertar nombre del cine: ");
        Scanner scanner = new Scanner(System.in);
        String nombreCine = scanner.nextLine();
    
        // Solicita la direccion del cine
        System.out.println("Insertar direccion del cine: ");
        String direccionCine = scanner.nextLine();
    
        // Solicita un telefono del cine
        System.out.println("Insertar telefono del cine: ");
        String telefonoCine = scanner.nextLine();
    
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

    private static void insertarSalaEnCine(Conexion conexion) {
      System.out.println("Insertar id de la sala: ");
      Scanner scanner = new Scanner(System.in);
      int idSala = scanner.nextInt();
      scanner.nextLine();
      System.out.println("Insertar cantidad de butacas: ");
      int cantButacas = scanner.nextInt();
      scanner.nextLine();
      System.out.println("Insertar nombre del cine: ");
      String nombreCine = scanner.nextLine();

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

    private static void listarCinesInfoPeliculas(Conexion conexion) {
      String consulta = "SELECT cine.nombre, cine.direccion, cine.telefono, sala.id, sala.cant_butacas " +
                        "FROM cine " +
                        "LEFT JOIN sala ON cine.nombre = sala.nombre_cine";
      try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
          ResultSet resultSet = statement.executeQuery();
          while (resultSet.next()) {
              String nombreCine = resultSet.getString("nombre");
              String direccion = resultSet.getString("direccion");
              String telefono = resultSet.getString("telefono");
              int idSala = resultSet.getInt("id");
              int cantButacas = resultSet.getInt("cant_butacas");

              System.out.println("Cine: " + nombreCine + ", Dirección: " + direccion + ", Teléfono: " + telefono);
              System.out.println("  Sala ID: " + idSala + ", Cantidad de Butacas: " + cantButacas);
          }
      } catch (SQLException e) {
          System.out.println("Error al listar datos: " + e.getMessage());
      }
    }

    private static void actoresUnaPelícula(Conexion conexion) {
        String consulta = "SELECT nombre FROM actor WHERE nombre IN (" +
                          "SELECT nombre FROM es_protagonista GROUP BY nombre HAVING COUNT(id) = 1" +
                          " UNION " +
                          "SELECT nombre FROM es_reparto GROUP BY nombre HAVING COUNT(id) = 1)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                System.out.println("Actor: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void listarActoresYDirectores(Conexion conexion) {
        String consulta = "SELECT nombre FROM persona WHERE nombre IN (" +
                          "SELECT nombre FROM actor) AND nombre IN (" +
                          "SELECT nombre FROM director)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                System.out.println("Persona: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void listarCinesCantButacas(Conexion conexion) {
        String consulta = "SELECT cine.nombre, SUM(sala.cant_butacas) AS total_butacas " +
                          "FROM cine " +
                          "LEFT JOIN sala ON cine.nombre = sala.nombre_cine " +
                          "GROUP BY cine.nombre";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombreCine = resultSet.getString("nombre");
                int totalButacas = resultSet.getInt("total_butacas");
                System.out.println("Cine: " + nombreCine + ", Total de Butacas: " + totalButacas);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void consultasPropias(Conexion conexion) {
        // Consultas adicionales
        String consulta = "SELECT * FROM pelicula WHERE calificacion > 4.0";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String genero = resultSet.getString("genero");
                String idiomaOriginal = resultSet.getString("idioma_original");
                String url = resultSet.getString("url");
                String duracion = resultSet.getString("duracion");
                BigDecimal calificacion = resultSet.getBigDecimal("calificacion");
                Date fechaEstreno = resultSet.getDate("fecha_estreno_Argentina");
                String resumen = resultSet.getString("resumen");

                System.out.println("ID: " + id + ", Género: " + genero + ", Idioma Original: " + idiomaOriginal);
                System.out.println("URL: " + url + ", Duración: " + duracion + ", Calificación: " + calificacion);
                System.out.println("Fecha de Estreno en Argentina: " + fechaEstreno + ", Resumen: " + resumen);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }
}
