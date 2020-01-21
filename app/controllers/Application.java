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


	public static void buscarCanso(String nom)
	{
		Canso song = Canso.find("byNom", nom).first();
		String s = "";
		String lyrics = "";
		int u = 0;
		if (song!=null && (!nom.equals(" ") || !nom.equals("")) ) {
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
		}
		if(nom.equals("")){
			render("Application/fail2.html");
		}
		else{
			renderText("La cançó " +nom+ " no està a la base de dades");
		}
	}

	public static void buscarCantant(String nom)
	{
		Cantant singer = Cantant.find("byNom", nom).first();
		String s = "";
		String pais = "";
		int u = 1;
		if (singer!=null && (!nom.equals(" ") || !nom.equals(""))) {
			while(u < singer.getNum() -1){
				s = s + singer.getCantant(u);
				if(u != singer.getNum() -2){
					s = s + ", ";
				}
				u = u + 1;
			}
			pais = singer.getPais();
			render(nom,pais,s);
		}
		if(nom.equals("")) {
			render("Application/fail2.html");
		}
		else{
			renderText("El/la cantant: "+nom+ " no està a la base de dades");
		}
	}
	public static void buscarCansoAny(String any)
	{
		if(any.equals("")){
			render("Application/fail2.html");
		}
		if(!any.equals("") ) {
			List<Canso> song = Canso.find("byData", Integer.parseInt(any)).fetch();
			int u = song.size();
			boolean first = true;
			int w = 0;
			String s ="";
			for (Canso c : song) {
				if (!first) {
					s += ", ";
				}
				first = false;
				s = s + c.getNom();
			}
			if (u != 0) {
				render(any, s);
			}
			else{
				renderText("No hi ha cançons de l'any "+ any + " en la nostra base de dades");
			}
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
			String nom = user.nom;
			render("Application/home.html",nom);
		}else {
			// Oops
			render("Application/fail.html");
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
	public static void fail2(){
		render();
	}

	public static void getInfoSession(){
		renderText("Està connectat "+ session.get("user"));
	}
	public static void SubmitCantant(String n, String p) {
		render(n,p);
	}
	public static void AddCantant(String nom, String pais) {
		Cantant c = Cantant.find("NOM", nom).first();
		if (c == null) {
			c = new Cantant(nom,pais);
			c.save();
			renderText("OK");
		}
		else{
			renderText("Alredy exists");
		}
	}
	public static void AddCanso(String nom, String data, String lletra, String cantants){
		Canso song = Canso.find("byNom", nom).first();
		if(song == null){
			song = new Canso(nom, Integer.decode(data), lletra);
			String [] cantantsStrings = cantants.split(";");
			Cantant cantant;
			for (String c:cantantsStrings) {
				cantant = Cantant.find("byNom", c).first();
				if(cantant != null){
					song.cantants.add(cantant);
				}
			}
			song.save();
			renderText("OK");
		}
		else{
			renderText("Song with this name already exists");
		}
	}
	public static void AddCansoWEB(String nom, String data, String lletra, String cantants){
		Canso song = Canso.find("byNom", nom).first();
		if(song == null){
			song = new Canso(nom, Integer.decode(data), lletra);
			String [] cantantsStrings = cantants.split(";");
			Cantant cantant;
			for (String c:cantantsStrings) {
				cantant = Cantant.find("byNom", c).first();
				if(cantant != null){
					song.cantants.add(cantant);
				}
			}
			song.save();
			render("@SuccessCanso",nom,data,lletra,cantants);
		}
		else{
			renderText("Una cançó amb aquest nom ja existeix");
		}
	}
	public static void AddCantantWEB(String nom, String pais) {
		Cantant c = Cantant.find("NOM", nom).first();
		if (c == null) {
			c = new Cantant(nom,pais);
			c.save();
			render("@SuccessCantant",nom,pais);
		}
		else{
			renderText("Alredy exists");
		}
	}
	public static void SuccessCantant(String n, String p) {
		AddCantantWEB(n,p);
	}
	public static void GetCantant(String cantant){
		Cantant c = Cantant.find("NOM", cantant).first();
		returnJSON(c);
	}
	public static void GetCanso(String canso){
		Canso c = Canso.find("NOM", canso).first();
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
	public static void DeleteCanso(String nom){
		Canso c = Canso.find("NOM", nom).first();
		if(c!=null){
			c.delete();
			renderText("OK");
		}
		else {
			renderText("Song " + nom + " not found");
		}
	}

}