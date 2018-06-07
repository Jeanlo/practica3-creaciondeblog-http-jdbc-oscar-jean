/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServicioBootstrap {

    /*
            Se encarga de iniciar la base de datos para poder hacer
            transacciones y demás acciones.
     */

    public static void iniciarBaseDatos() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    /*
            Se encarga de detener la base de datos, en el caso de que sea
            necesario.
     */

    public static void detenerBaseDatos() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    /*
           Ejecuta cualquier consulta SQL que se le pase
           como parámetro.
     */

    public static void ejecutarSQL(String sql) throws SQLException {
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        Statement statement = conexion.createStatement();

        statement.execute(sql);
        statement.close();

        conexion.close();
    }

    /*
            Crea todas las tablas por medio de SQL.
     */

    public static void crearTablas() throws SQLException {
        ejecutarSQL(
            "CREATE TABLE IF NOT EXISTS etiquetas\n" +
            "(\n" +
            "id BIGINT auto_increment PRIMARY KEY,\n" +
            "etiqueta VARCHAR(100) NOT NULL\n" +
            ");");

        ejecutarSQL(
            "CREATE TABLE IF NOT EXISTS usuarios\n" +
            "(\n" +
            "id BIGINT PRIMARY KEY NOT NULL,\n" +
             "username VARCHAR(40) UNIQUE NOT NULL, \n" +
                    "password VARCHAR(40) NOT NULL, \n" +
                    "administrator BOOLEAN NOT NULL, \n" +
                    "autor BOOLEAN NOT NULL" +
            ");");

        ejecutarSQL(
                "CREATE TABLE IF NOT EXISTS articulos\n" +
                        "(\n" +
                        "id BIGINT PRIMARY KEY NOT NULL,\n" +
                        "titulo VARCHAR(100) UNIQUE NOT NULL, \n" +
                        "cuerpo VARCHAR(10000) NOT NULL, \n" +
                        "usuarioID BIGINT, \n" +
                        "fecha DATE NOT NULL, \n" +
                        "FOREIGN KEY(usuarioID) REFERENCES usuarios(id)" +
                        ");");

        ejecutarSQL(
                "CREATE TABLE IF NOT EXISTS comentarios\n" +
                        "(\n" +
                        "id BIGINT PRIMARY KEY NOT NULL,\n" +
                        "comentario VARCHAR(1000) NOT NULL, \n" +
                        "autor VARCHAR(40) NOT NULL, \n" +
                        "articuloID BIGINT, \n" +
                        "FOREIGN KEY(articuloID) REFERENCES articulos(id)" +
                        ");");

        ejecutarSQL("create table if not exists articulosYetiquetas\n" +
                "  (\n" +
                "    id bigint auto_increment PRIMARY KEY,\n" +
                "    articulo bigint,\n" +
                "    etiqueta bigint,\n" +
                "    FOREIGN KEY (articulo) REFERENCES articulos(id),\n" +
                "    FOREIGN KEY (etiqueta) REFERENCES etiquetas(id)\n" +
                "  );");

    }
}
