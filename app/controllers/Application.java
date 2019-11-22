package controllers;

import models.Admin;
import models.Canso;
import play.*;
import play.mvc.*;
import play.data.validation.*;

public class Application extends Controller  {
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
		if (song!=null) {
			renderText("la canción " + nom + "  tiene la siguiente letra " + song.getLletra());
		}
		else{
			renderText("no hay ninguna canción con ese nombre");
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
	public static void SuccessCantant(String n, String p) {
		Canso c = new Canso("qwkdm", 2019, "qenceiuqlnicjnq");
		c.AddCantantNomPais(n, p);
		c.save();
		render(n,p);
	}
	public static void SuccessCanso(String ca, Integer num) {
		render(ca,num);
	}
	public static void SubmitCanso(String n, String p) {
		render(n,p);
	}

}