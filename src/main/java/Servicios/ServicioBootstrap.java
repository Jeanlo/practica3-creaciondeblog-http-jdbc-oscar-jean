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

    public static void crearTablas() throws SQLException {
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        Statement statement = conexion.createStatement();

        String sqlEtiqueta =
            "CREATE TABLE IF NOT EXISTS etiqueta\n" +
            "(\n" +
            "  id BIGINT PRIMARY KEY NOT NULL,\n" +
            "  etiqueta VARCHAR(100) NOT NULL,\n" +
            ");";

        statement.execute(sqlEtiqueta);
        statement.close();

        conexion.close();
    }
}
