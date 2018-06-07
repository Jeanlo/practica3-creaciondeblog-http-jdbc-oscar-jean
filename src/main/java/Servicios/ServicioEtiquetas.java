/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Servicios;

import Modelos.Etiqueta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ServicioEtiquetas {

    /*
            Te permite conseguir todas las etiquetas que hay registradas en la base de datos por medio del form.
    */

    public static ArrayList<Etiqueta> conseguirEtiquetas(Long idArticulo)
    {
        ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
        try
        {
            ServicioBaseDatos servicioBaseDatos = new ServicioBaseDatos();
            Connection conexion = servicioBaseDatos.getConexion();
            Statement statement = conexion.createStatement();

            for(Long id: conseguirIDEtiquetas(idArticulo)){
                ResultSet resultado = statement.executeQuery("select * from etiquetas ORDER BY ID where id = " + id + ";");

                while (resultado.next())
                {
                    etiquetas.add(new Etiqueta(resultado.getLong("id"), resultado.getString("etiqueta")));
                }
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

    public static ArrayList<Long> conseguirIDEtiquetas(Long idArticulo)
    {
        ArrayList<Long> IDetiquetas = new ArrayList<Long>();
        try
        {
            ServicioBaseDatos servicioBaseDatos = new ServicioBaseDatos();
            Connection conexion = servicioBaseDatos.getConexion();
            Statement statement = conexion.createStatement();

            ResultSet resultado = statement.executeQuery("select * from ARTICULOSYETIQUETAS where articulo = " + idArticulo+ ";");
            while (resultado.next())
            {
                IDetiquetas.add(resultado.getLong("etiqueta"));
            }
            statement.close();
            conexion.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return IDetiquetas;
    }

   /*
        Te permite conseguir el ID de cualquier consulta que se le pase, en el caso de que tenga ID la tabla, que en sí es válido
        para todas las tablas existentes.
    */


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
