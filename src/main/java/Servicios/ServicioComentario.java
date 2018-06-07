package Servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ServicioComentario {

    public static boolean crearComentario(long id, String comentario, Long autor, Long articuloID) {
        boolean creadoCorrectamente = false;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String comentarioNuevo = "MERGE INTO comentarios \n" +
                    "KEY(ID) \n" +
                    "VALUES (" + id + ",'" + comentario + "'," + autor + "," + articuloID + ");";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(comentarioNuevo);

            // Si se ejecutó el query bien pues la cantidad de filas de la tabla debe ser mayor a 0, pues se ha insertado una fila.
            int fila = prepareStatement.executeUpdate();
            creadoCorrectamente = fila > 0 ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return creadoCorrectamente;
    }

    public static Long conseguirTamano() {
        Long ultimoID = new Long(0);
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String conseguirTamanoTabla = "SELECT TOP 1 * FROM comentarios ORDER BY ID DESC;";

            // Ejecuta el query.
            PreparedStatement prepareStatement = conexion.prepareStatement(conseguirTamanoTabla);
            ResultSet resultado = prepareStatement.executeQuery();
            while(resultado.next()){
                ultimoID = resultado.getLong("id");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ultimoID;
    }
}
