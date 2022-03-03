/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

/**
 *
 * @author Karlo
 */
public class User {
    private int id;
    private String user;
    private String pass;

    
    public User() {
    }
    
    public User(int id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;

    }
 
}
