import java.conexion.Conexion;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
        Conexion conexion = new Conexion("complejo_cines");
        conexion.conectar();
        principalDriver();
        conexion.desconectar();
    }

    private static void principalDriver() {
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
                  insertarCine();
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
                  listarCinesCantButacas();
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

    private static void insertarCine() {
        
    }

    private static void insertarSalaEnCine() {
        
    }

    private static void listarCinesInfoPeliculas() {
        
    }

    private static void actoresUnaPelícula() {
        
    }

    private static void listarActoresYDirectores() {
        
    }

    private static void listarCinesCantButacas() {
        
    }

    private static void consultasPropias() {
        
    }
}
