import Modelos.Articulo;
import Servicios.ServicioArticulo;
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

//        before((req, res) -> {
//            if(req.cookie("sesionUsuario") == null){
//                res.redirect("/registro");
//            }
//            else {
//                res.redirect("/");
//            }
//        });

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/index.ftl");
            ArrayList<Articulo> articulos = new ArrayList<>();
            articulos = ServicioArticulo.listarArticulos();
            atributos.put("articulos", articulos);
            template.process(null, writer);

            return writer;
        });

        get("/registro", (req, res) -> {
            return "";
        });
    }
}
