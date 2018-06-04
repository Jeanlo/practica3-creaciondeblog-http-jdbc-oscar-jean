import Servicios.ServicioBaseDatos;
import Servicios.ServicioBootstrap;

import java.sql.SQLException;

/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

public class Main {
    public static void main(String[] args) {
        try {
            //Iniciando el servicio
            ServicioBootstrap.startDb();

            //Prueba de Conexión.
            ServicioBaseDatos.getInstancia().testConexion();

            //Creando tablas de la Base de datos
            ServicioBootstrap.crearTablas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
