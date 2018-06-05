package Servicios;

import Modelos.Articulo;
import Modelos.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class ServicioUsuario {
    public boolean crearUsuarioPorDefecto(){
        boolean creadoCorrectamente = false;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String usuarioDefecto = "MERGE INTO usuarios \n" +
                    "KEY(ID) \n" +
                    "VALUES (1, 'admin', '1234', true, true);";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(usuarioDefecto);

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

    public boolean registrarUsuarios(int id, String usuario, String password, boolean administador, boolean autor){
        boolean creadoCorrectamente = false;
        Connection conexion = ServicioBaseDatos.getInstancia().getConexion();

        try {
            // Crealo si no existe y si existe actualizalo.
            String usuarioNuevo = "MERGE INTO usuarios \n" +
                    "KEY(ID) \n" +
                    "VALUES (" + id + "," + usuario + "," + password + "," + administador + "," + autor + ");";

            // Ejecuta el query pasado por parámetro "usuarioDefecto".
            PreparedStatement prepareStatement = conexion.prepareStatement(usuarioNuevo);

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

    public static Usuario elUsuarioExiste(String nombreUsuario, String password)
    {
        try
        {
            ServicioBaseDatos servicioBaseDatos = new ServicioBaseDatos();
            Connection conexion = servicioBaseDatos.getConexion();

            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from Usuarios where username = '" + nombreUsuario +"' and password = '" + password + "'");
            while (rs.next())
            {
                return new Usuario(rs.getLong("id"), rs.getNString("username"), rs.getNString("password"), rs.getBoolean("administrator"), rs.getBoolean("autor"));
            }
            statement.close();
            conexion.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
