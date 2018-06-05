import Modelos.Articulo;
import Modelos.Usuario;
import Servicios.ServicioArticulo;
import Servicios.ServicioUsuario;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Enrutamiento {

    public static void crearRutas(){
        final Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFiles.location("/publico");

       /*before((req, res) -> {
          if(req.cookie("sesionUsuario") == null){
               res.redirect("/login");
           }
           else {
               res.redirect("/");
           }
        });*/

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/index.ftl");
            ArrayList<Articulo> articulos = new ArrayList<>();
            articulos = ServicioArticulo.listarArticulos();
            atributos.put("articulos", articulos);
            template.process(atributos, writer);

            return writer;
        });

        get("/registro", (req, res) -> {
            return "";
        });

        get("/login", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/login.ftl");
            template.process(atributos, writer);

            return writer;
        });

        post("/loginDatosEntrados", (req, res) -> {
            try{
                ServicioUsuario servicioUsuario = new ServicioUsuario();
                String username = req.queryParams("username");
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

            return "";
        });
    }


}
