package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cantant extends Model {

    private String nom;
    private String pais;

    @ManyToMany
    List<Canso> cansons = new ArrayList<Canso>();

    public Cantant(){
        nom = null;
        pais = null;
    }
    public Cantant(String nom, String pais){
        this.nom = nom;
        this.pais = pais;
    }


}
