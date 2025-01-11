package JavaMySql.options;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import conexion.Conexion;
import JavaMySql.controller.Cine;
import JavaMySql.options.inserciones.*;
import JavaMySql.options.listaciones.*;
import JavaMySql.options.propias.*;

public class OptionHandler {
    private static Conexion conexion;
    //private static Scanner entryScanner = new Scanner(System.in);

    public OptionHandler(Conexion con) {
        conexion = con;
    }

    public void insertarCine(String nombre, String direccion, String telefono) {
        InsertarCine.insertarCine(conexion, nombre, direccion, telefono);
    }

    public List<Cine> listarCinesInfo() {
        return ListarCinesInfo.listarCinesInfo(conexion);
    }

    public void insertarSala(int idSala, int cantB, String nombreCine) {
        InsertarSalaEnCine.insertarSalaEnCine(conexion, idSala, cantB, nombreCine);
    }

    public List<String> listarCinesInfoSalas() {
        return ListarCinesInfoSalas.listarCinesInfoSalas(conexion);
    }
}
