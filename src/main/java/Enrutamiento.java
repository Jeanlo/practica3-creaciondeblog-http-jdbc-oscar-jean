import Modelos.Articulo;
import Modelos.Usuario;
import Servicios.ServicioArticulo;
import Servicios.ServicioUsuario;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Session;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Enrutamiento {

    static ArrayList<Articulo> articulos = new ArrayList<>();
    static String nombreUsuario = "";

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
            articulos = ServicioArticulo.listarArticulos();
            atributos.put("articulos", articulos);
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
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
                nombreUsuario = req.queryParams("username");
                String contrasena = req.queryParams("password");
                Usuario usuario = ServicioUsuario.elUsuarioExiste(nombreUsuario, contrasena);

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





        path("/articulo", () -> {
           get("/crear", (req, res) -> {
               StringWriter writer = new StringWriter();
               Map<String, Object> atributos = new HashMap<>();
               Template template = configuration.getTemplate("plantillas/crear-articulo.ftl");

               atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
               atributos.put("nombreUsuario", nombreUsuario);
               atributos.put("fechaActual", new Date().toString());
               template.process(atributos, writer);

               return writer;
           });

            post("/crear", (req, res) -> {
                long id = articulos.size() + 1;
                String titulo = req.queryParams("titulo");
                String cuerpo = req.queryParams("cuerpo");

                String string = req.queryParams("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(string, formatter);

                ServicioArticulo.crearArticulo(id, titulo, cuerpo, fecha);

                res.redirect("/");

                return null;
            });

           get("/:id", (req, res) -> {
                for(Articulo articulo : articulos) {
                    if(articulo.getId() == Integer.parseInt( req.params("id"))) {
                        StringWriter writer = new StringWriter();
                        Map<String, Object> atributos = new HashMap<>();
                        Template template = configuration.getTemplate("plantillas/articulo.ftl");

                        atributos.put("articulo", articulo);
                        atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                        atributos.put("nombreUsuario", nombreUsuario);
                        template.process(atributos, writer);

                        return writer;
                    }
                }

                return null;
           });
        });
    }


}
