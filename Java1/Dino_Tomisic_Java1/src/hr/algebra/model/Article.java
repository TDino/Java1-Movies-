/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;


/**
 *
 * @author dnlbe
 */
public class Article {
       

    private int id;
    private String title;
    private String actor;
    private String director;
    private String picturePath;
    private String genre;

    public Article() {
    }
    
    public Article(String title, String actor, String director, String picturePath, String genre) {
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.picturePath = picturePath;
        this.genre = genre;
    }
    
    public Article(int id, String title, String actor, String director, String picturePath, String genre) {
        this(title, actor, director, picturePath, genre);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    //ovo
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "ID-"+id + " Name of a movie: " + title;
    }
    
}
