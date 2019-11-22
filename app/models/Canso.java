package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Canso extends Model {
    private String nom;
    private int data; //Any de creacio
    private String lletra;

    @ManyToMany (mappedBy="cansons")
    List<Cantant> cantants = new ArrayList<Cantant>();


    public Canso(){
        nom = null;
        data = 0;
        lletra = null;
    }

    public Canso(String nom, int data, String lletra){
        this.nom = nom;
        this.data = data;
        this.lletra = lletra;
    }

    public String getNom() {
        return nom;
    }

    public int getData() {
        return data;
    }

    public String getLletra() {
        return lletra;
    }

    public void setData(int data) {
        this.data = data;
        this.refresh();
    }

    public void setLletra(String lletra) {
        this.lletra = lletra;
        this.refresh();
    }

    public void setNom(String nom) {
        this.nom = nom;
        this.refresh();
    }

    public void AddCantantNomPais(String nom, String pais){
        Cantant c = new Cantant(nom, pais);
        c.save();
        cantants.add(c);
        //this.save();
    }
    /*public void AddCantant(String nom){
        Cantant c = Cantant.find()
    }*/
}
