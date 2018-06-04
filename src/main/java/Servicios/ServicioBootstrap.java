package Servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServicioBootstrap {
    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void stopDb() throws SQLException {
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
            "");
    }
}
