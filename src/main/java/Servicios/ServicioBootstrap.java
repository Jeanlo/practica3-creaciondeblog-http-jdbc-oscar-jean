package Servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServicioBootstrap {
    public static void iniciarBaseDatos() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void detenerBaseDatos() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public static void ejecutarSQL(String sql) throws SQLException {
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        Statement statement = conexion.createStatement();

        statement.execute(sql);
        statement.close();

        conexion.close();
    }

    public static void crearTablas() throws SQLException {
        ejecutarSQL(
            "CREATE TABLE IF NOT EXISTS etiquetas\n" +
            "(\n" +
            "id BIGINT PRIMARY KEY NOT NULL,\n" +
            "etiqueta VARCHAR(100) NOT NULL,\n" +
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
                        "comentario VARCHAR(1000) UNIQUE NOT NULL, \n" +
                        "autor VARCHAR(40) NOT NULL, \n" +
                        "articuloID BIGINT, \n" +
                        "FOREIGN KEY(articuloID) REFERENCES articulos(id)" +
                        ");");

    }
}
