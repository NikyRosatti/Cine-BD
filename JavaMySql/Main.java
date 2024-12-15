import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.io.IOException;
import java.util.Scanner;
import java.util.Properties;

import conexion.Conexion;
import options.OptionHandler;

public class Main {
    private static Scanner entryScanner = new Scanner(System.in);
    private static Properties prop;
    private static Conexion conexion;
    private static OptionHandler option;

    public static void main(String[] args) throws IOException {
        prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream("configuration.properties"));
        // Conexion pasando el archivo .properties con la configuracion respectiva.
        conexion = new Conexion("complejo_cines", prop);
        if (conexion.conectar() != null) {
            option = new OptionHandler(conexion, entryScanner);
            int resEntry;
            do {
                mostrarMenu();
                resEntry = pedirEntrada();
                if (resEntry < 1 || resEntry > 8) {
                    System.out.println("Debe ser una opción del 1 al 8");
                } else {
                    option.handleOption(resEntry);
                }
            } while (resEntry != 8);
            entryScanner.close();
            conexion.desconectar();
        } else {
            System.out.println("Las causas de no haberse conectado pueden ser: ");
            System.out.println("    1) Falta o es incorrecta la contraseña en el archivo configuration.properties");
            System.out.println("    2) Falta o es incorrecto el usuario en el archivo configuration.properties");
            System.out.println("    3) No se creó o inicializó la base de datos en MySQL");
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("# Menú principal #");
        System.out.println("Las primeras tres opciones son pedidas para este archivo Java");
        System.out.println("Mientras que las demas no necesariamente son para Java, sino para el proyecto en general");
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
        String entry = entryScanner.nextLine();
        return Integer.valueOf(entry);
    }
}
