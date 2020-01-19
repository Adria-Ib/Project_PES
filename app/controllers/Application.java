package controllers;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import models.Admin;
import models.Canso;
import models.Cantant;
import netscape.javascript.JSObject;
import play.*;
import play.mvc.*;
import play.data.validation.*;

import javax.lang.model.type.NullType;
import java.util.*;
import flexjson.*;

import java.util.List;

public class Application extends Controller  {
	private static JSONSerializer serializer = new JSONSerializer();


	private static void returnJSON(Object object){
		renderJSON(serializer.serialize(object));
	}

	@Before
	static void addUser() {
    	Admin user = connected();

		if(user != null) {
			renderArgs.put("client", user);
		}
	}

	//User bob = User.find("byEmail", "bob@gmail.com").first();

	public static void buscarCanso(String nom)
	{
		Canso song = Canso.find("byNom", nom).first();
		String s = "";
		String lyrics = "";
		int u = 0;
		if (song!=null) {
			while(u < song.getNum()){
				if(u == song.getNum() - 1 && u!= 0) {
					s = s + " i " + song.getCantant(u);
				}
				if(u == song.getNum() - 1 && u== 0){
					s = s + song.getCantant(u);
				}
				else{
					s = s + song.getCantant(u) + ", ";
				}
				u = u + 1;
			}
			lyrics = song.getLletra();
			render(nom, s,lyrics);
			//renderText("La cançó " + nom + " de " + s + "  té la següent lletra " + song.getLletra());
		}
		else{
			render('0');
			//renderText("No hi ha cap cançó registrada amb aquest nom");
		}
	}

	public static void buscarCantant(String nom)
	{
		Cantant singer = Cantant.find("byNom", nom).first();
		String s = "";
		int u = 0;
		if (singer!=null) {
			while(u < singer.getNum()){
				if(u == singer.getNum() - 1 && u!= 0) {
					s = s + " i " + singer.getCanso(u);
				}
				if(u == singer.getNum() - 1 && u== 0){
					s = s + singer.getCanso(u);
				}
				else{
					s = s + singer.getCanso(u) + ", ";
				}
				u = u + 1;
			}
			renderText("En/Na " + nom + "  nascut a " + singer.getPais() + " es troba a la nostra base de dades\n"+"Les seves cansons son: " + s);
		}
		else{
			renderText("No hi ha cap cantant registrat/da amb aquest nom");
		}
	} /*funcions mostrar cansons per any, buscar per pais del cantant, buscar per lletra una canso--> alike en lloc de byName*/
	public static void buscarCansoAny(int any)
	{
		List<Canso> song = Canso.find("byData", any).fetch();
		int u = song.size();
		boolean first = true;
		int w = 0;
		String s ="";
        for (Canso c: song) {
            if (!first) {
                s += ", ";
            }
            first = false;
            s = s + c.getNom();
        }
        if (u != 0){
			renderText("Les cançons de l'any " + String.valueOf(any) + " son les següents " + s);
		}
		else{
			renderText("No hi ha cap cançó publicada l'any " + String.valueOf(any) + " en les nostres bases de dades");
		}
	}
	static Admin connected() {

		if(renderArgs.get("client") != null) {
			return renderArgs.get("client", Admin.class);
		}
		String username = session.get("user");
		if(username != null) {
			return Admin.find("byNom", username).first();
		}

		return null;
	}

	// ~~

	public static void loginTemplate() {
		if(connected() != null) {
			Admin c = renderArgs.get("client", Admin.class);
			String username = session.get("user");
			//renderText("Connectat  " + c.nom + c.password +"---"+username);
			render();
		}else {
			render();
		}
	}
	public static void home() { render(); }

	public static void register() {
		render();
	}

	public static void saveUser(@Valid Admin user, String verifyPassword) {
		validation.required(verifyPassword);
		validation.equals(verifyPassword, user.password).message("Your password doesn't match");
		if(validation.hasErrors()) {
			render("@register", user, verifyPassword);
		}
		if (Admin.find("byNomAndPassword",user.nom,user.password).first()==null) {
			user.create();
			session.put("user", user.nom);
			renderText("Usuari registrat " + user.nom);

		}else{
			renderText("Usuari ja existeix!!!");
		}
	}

	//public static void login(String username, String password) {
	public static void login(@Valid Admin user){
		Admin u = Admin.find("byNomAndPassword", user.nom, user.password).first();
		if(u != null) {
			session.put("user", user.nom);
			renderTemplate("Application/home.html");
		}else {
			// Oops
			renderText("User not registered");
		}
	}

	public static void loginandroid(Admin user){
		Admin u = Admin.find("byNomAndPassword", user.nom, user.password).first();
		if(u != null) {
			//session.put("user", user.nom);
			renderText("OK");
		}else {
			// Oops
			renderText("FAIL");
		}
	}

	public static void logout() {
		session.clear();
		renderArgs.put("client",null);
		renderTemplate("Application/loginTemplate.html");
	}

	public static void getInfoSession(){
		renderText("Està connectat "+ session.get("user"));
	}
	public static void SubmitCantant(String n, String p) {
		render(n,p);
	}
	public static void SuccessCantant(String n, String p, Canso canso) {
		Canso c = new Canso("Canço1", 2019, "Lletra de la canço1");
		c.AddCantantNomPais(n, p,canso);
		c.save();
		render(n,p);
	}

	public static void GetCantants(){
		List<Cantant> cantantList = Cantant.findAll();

		returnJSON(/*serializer.serialize*/(cantantList));
	}

	public static void GetCantant(String cantant){
		Cantant c = Cantant.find("NOM", cantant).first();
		returnJSON(c);
	}
	public static void GetCansonsByCantant(String cantant){
		Cantant c = Cantant.find("NOM", cantant).first();
		returnJSON(c.cansons);
	}
	public static void GetCantantsByCanso(String canso){
		Canso c = Canso.find("NOM", canso).first();
		returnJSON(c.cantants);
	}

	public static void SuccessCanso(String ca, Integer num) {
		render(ca,num);
	}
	public static void SubmitCanso(String n, String p) {
		render(n,p);
	}

}