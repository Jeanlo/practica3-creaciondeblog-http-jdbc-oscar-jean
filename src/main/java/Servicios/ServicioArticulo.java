/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Servicios;

import Modelos.Articulo;
import Modelos.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServicioArticulo  {
    public static ArrayList<Articulo> listarArticulos() {
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();
        ArrayList<Articulo> articulos = new ArrayList<>();

        try {
            // Consultando todos los articulos.
            String articulosQuery = "SELECT * FROM articulos ORDER BY fecha DESC;";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(articulosQuery);

            while(resultado.next()) {
                articulos.add(
                    new Articulo(resultado.getLong("id"),
                        resultado.getNString("titulo"),
                        resultado.getNString("cuerpo"),
                        null,
                        resultado.getDate("fecha"),
                        null,
                        null
                    )
                );
            }

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return articulos;
    }

    public static Articulo buscarArticulo(long id) {
        Articulo articulo = null;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String articuloEncontrado = "SELECT * FROM articulos WHERE id = " + id + ";";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(articuloEncontrado);
            ResultSet rs = prepareStatement.executeQuery();

            while(rs.next()) {
                // TODO Obtener los verdaderos datos del usuario
                articulo = new Articulo(rs.getLong("id"), rs.getNString("titulo"), rs.getNString("cuerpo"), new Usuario(rs.getLong("usuarioid"), "admin", "1234", true, true), rs.getDate("fecha"), null, null);
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

        return articulo;
    }

    public static boolean crearArticulo(long id, String titulo, String cuerpo, LocalDate fecha) {
        boolean creadoCorrectamente = false;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String articuloNuevo = "MERGE INTO articulos \n" +
                    "KEY(ID) \n" +
                    "VALUES (" + id + ",'" + titulo + "','" + cuerpo + "'," + 1 + ",'" + fecha + "');";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(articuloNuevo);

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

    public static void eliminarArticulo(Long id) {
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();
        ArrayList<Articulo> articulos = new ArrayList<>();
        boolean creadoCorrectamente;

        try {
            // Consultando y eliminando el articulo que tenga el id indicando.
            String eliminarArticuloQuery = "DELETE FROM articulos where ID = " + id + ";";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(eliminarArticuloQuery);

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
    }
}
