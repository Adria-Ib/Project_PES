
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
            Canso a = new Canso("Don't you worry child", 2012, "There was a time, I used to look into my father's eyes In a happy home, I was a king I had a golden throne ...").save();
            a.AddCantantNomPais( "Axwell", "Suècia");
            a.AddCantantNomPais( "Sebastian Ingrosso", "Suècia");
            a.AddCantantNomPais( "Steve Angello", "Suècia");
            a.save();
            Canso b= new Canso("Belong", 2016, "Take a look at how I'm doing There's only so much to get me through the day Take a deep breath of a smell that I ...").save();
            b.AddCantantNomPais( "Axwell", "Suècia");
            b.AddCantantNomPais( "Shapov", "Rússia");
            b.save();
            Canso c = new Canso("Remember", 2015, "Remember when you had a dream? Remember when you had a heart? Remember when it was so simple, this world...").save();
            c.AddCantantNomPais( "Steve Angello", "Suècia");
            c.save();
            Canso d = new Canso("Calling (Lose my mind)", 2011, "I see you walking through the rain And I see the water covering your teardrops on your face...").save();
            d.AddCantantNomPais( "Sebastian Ingrosso", "Suècia");
            d.AddCantantNomPais( "Alesso", "Suècia");
            d.save();
            Canso e = new Canso("More than you know", 2017, "I saw it coming, from miles away I better speak up if I got something to say 'Cause it ain't over...").save();
            e.AddCantantNomPais( "Sebastian Ingrosso", "Suècia");
            e.AddCantantNomPais( "Axwell", "Suècia");
            e.save();
        }
    }

}
