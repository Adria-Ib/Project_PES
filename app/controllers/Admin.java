package controllers;

import play.*;
import play.mvc.*;

@With(Secure.class)
public class Admin extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            models.Admin user = models.Admin.find("byNom", Security.connected()).first();
            renderArgs.put("user", user.nom);
        }
    }

    public static void index() {
        renderTemplate("Application/index.html");
    }

}
