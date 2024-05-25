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

    /**
     * Constructor de la clase Conexion
     * @param bd el nombre de la base de datos
     * @param configuration una clase Properties que ya este cargada con los datos de configuracion para esta clase Conexion
     */
    public Conexion(String bd, Properties configuration) {
        this.bd = bd;
        this.url = configuration.getProperty("db.url");
        this.user = configuration.getProperty("db.user");
        this.password = configuration.getProperty("db.password");
    }
    
    /**
     * Da formato a la clase Connection, segun url, bd, user y password
     * @return la clase Connection en caso que haya sido exitosa la conexion entre la base de datos y el programa
     * @throws ClassNotFoundException si no se pudo encontrar la clase driver (el .jar)
     * @throws SQLException si tuvo problemas SQL para conectarse 
     */
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

    /**
     * Cerrar la clase connection si es que fue abierta
     * @throws SQLEXception en caso que falle el close()
     */
    public void desconectar() {
        if (cx != null) {
            try {
                cx.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
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
