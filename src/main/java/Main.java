/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

import Servicios.ServicioBaseDatos;
import Servicios.ServicioBootstrap;
import Servicios.ServicioUsuario;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            //Iniciando el servicio de Base de datos
            ServicioBootstrap.iniciarBaseDatos();

            //Prueba de conexión
            ServicioBaseDatos.getInstancia().testConexion();

            //Creando tablas de la Base de datos
            ServicioBootstrap.crearTablas();

            // Crear usuario por defecto
            ServicioUsuario serviciouser = new ServicioUsuario();
            serviciouser.crearUsuarioPorDefecto();

            Enrutamiento.crearRutas();

            //Deteniendo el servicio de Base de datos
            ServicioBootstrap.detenerBaseDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
