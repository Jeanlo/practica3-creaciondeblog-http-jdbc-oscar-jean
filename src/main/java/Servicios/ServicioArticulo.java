/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Servicios;

import Modelos.Articulo;
import Modelos.Comentario;
import Modelos.Etiqueta;
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
                ArrayList<Comentario> comentarios = ServicioComentario.listarComentarios(resultado.getLong("id"));
                ArrayList<Etiqueta> etiquetas = ServicioEtiquetas.conseguirEtiquetas(resultado.getLong("id"));

                articulos.add(
                    new Articulo(resultado.getLong("id"),
                        resultado.getNString("titulo"),
                        resultado.getNString("cuerpo"),
                        ServicioUsuario.buscarUsuario(resultado.getLong("usuarioid")),
                        resultado.getDate("fecha"),
                        comentarios,
                        etiquetas
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
                ArrayList<Comentario> comentarios = ServicioComentario.listarComentarios(rs.getLong("id"));
                ArrayList<Etiqueta> etiquetas = ServicioEtiquetas.conseguirEtiquetas(rs.getLong("id"));

                // TODO Obtener los verdaderos datos del usuario
                articulo = new Articulo(
                    rs.getLong("id"),
                    rs.getNString("titulo"),
                    rs.getNString("cuerpo"),
                    ServicioUsuario.buscarUsuario(rs.getLong("usuarioid")),
                    rs.getDate("fecha"),
                    comentarios, etiquetas
                );
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

    public static boolean crearArticulo(long id, String titulo, String cuerpo, long usuarioID, LocalDate fecha) {
        boolean creadoCorrectamente = false;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String articuloNuevo = "MERGE INTO articulos \n" +
                    "KEY(ID) \n" +
                    "VALUES (" + id + ",'" + titulo + "','" + cuerpo + "'," + usuarioID + ",'" + fecha + "');";

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

    public static Long conseguirTamano() {
        Long ultimoID = new Long(0);
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String conseguirTamanoTabla = "SELECT TOP 1 * FROM articulos ORDER BY ID DESC;";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
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
