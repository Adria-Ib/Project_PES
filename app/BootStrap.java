
import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class BootStrap extends Job {

    public void doJob() {
        // Check if the database is empty
        if(Admin.count() == 0) {
            //Fixtures.loadModels("initial-data.yml");
            new Admin("admin", "adminp", 50).save();
            new Admin("lola","lolap",50).save();
            Canso a = new Canso("Canço1", 2019, "Lletra de la canço1").save();
            a.AddCantantNomPais( "Cantant1", "pais1");
            Canso b= new Canso("Canço2", 2017, "Lletra de la canço2").save();
            b.AddCantantNomPais( "Cantant1", "pais1");
            Canso c = new Canso("Canço1", 2011, "Lletra de la canço1").save();
            c.AddCantantNomPais( "Cantant2", "pais2");
        }
    }

}
