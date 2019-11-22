package models;

import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;

import play.db.jpa.Model;

import javax.persistence.Entity;

import javax.persistence.ManyToMany;

@Entity
public class Admin extends Model {
    @Required
    public String nom;

    @Required
    public String password;

    public Admin(String n, String p, int e){
        nom =n;
        password = p;
    }

    public static Admin connect(String n, String p){
        Admin c = Admin.find("byNomAndPassword",n,p).first();
        return c;
    }

    public String toString() {
        return nom;
    }
}
