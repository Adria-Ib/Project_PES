
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
            Cantant a1 = new Cantant("Axwell", "Suècia");
            a1.save();
            a1.AddCanso(a);
            Cantant a2 = new Cantant( "Sebastian Ingrosso", "Suècia");
            a2.save();
            a2.AddCanso(a);
            Cantant a3 = new Cantant("Steve Angello", "Suècia");
            a3.save();
            a3.AddCanso(a);

            a.save();
            Canso b= new Canso("Belong", 2016, "Take a look at how I'm doing There's only so much to get me through the day Take a deep breath of a smell that I ...").save();
            a1.AddCanso(b);
            Cantant a4 = new Cantant( "Shapov", "Rússia");
            a4.save();
            a4.AddCanso(b);
            b.save();
            Canso c = new Canso("Remember", 2015, "Remember when you had a dream? Remember when you had a heart? Remember when it was so simple, this world...").save();
            a3.AddCanso(c);
            c.save();
            Canso d = new Canso("Calling (Lose my mind)", 2011, "I see you walking through the rain And I see the water covering your teardrops on your face...").save();
            a2.AddCanso(d);
            Cantant a5 = new Cantant( "Alesso", "Suècia");
            a5.save();
            a5.AddCanso(d);
            d.save();
            Canso e = new Canso("More than you know", 2017, "I saw it coming, from miles away I better speak up if I got something to say 'Cause it ain't over...").save();
            a1.AddCanso(e);
            a2.AddCanso(e);
            e.save();
        }
    }

}
