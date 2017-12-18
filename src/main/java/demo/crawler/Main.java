package demo.crawler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import demo.crawler.crawl.Spider;

public class Main {

    //    private static final String DOMAIN_URL = "http://wiprodigital.com/";
    //selected this because this is a small website
    private static final String DOMAIN_URL = "https://www.keanrichmond.com/";

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Spider spider = injector.getInstance(Spider.class);
        spider.setDomainUrl(DOMAIN_URL);
        spider.crawl();
        System.out.println("\n ######### Domain Links ###### \n" + spider.getDomainLinks());
        System.out.println("\n\n\n ######### External Links ###### \n" + spider.getExternalLinks());
        System.out.println("\n\n\n ######### Images Links ###### \n" + spider.getImages());
        System.out.println("\n\n\n ######### Imports - CSS, Icons etc Links ###### \n" + spider.getImportLinks());
        System.out.println("\n\n\n ######### JS Links ###### \n" + spider.getJsLinks());
    }

}
