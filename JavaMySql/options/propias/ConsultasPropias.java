package options.propias;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;

public class ConsultasPropias {
    private static Conexion conexion;
    private static String consulta;

    public static void consultasPropias(Conexion con) {
        // Consultas adicionales
        conexion = con;

        // Resolucion de consultas (No necesariamente Java)
        System.out.println("· Opción 7: Resultado de consultas propias");
        System.out.println("Opcion no obligatoria para este archivo Java");

        // Inicio consulta1: Listar las peliculas que tienen calificacion ATP
        consulta1();

        // Inicio consulta2: Paises con la mayor produccion en peliculas
        consulta2();

        // Inicio consulta3: Películas con la mayor duración en cada género
        consulta3();
    }

    private static void consulta1() {
        System.out.println("    Consulta1: Listar las peliculas que tienen calificacion Apta para todo Publico");
        consulta = "SELECT * FROM pelicula WHERE calificacion = 'ATP'";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    int id = resultSet.getInt("id");
                    String genero = resultSet.getString("genero");
                    String idiomaOriginal = resultSet.getString("idioma_original");
                    String url = resultSet.getString("url");
                    String duracion = resultSet.getString("duracion");
                    String calificacion = resultSet.getString("calificacion");
                    Date fechaEstreno = resultSet.getDate("fecha_estreno_Argentina");
                    String resumen = resultSet.getString("resumen");
                    String tituloDistribucion = resultSet.getString("titulo_distribucion");
                    String tituloOriginal = resultSet.getString("titulo_original");
                    String tituloEspaniol = resultSet.getString("titulo_espaniol");

                    System.out.println("ID: " + id + ", Género: " + genero + ", Idioma Original: " + idiomaOriginal);
                    System.out.println("URL: " + url + ", Duración: " + duracion + ", Calificación: " + calificacion);
                    System.out.println("Fecha de Estreno en Argentina: " + fechaEstreno + ", Resumen: " + resumen);
                    System.out.println(
                            "Título de Distribución: " + tituloDistribucion + ", Título Original: " + tituloOriginal);
                    System.out.println("Título en Español: " + tituloEspaniol);
                } while (resultSet.next());
            } else {
                System.out.println("No hay peliculas aptas para todo publico cargadas");
            }
        } catch (SQLException e) {
            System.out.println("consultasPropias: consulta1");
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void consulta2() {
        System.out.println("    Consulta2: Paises con la mayor produccion en peliculas");
        consulta = "SELECT nombre FROM pais WHERE (" +
                "SELECT COUNT(*) FROM origen_produccion op WHERE op.nombre = pais.nombre GROUP BY op.id" +
                ") = (" +
                "    SELECT MAX(producciones) AS cantMaxPeliculas FROM (" +
                "SELECT COUNT(*) AS producciones FROM origen_produccion GROUP BY nombre" +
                ") AS subconsulta" +
                ")";
        /*
         * Selecciono nombres de paises tales que:
         * La cantidad de peliculas con origen nombre.pais
         * (cantidad = count(*) , con origen WHERE op.nombre = pais.nombre)
         * Es la maxima cantidad de peliculas producidas por ese pais
         * (max(producciones) FROM (cantidad de producciones desde origen_produccion))
         * Es decir, si la maxima cantidad de peliculas encontradas es 5
         * (por un pais cualquiera), se van a listar todos aquellos paises
         * tales que produjeron 5 peliculas.
         */
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            int nroPais = 0;
            if (resultSet.next()) {
                do {
                    nroPais++;
                    String nombrePais = resultSet.getString("nombre");
                    System.out.println(nroPais + "- Nombre: " + nombrePais);
                } while (resultSet.next());
            } else {
                System.out.println("No hay paises o peliculas cargados");
            }
        } catch (SQLException e) {
            System.out.println("consultasPropias: consulta2");
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }

    private static void consulta3() {
        System.out.println("    Consulta3: Películas con la mayor duración en cada género");
        consulta = "SELECT id, genero, titulo_original, duracion FROM pelicula p1 " +
                "WHERE duracion = (SELECT MAX(duracion) FROM pelicula p2 WHERE p1.genero = p2.genero)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String genero = resultSet.getString("genero");
                    String tituloOriginal = resultSet.getString("titulo_original");
                    String duracion = resultSet.getString("duracion");

                    System.out.println("ID: " + id + ", Género: " + genero + ", Titulo Original: " + tituloOriginal
                            + ", Duración: " + duracion);
                }
            } else {
                System.out.println("No hay peliculas cargadas");
            }
        } catch (SQLException e) {
            System.out.println("consultasPropias: consulta3");
            System.out.println("Error al listar datos: " + e.getMessage());
        }
    }
}
