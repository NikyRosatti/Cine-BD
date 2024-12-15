package options;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexion.Conexion;
import options.inserciones.*;
import options.listaciones.*;
import options.propias.*;

public class OptionHandler {
    private static Conexion conexion;
    private static Scanner entryScanner = new Scanner(System.in);

    public OptionHandler(Conexion con, Scanner enSc) {
        conexion = con;
        entryScanner = enSc;
    }

    public void handleOption(Integer entry) {
        switch (entry) {
            case 1:
                InsertarCine.insertarCine(conexion, entryScanner);
                break;
            case 2:
                InsertarSalaEnCine.insertarSalaEnCine(conexion, entryScanner);
                break;
            case 3:
                ListarCinesInfoSalas.listarCinesInfoSalas(conexion);
                break;
            case 4:
                ActoresUnaPelicula.actoresUnaPelícula(conexion);
                break;
            case 5:
                ListarActoresYDirectores.listarActoresYDirectores(conexion);
                break;
            case 6:
                ListarCinesCantButacas.listarCinesCantButacas(conexion);
                break;
            case 7:
                ConsultasPropias.consultasPropias(conexion);
                break;
            default:
                System.out.println("· Opción 8: Salir");
                break;
        }
    }
}
