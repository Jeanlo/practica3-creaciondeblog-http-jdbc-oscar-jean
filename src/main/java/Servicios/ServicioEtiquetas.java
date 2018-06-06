package Servicios;

import Modelos.Etiqueta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ServicioEtiquetas {

    public static ArrayList<Etiqueta> conseguirEtiquetas()
    {
        ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
        try
        {
            ServicioBaseDatos servicioBaseDatos = new ServicioBaseDatos();
            Connection conexion = servicioBaseDatos.getConexion();
            Statement statement = conexion.createStatement();

            ResultSet resultado = statement.executeQuery("select * from Etiquetas ORDER BY ID");
            while (resultado.next())
            {
                etiquetas.add(new Etiqueta(resultado.getLong("id"), resultado.getString("etiqueta")));
            }
            statement.close();
            conexion.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
    }
        return etiquetas;
    }

    public static long conseguirID(String consulta)
    {
        long idCualquierTabla = -1;

        try
        {
            ServicioBaseDatos servicioBaseDatos = new ServicioBaseDatos();
            Connection conexion = servicioBaseDatos.getConexion();
            Statement statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery(consulta);
            while (rs.next())
            {
                idCualquierTabla = rs.getLong("id");
            }
            statement.close();
            conexion.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return idCualquierTabla;
    }
}
