package demo.crawler.crawl;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ReadUrl {

    public Document getDocument(String url) {
        try {
             return Jsoup.connect(url).userAgent(HttpConnection.DEFAULT_UA).get();
        } catch (IOException ioe) {
            System.err.println("Faced IOException - " + ioe.getMessage());
            return Document.createShell(url);
        }
    }

}
