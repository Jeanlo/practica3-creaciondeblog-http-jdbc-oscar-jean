import Modelos.Articulo;
import Modelos.Etiqueta;
import Modelos.Usuario;
import Servicios.ServicioArticulo;
import Servicios.ServicioBootstrap;
import Servicios.ServicioEtiquetas;
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

    static ArrayList<Etiqueta> etiquetasAux = new ArrayList<>();
    static ArrayList<Articulo> articulos = ServicioArticulo.listarArticulos();
    static String nombreUsuario = "";
    static Boolean etiquetasBool = false;

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

            Articulo articulo = new Articulo();
            articulo.setListaEtiquetas(etiquetasAux);

            atributos.put("articulos", articulos);
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            /*
            atributos.put("etiquetasBool", etiquetasBool);

            if(etiquetasBool) {
                atributos.put("etiquetas", articulo.getListaEtiquetas().get(1));
            }
            */
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
//               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//               LocalDate fecha = LocalDate.parse(new Date().toString(), formatter);
//               Poniendo en On Hold el formateo de la fecha
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

                /*

                String[] etiquetas = req.queryParams("etiquetas").split(",");
                 etiquetasAux = ServicioEtiquetas.conseguirEtiquetas();


                long articuloID = ServicioEtiquetas.conseguirID("select * from articulos");

                for (int i = 0; i < etiquetas.length; i++)
                {
                    boolean encontrado = false;
                    for (Etiqueta etiqueta: etiquetasAux)
                    {
                        if(etiqueta.getEtiqueta().equals(etiquetas[i]))
                        {
                            encontrado = true;
                        }
                    }
                    if(!encontrado)
                    {
                        ServicioBootstrap.ejecutarSQL("insert into etiquetas (etiqueta) values ('" + etiquetas[i] + "')");
                    }

                    long etiquetaID = ServicioEtiquetas.conseguirID("select * from etiquetas where etiqueta ='" + etiquetas[i] + "'");
                    ServicioBootstrap.ejecutarSQL("insert into articulosYetiquetas (articulo, etiqueta) values(" + articuloID +", " + etiquetaID +")");
                }

                etiquetasBool = true;

                */
                res.redirect("/");

                return null;
            });

            get("/editar/:id", (req, res) -> {
                for(Articulo articulo : articulos) {
                    if(articulo.getId() == Integer.parseInt( req.params("id"))) {
                        StringWriter writer = new StringWriter();
                        Map<String, Object> atributos = new HashMap<>();
                        Template template = configuration.getTemplate("plantillas/editar-articulo.ftl");

                        atributos.put("articulo", articulo);
                        atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                        atributos.put("nombreUsuario", nombreUsuario);
                        template.process(atributos, writer);

                        return writer;
                    }
                }

                return null;
            });

            post("/editar/:id", (req, res) -> {
                long id = Integer.parseInt(req.params("id"));
                String titulo = req.queryParams("titulo");
                String cuerpo = req.queryParams("cuerpo");

                String string = req.queryParams("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(string, formatter);
                ServicioArticulo.crearArticulo(id, titulo, cuerpo, fecha);

                res.redirect("/");

                return null;
            });

            get("/eliminar/:id", (req, res) -> {
                ServicioArticulo.eliminarArticulo(Long.parseLong(req.params("id")));
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

        notFound((req, res) -> {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate("plantillas/404.ftl");
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            template.process(atributos, writer);
            res.status(404);
            res.body(writer.toString());

            return writer;
        });
    }


}
