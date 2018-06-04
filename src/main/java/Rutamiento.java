import static spark.Spark.*;

public class Rutamiento {

    public static void crearRutas(){
        before((req, res) -> {
            if(req.cookie("sesionUsuario") == null){
                res.redirect("/registro");
            }
            else {
                res.redirect("/");
            }
        });

        get("/registro", (req, res) -> {
            return "";
        });
    }
}
