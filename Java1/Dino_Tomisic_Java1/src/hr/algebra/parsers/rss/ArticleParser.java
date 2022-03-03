/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.model.Article;
import hr.algebra.utils.FileUtils;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author dnlbe
 */
public class ArticleParser {

    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final Random RANDOM = new Random();

    public static List<Article> parse() throws IOException, XMLStreamException {
        System.out.println("USO SAM");
        List<Article> articles = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpsUrlConnection(RSS_URL, TIMEOUT, REQUEST_METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());
        Optional<TagType> tagType = Optional.empty();
        Article article = null;
        StartElement startElement = null;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    tagType = TagType.from(qName);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (tagType.isPresent()) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();                        
                        switch (tagType.get()) {
                            case ITEM:
                                article = new Article();
                                articles.add(article);
                                break;
                            case TITLE:
                                if (article != null && !data.isEmpty()) {
                                    article.setTitle(data);
                                }
                                break;   
                            case ACTOR:
                                if (article != null && !data.isEmpty()) {
                                    article.setActor(data);
                                }
                                break;
                            case REDATELJ:
                                if (article != null && !data.isEmpty()) {
                                    article.setDirector(data);
                                }
                                break;
                            case POSTER:
                                if (article != null && startElement != null) {                                   
                                        article.setPicturePath(handlePicture(data));
                                }
                                break;
                            case ZANR:
                                if (article != null && !data.isEmpty()) {
                                    article.setGenre(data);
                                }
                                break;
                                
                        }
                    }
                    break;
            }
        }      
        return articles;
    }

    private static String handlePicture(String url) throws IOException {
        
        url = url.replaceAll("http", "https");
        String ext = url.substring(url.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = Math.abs(RANDOM.nextInt()) + ext;
        String localPicturePath = DIR + File.separator + pictureName;

        FileUtils.copyFromUrl(url, localPicturePath);
        
        return localPicturePath;
    }

    private enum TagType {

        ITEM("item"), 
        TITLE("title"), 
        REDATELJ("redatelj"), 
        ACTOR("glumci"),    
        POSTER("plakat"),
        ZANR("zanr");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

}
