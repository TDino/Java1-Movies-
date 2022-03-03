/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Article;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author dnlbe
 */
public class SqlRepository implements Repository {

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ADMIN = "Admin";
    private static final String LOGIN = "{ CALL loginUser (?,?) }"; 
    private static final String REGISTER = "{ CALL registerUser (?,?) }"; 
    private static final String ID_ARTICLE = "IDArticle";
    private static final String TITLE = "Title";
    private static final String ACTOR = "Actor";
    private static final String DIRECTOR = "Director";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String GENRE = "Genre";
    
    private static final String CREATE_ARTICLE = "{ CALL createArticle (?,?,?,?,?,?) }";
    private static final String UPDATE_ARTICLE = "{ CALL updateArticle (?,?,?,?,?,?) }";
    private static final String DELETE_ARTICLE = "{ CALL deleteArticle (?) }";
    private static final String SELECT_ARTICLE = "{ CALL selectArticle (?) }";
    private static final String SELECT_ARTICLES = "{ CALL selectArticles }";
    
    
    @Override
    public Optional<User> Login(String user, String pass) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(LOGIN)) {
           
            stmt.setString(1, user);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
          
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD)));
                }
            }
        }
        return Optional.empty();
    }
    @Override
    public void register(String u, String p) throws Exception {
    DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(REGISTER)) {
            stmt.setString(1, u);
            stmt.setString(2, p);
 
            stmt.executeUpdate();
        }     
    }

    @Override
    public int createArticle(Article article) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {
            
            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getActor());
            stmt.setString(3, article.getDirector());
            stmt.setString(4, article.getPicturePath());
            stmt.setString(5, article.getGenre());
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(6);
        }
    }

    @Override
    public void createArticles(List<Article> articles) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {

            for (Article article : articles) {
                stmt.setString(1, article.getTitle());
                stmt.setString(2, article.getActor());
                stmt.setString(3, article.getDirector());
                stmt.setString(4, article.getPicturePath());
                stmt.setString(5, article.getGenre());
                stmt.registerOutParameter(6, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        } 
    }

    @Override
    public void updateArticle(int id, Article data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ARTICLE)) {
            
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getActor());
            stmt.setString(3, data.getDirector());
            stmt.setString(4, data.getPicturePath());
            stmt.setString(5, data.getGenre());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ARTICLE)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        } 
    }

    @Override
    public Optional<Article> selectArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ARTICLE)) {
           
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return Optional.of(new Article(
                            rs.getInt(ID_ARTICLE),
                            rs.getString(TITLE),
                            rs.getString(ACTOR),
                            rs.getString(DIRECTOR),
                            rs.getString(PICTURE_PATH),
                            rs.getString(GENRE)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Article> selectArticles() throws Exception {
        List<Article> articles = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ARTICLES);
                ResultSet rs = stmt.executeQuery()) {
          
            while (rs.next()) {
                articles.add(new Article(
                                rs.getInt(ID_ARTICLE),
                                rs.getString(TITLE),
                                rs.getString(ACTOR),
                                rs.getString(DIRECTOR),
                                rs.getString(PICTURE_PATH),
                                rs.getString(GENRE)));
            }
        } 
        return articles;
    }

   
}
