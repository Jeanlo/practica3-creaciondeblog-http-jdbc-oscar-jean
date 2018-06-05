import freemarker.template.Configuration;
import freemarker.template.Version;

import static spark.Spark.*;

public class Enrutamiento {

    public static void crearRutas(){
        final Configuration configuration = new Configuration(new Version(2, 3, 28));
        configuration.setClassForTemplateLoading(Main.class, "/");

        before((req, res) -> {
            if(req.cookie("sesionUsuario") == null){
                res.redirect("/registro");
            }
            else {
                res.redirect("/");
            }
        });

        get("/", (req, res) -> {
           return "";
        });

        get("/registro", (req, res) -> {
            return "";
        });
    }
}
