import Modelos.Articulo;
import Modelos.Etiqueta;
import Modelos.Usuario;
import Servicios.*;
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
    static Usuario usuario;

    public static void crearRutas(){
       final Configuration configuration = new Configuration(new Version(2, 3, 23));
       configuration.setClassForTemplateLoading(Main.class, "/");

       staticFiles.location("/publico");

       before("/", (req, res) -> {
           if (req.cookie("sesionSemanal") != null){
                Usuario usuarioRestaurado = ServicioUsuario.restaurarSesion(req.cookie("sesionSemanal"));
                nombreUsuario = usuarioRestaurado.getUsername();
                usuario = usuarioRestaurado;
                req.session().attribute("sesionUsuario", usuarioRestaurado);

               if(usuarioRestaurado != null) {
                   req.session(true);
                   req.session().attribute("sesionUsuario", usuarioRestaurado);
               }
           }

          if(req.session().attribute("sesionUsuario") == null){
              res.redirect("/login");
           }
        });

       before("/registrar", (req, res) -> {
          if(!usuario.isAdminstrator()){
              res.redirect("/");
          }
       });

       before("/articulo/crear", (req, res) -> {
          if(!usuario.isAdminstrator()){
              if(!usuario.isAutor()){
                  res.redirect("/");
              }
          }
       });

       before("/articulo/editar/:id", (req, res) ->{
           if(!usuario.isAdminstrator()){
               if(!usuario.isAutor()){
                   res.redirect("/");
               }
           }
       });

        before("/articulo/eliminar/:id", (req, res) ->{
            if(!usuario.isAdminstrator()){
                if(!usuario.isAutor()){
                    res.redirect("/");
                }
            }
        });

        before("articulo/:id", (req, res) -> {
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
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());
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
                usuario = ServicioUsuario.elUsuarioExiste(nombreUsuario, contrasena);

                if(usuario != null)
                {
                    req.session().attribute("sesionUsuario", usuario);

                    if(req.queryParams("guardarSesion") != null) {
                        res.cookie("/","sesionSemanal", req.session().id(), 604800, false);
                        boolean x = ServicioUsuario.guardarSesion(req.session().id(), usuario.getId());
                        System.out.println(req.cookie("sesionSemanal"));
                    }

                    res.redirect("/");
                } else {
                    res.redirect("/login");
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            return null;
        });

        get("/registrar", (req, res) -> {
            if(usuario.isAdminstrator()){
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("plantillas/registro.ftl");

                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());

                template.process(atributos, writer);

                return writer;
            }

            return null;
        });

        post("/registrarUsuario", (req, res) -> {
            String nombreUsuario = req.queryParams("username");
            String contrasena = req.queryParams("password");
            Long id = ServicioUsuario.conseguirTamano() + 1;
            Usuario usuarioNuevo = ServicioUsuario.elUsuarioExiste(nombreUsuario, contrasena);
            ServicioUsuario servicioUsuario = new ServicioUsuario();
            if(usuarioNuevo == null){
                servicioUsuario.registrarUsuarios(id, "'" + nombreUsuario + "'", "'" + contrasena + "'", false, true );
                res.redirect("/");
            }
            else{
                res.redirect("/registrar");
            }

            return null;
        });

        get("/salir", (req, res) ->
        {
            Session sesion = req.session(true);
            sesion.invalidate();

            res.removeCookie("sesionSemanal");

            res.redirect("/");

            return null;
        });

        path("/articulo", () -> {
           get("/crear", (req, res) -> {
               if(usuario.isAutor()) {
                   StringWriter writer = new StringWriter();
                   Map<String, Object> atributos = new HashMap<>();
                   Template template = configuration.getTemplate("plantillas/crear-articulo.ftl");

                   atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                   atributos.put("nombreUsuario", nombreUsuario);
                   atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                   atributos.put("esAdmin", usuario.isAdminstrator());
//               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//               LocalDate fecha = LocalDate.parse(new Date().toString(), formatter);
//               Poniendo en On Hold el formateo de la fecha
                   atributos.put("fechaActual", new Date().toString());
                   template.process(atributos, writer);

                   return writer;
               }
               res.redirect("/");
               return null;
           });

            post("/crear", (req, res) -> {
                if(usuario.isAutor()) {
                    long idArticulo = ServicioArticulo.conseguirTamano() + 1;
                    String titulo = req.queryParams("titulo");
                    String cuerpo = req.queryParams("cuerpo");
                    long usuarioID = usuario.getId();

                    String string = req.queryParams("fecha");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fecha = LocalDate.parse(string, formatter);
                    ServicioArticulo.crearArticulo(idArticulo, titulo, cuerpo, usuarioID, fecha);

                    String[] etiquetas = req.queryParams("etiquetas").split(",");
                    //etiquetasAux = ServicioEtiquetas.conseguirEtiquetas(idArticulo);

                    long articuloID = ServicioArticulo.buscarArticulo(idArticulo).getId();
                    long etiquetaIDAux;

                    for (int i = 0; i < etiquetas.length; i++) {
                        if (ServicioEtiquetas.conseguirID("select * from etiquetas;") != -1) {
                            etiquetaIDAux = ServicioEtiquetas.conseguirID("select * from etiquetas;") + 1;
                        } else {
                            etiquetaIDAux = 1;
                        }
                        ServicioBootstrap.ejecutarSQL("MERGE INTO etiquetas \n" +
                                "KEY(ID) \n" +
                                "VALUES (" + etiquetaIDAux + ", " + "'" + etiquetas[i] + "');");
                        long etiquetaID = ServicioEtiquetas.conseguirID("select * from etiquetas where etiqueta = '" + etiquetas[i] + "';");
                        ServicioBootstrap.ejecutarSQL("insert into articulosYetiquetas (articulo, etiqueta) values(" + articuloID + ", " + etiquetaID + ");");
                    }
                }
                res.redirect("/");

                return null;
            });

            get("/editar/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("plantillas/editar-articulo.ftl");

                Articulo articulo = ServicioArticulo.buscarArticulo(Long.parseLong(req.params("id")));

                atributos.put("articulo", articulo);
                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                template.process(atributos, writer);

                return writer;
            });

            post("/editar/:id", (req, res) -> {
                long id = Integer.parseInt(req.params("id"));
                String titulo = req.queryParams("titulo");
                String cuerpo = req.queryParams("cuerpo");
                long usuarioID = usuario.getId();

                String string = req.queryParams("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(string, formatter);
                ServicioArticulo.crearArticulo(id, titulo, cuerpo, usuarioID, fecha);

                String[] etiquetas = req.queryParams("etiquetas").split(",");

                long etiquetaIDAux;

                ServicioBootstrap.ejecutarSQL("DELETE FROM articulosyetiquetas WHERE articulo = " + id);

                for (int i = 0; i < etiquetas.length; i++) {
                    if (ServicioEtiquetas.conseguirID("select * from etiquetas;") != -1) {
                        etiquetaIDAux = ServicioEtiquetas.conseguirID("select * from etiquetas;") + 1;
                    } else {
                        etiquetaIDAux = 1;
                    }
                    ServicioBootstrap.ejecutarSQL("MERGE INTO etiquetas \n" +
                            "KEY(ID) \n" +
                            "VALUES (" + etiquetaIDAux + ", " + "'" + etiquetas[i] + "');");
                    long etiquetaID = ServicioEtiquetas.conseguirID("select * from etiquetas where etiqueta = '" + etiquetas[i] + "';");
                    ServicioBootstrap.ejecutarSQL("insert into articulosYetiquetas (articulo, etiqueta) values(" + id + ", " + etiquetaID + ");");
                }

                res.redirect("/");

                return null;
            });
            get("/eliminar/:id", (req, res) -> {
                if(usuario.isAdminstrator() || usuario.isAutor()) {
                    StringWriter writer = new StringWriter();
                    Map<String, Object> atributos = new HashMap<>();
                    Template template = configuration.getTemplate("plantillas/eliminar-articulo.ftl");

                    Articulo articulo = ServicioArticulo.buscarArticulo(Long.parseLong(req.params("id")));

                    atributos.put("articulo", articulo);
                    atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                    atributos.put("nombreUsuario", nombreUsuario);
                    atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                    atributos.put("esAdmin", usuario.isAdminstrator());
                    template.process(atributos, writer);

                    return writer;
                }
                res.redirect("/");
                return null;
            });

            post("/eliminar/:id", (req, res) -> {
                if(usuario.isAdminstrator() || usuario.isAutor()) {
                    ServicioBootstrap.ejecutarSQL("DELETE FROM comentarios where articuloid = " + req.params("id"));
                    ArrayList<Long> etiquetasID = ServicioEtiquetas.conseguirIDEtiquetas(Long.parseLong(req.params("id")));

                    for (Long etiqueta : etiquetasID) {
                        ServicioBootstrap.ejecutarSQL("DELETE FROM articulosyetiquetas where articulo = " + req.params("id"));
                        ServicioBootstrap.ejecutarSQL("DELETE FROM etiquetas where id = " + etiqueta);
                    }

                    ServicioArticulo.eliminarArticulo(Long.parseLong(req.params("id")));
                }
                res.redirect("/");
                return null;
            });

           get("/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("plantillas/articulo.ftl");

                Articulo articulo = ServicioArticulo.buscarArticulo(Long.parseLong(req.params("id")));

                atributos.put("articulo", articulo);
                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                template.process(atributos, writer);

                return writer;
           });

           post("/:id/comentar", (req, res) -> {
               Long id = ServicioComentario.conseguirTamano() + 1;
               Long articuloID = Long.parseLong(req.params("id"));
               String comentario = req.queryParams("comentario");
               Long autor = usuario.getId();

               ServicioComentario.crearComentario(id, comentario, autor, articuloID);

               res.redirect("/articulo/" +  articuloID);
               return null;
           });
        });

        notFound((req, res) -> {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate("plantillas/404.ftl");
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());

            template.process(atributos, writer);
            res.status(404);
            res.body(writer.toString());

            return writer;
        });
    }


}
