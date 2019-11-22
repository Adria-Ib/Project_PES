
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
            Canso a = new Canso("qwkdm", 2019, "qenceiuqlnicjnq").save();
            a.AddCantantNomPais( "Adria", "spain");
            Canso b= new Canso("qwkm", 2017, "qenceiafuqlnicjnq").save();
            b.AddCantantNomPais( "Adria", "spain");
            Canso c = new Canso("qwkasdm", 2011, "qenceiasuqlnicjnq").save();
            c.AddCantantNomPais( "Estrela", "spain");
        }
    }

}
