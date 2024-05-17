import java.util.Scanner;
import java.sql.*;

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
                    insertarSalaEnCine();
                    break;
                case 3:
                    listarCinesInfoPeliculas();
                    break;
                case 4:
                    actoresUnaPelícula();
                    break;
                case 5:
                    listarActoresYDirectores();
                    break;
                case 6:
                    listarCinesCantButacas(conexion);
                    break;
                case 7:
                    consultasPropias();
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

    private static void insertarSalaEnCine() {
        // Implementar
    }

    private static void listarCinesInfoPeliculas() {
        // Implementar
    }

    private static void actoresUnaPelícula() {
        // Implementar
    }

    private static void listarActoresYDirectores() {
        // Implementar
    }

    private static void listarCinesCantButacas(ConexionPostgreSQL conexion) {
        String consulta = "SELECT * FROM cine";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                System.out.println("Nombre: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void consultasPropias() {
        // Implementar
    }
}
