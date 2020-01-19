
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
            Canso a = new Canso("Canso1", 2019, "Lletra de la canço1").save();
            a.AddCantantNomPais( "Cantant1", "pais1",null);
            Canso b= new Canso("Canso2", 2017, "Lletra de la canço2").save();
            b.AddCantantNomPais( "Cantant1", "pais1",null);
            b.save();
            Canso c = new Canso("Canso3", 2011, "Lletra de la canço3").save();
            c.AddCantantNomPais( "Cantant2", "pais2",null);
            c.save();
        }
    }

}
