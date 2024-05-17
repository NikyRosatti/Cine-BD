package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionPostgreSQL {
    String bd = "complejo_cines";
    String url = "jdbc:postgresql://localhost:5432/";
    String user = "tad";
    String password = "tad";
    String driver = "org.postgresql.Driver";
    Connection cx;

    public ConexionPostgreSQL(String bd) {
        this.bd = bd;
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Connected to the database " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Unable to connect to the database " + bd);
            Logger.getLogger(ConexionPostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }

    public void desconectar() {
        if (cx != null) {
            try {
                cx.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionPostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public PreparedStatement prepareStatement(String consulta) {
        try {
            return cx.prepareStatement(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
