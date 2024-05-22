package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;

public class Conexion {
    String bd = "complejo_cines";
    String url;
    String user;
    String password;
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public Conexion(String bd, Properties configuration) {
        this.bd = bd;
        this.url = configuration.getProperty("db.url= ");
        this.user = configuration.getProperty("db.user= ");
        this.password = configuration.getProperty("db.password= ");
    }
    
    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Se conecto a la base de datos " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se pudo conectar a la base de datos " + bd);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }


    public void desconectar() {
        if (cx != null) {
            try {
                cx.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * Este metodo prepara la consulta parametrizada que se solicita realizar.
     *
     * @param consulta
     * @return objeto PreparedStatement preparado para esa consulta.
     */
    public PreparedStatement prepareStatement(String consulta) {
        try {
            return cx.prepareStatement(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
