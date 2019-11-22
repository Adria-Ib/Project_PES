import org.junit.*;
import play.test.*;
import models.*;
import play.test.Fixtures;

public class BasicTest extends UnitTest {

    @Test
    public void testProducte(){

	Producte.deleteAll();
    	Producte p = new Producte("prod",120).save();
	p.IncrementarQuantitat(10);
	
     Producte p2 = Producte.find("byNom","prod").first();

	assertNotNull(p2);
	assertEquals(p2.QuantitatProducte(),10);
	assertEquals(Producte.count(),1);	

    }

    @Test
    public void testClient(){

	Admin.deleteAll();
	new Admin("Pedro", 54).save();

	Admin c = Admin.find("byNom","Pedro").first();

	assertNotNull(c);

	assertNotNull(c.lcompra);

	assertEquals(c.lcompra.size(),0);

    }


	@After
      public void TestGlobal(){

	Producte p = Producte.find("byNom","prod").first();
	
      assertNotNull(p);
	 assertEquals(p.QuantitatProducte(),10);
      }

	}
