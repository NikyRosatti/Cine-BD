package JavaMySql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Properties;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;

import conexion.Conexion;
import JavaMySql.options.OptionHandler;
import JavaMySql.options.inserciones.InsertarCine;
import JavaMySql.options.listaciones.ListarCinesInfoSalas;

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

        // Configurar Spark
        port(4567);

        // Configura el motor de plantillas FreeMarker
        Configuration freeMarkerConfig = new freemarker.template.Configuration(Configuration.VERSION_2_3_31);
        freeMarkerConfig.setClassForTemplateLoading(Main.class, "/templates");
        freeMarkerConfig.setDefaultEncoding("UTF-8");

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(freeMarkerConfig);
        staticFiles.externalLocation("src/main/resources/public"); // Ruta donde están tus archivos estáticos

        if (conexion.conectar() != null) {
            option = new OptionHandler(conexion);
            int resEntry;

            // Ruta principal
            get("/", (req, res) -> {
                return freeMarkerEngine.render(new ModelAndView(null, "home.html"));
            });

            // Formulario para insertar cine
            get("/insertar-cine", (req, res) -> {
                return freeMarkerEngine.render(new ModelAndView(null, "insertarCine.html"));
            });

            // Procesar inserción de cine
            post("/insertar-cine", (req, res) -> {
                String nombreCine = req.queryParams("nombre");
                String dirCine = req.queryParams("direccion");
                String telCine = req.queryParams("telefono");
                option.insertarCine(nombreCine, dirCine, telCine); // Lógica existente adaptada
                res.redirect("/listar-cines");
                return null;
            });
            
            // Formulario para insertar sala
            get("/insertar-sala", (req, res) -> {
                return freeMarkerEngine.render(new ModelAndView(null, "insertarSala.html"));
            });

            post("/insertar-sala", (req, res) -> {
                int idSala = Integer.parseInt(req.queryParams("id"));
                int cantB = Integer.parseInt(req.queryParams("cantButacas"));
                String nombreCine = req.queryParams("nombreCine");
                option.insertarSala(idSala,cantB, nombreCine); // Lógica existente adaptada
                res.redirect("/listar-todo");
                return null;
            });


            // Listar cines con sus salas
            get("/listar-todo", (req, res) -> {
              return option.listarCinesInfoSalas();
            });


            // Listar cines con sus salas
            get("/listar-cines", (req, res) -> {
              return option.listarCinesInfo() + "\n";
            });


            // Desconectar al cerrar
            Runtime.getRuntime().addShutdownHook(new Thread(() -> conexion.desconectar()));

            /*do {
                mostrarMenu();
                resEntry = pedirEntrada();
                if (resEntry < 1 || resEntry > 8) {
                    System.out.println("Debe ser una opción del 1 al 8");
                } else {
                    option.handleOption(resEntry);
                }
            } while (resEntry != 8);
            entryScanner.close();
            conexion.desconectar();*/
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
