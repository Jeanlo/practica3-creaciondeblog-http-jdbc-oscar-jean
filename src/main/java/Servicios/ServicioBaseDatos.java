/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServicioBaseDatos {
    private static ServicioBaseDatos baseDatos;
    private String URL = "jdbc:h2:~/manga-anime-empire";

    // Consigue una instancia de la base de datos en el caso de que no exista.

    public static ServicioBaseDatos getInstancia() {
        if (baseDatos == null)
            baseDatos = new ServicioBaseDatos();

        return baseDatos;
    }

    // Consigue una conexión de la base de datos para ejeuctar statements y demás.

    public Connection getConexion() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conexion;
    }

    // Prueba la conexión con la base de datos para probar que la aplicación pueda correr correctamente

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
