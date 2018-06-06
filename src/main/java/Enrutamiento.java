import Modelos.Articulo;
import Modelos.Usuario;
import Servicios.ServicioArticulo;
import Servicios.ServicioUsuario;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Session;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Enrutamiento {
    static String username = "";
    public static void crearRutas(){
        final Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFiles.location("/publico");

       before("/", (req, res) -> {
          if(req.session().attribute("sesionUsuario") == null){
               res.redirect("/login");
           }
        });

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/index.ftl");
            ArrayList<Articulo> articulos = ServicioArticulo.listarArticulos();
            atributos.put("articulos", articulos);
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", username);
            template.process(atributos, writer);

            return writer;
        });

        get("/login", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/login.ftl");
            template.process(atributos, writer);

            return writer;
        });

        post("/login", (req, res) -> {
            try{
                username = req.queryParams("username");
                String contrasena = req.queryParams("password");
                Usuario usuario = ServicioUsuario.elUsuarioExiste(username, contrasena);

                if(usuario != null)
                {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuario);
                    res.redirect("/");
                } else {
                    res.redirect("/login");
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            return null;
        });

        post("/registrar", (req, res) -> {
            return null;
        });

        get("/salir", (req, res) ->
        {
            Session sesion = req.session(true);
            sesion.invalidate();

            res.redirect("/");

            return null;
        });
    }


}
