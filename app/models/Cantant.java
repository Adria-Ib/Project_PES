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

    @ManyToMany (mappedBy="cantants")
    public List<Canso> cansons = new ArrayList<Canso>();

    public Cantant(){
        nom = null;
        pais = null;
    }
    public Cantant(String nom, String pais){
        this.nom = nom;
        this.pais = pais;
    }
    public Cantant(String nom, String pais, Canso canso){
        this.nom = nom;
        this.pais = pais;
        this.cansons.add(canso);
    }
    public String getNom(){
        return this.nom;
    }
    public String getPais(){
        return this.pais;
    }
    public String getCanso(int i) {
        return this.cansons.get(i).getNom();
    }
    public int getNum(){
        return this.cansons.size();
    }

}
