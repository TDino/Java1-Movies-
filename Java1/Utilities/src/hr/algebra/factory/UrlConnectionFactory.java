/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author dnlbe
 */
public class UrlConnectionFactory {
    
    public static HttpsURLConnection getHttpsUrlConnection(String path, int timeout, String requestMethod) throws MalformedURLException, IOException {
        URL url = new URL(path);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setConnectTimeout(timeout);
        con.setReadTimeout(timeout);
        con.setRequestMethod(requestMethod);
        con.addRequestProperty("User-Agent", "Mozilla/5.0");
        return con;
    }
}
