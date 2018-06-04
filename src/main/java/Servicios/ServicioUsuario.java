package Servicios;

import Modelos.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioUsuario {
    public boolean crearUsuarioPorDefecto(){
        boolean creadoCorrectamente = false;
        Connection conexion = null;

        // Variables del usuario por defecto
        Integer id = 1;
        String username = "admin";
        String password = "1234";
        Boolean administrator = true;
        Boolean autor = true;
        try {
            String usuarioDefecto = "insert into usuarios(id, username, password, administrator, autor) values(?,?,?,?,?)";
            conexion = ServicioBaseDatos.getInstancia().getConexion();
            PreparedStatement prepareStatement = conexion.prepareStatement(usuarioDefecto);

            // Antes de ejecutar se ponen los parámetros del usuario por defecto
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, username);
            prepareStatement.setString(3, password);
            prepareStatement.setBoolean(4, administrator);
            prepareStatement.setBoolean(5, autor);
            //
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
}
