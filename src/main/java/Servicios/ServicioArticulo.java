package Servicios;

import Modelos.Articulo;

import java.sql.*;
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
            return articulos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
