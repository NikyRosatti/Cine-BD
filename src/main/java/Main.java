import java.sql.*;
import java.util.Scanner;
import java.math.BigDecimal;

import conexion.ConexionPostgreSQL;

public class Main {
    public static void main(String[] args) {
        ConexionPostgreSQL conexion = new ConexionPostgreSQL("complejo_cines");
        conexion.conectar();
        principalDriver(conexion);
        conexion.desconectar();
    }

    private static void principalDriver(ConexionPostgreSQL conexion) {
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

    private static void insertarCine(ConexionPostgreSQL conexion) {
        System.out.println("Insertar nombre del cine: ");
        Scanner scanner = new Scanner(System.in);
        String nombreCine = scanner.nextLine();
        System.out.println("Insertar direccion del cine: ");
        String direccionCine = scanner.nextLine();
        System.out.println("Insertar telefono del cine: ");
        String telefonoCine = scanner.nextLine();
        String consulta = "INSERT INTO cine (nombre, direccion, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, nombreCine);
            statement.setString(2, direccionCine);
            statement.setString(3, telefonoCine);
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

    private static void insertarSalaEnCine(ConexionPostgreSQL conexion) {
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

    private static void listarCinesInfoPeliculas(ConexionPostgreSQL conexion) {
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

    private static void actoresUnaPelícula(ConexionPostgreSQL conexion) {
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

    private static void listarActoresYDirectores(ConexionPostgreSQL conexion) {
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

    private static void listarCinesCantButacas(ConexionPostgreSQL conexion) {
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

    private static void consultasPropias(ConexionPostgreSQL conexion) {
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
                Date fechaEstreno = resultSet.getDate("fecha_estreno_argentina");
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
