package controllers;

import models.Admin;
 public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        return Admin.connect(username, password) !=null;

    }

}
