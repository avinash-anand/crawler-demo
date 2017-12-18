package demo.crawler.crawl;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Singleton
public class Spider {

    //for uniqueness
    private Set<String> visitedPages = new HashSet<>();
    // for maintaining order
    private Queue<String> linksToVisit = new LinkedList<>();

    private Set<String> images = new HashSet<>();
    private Set<String> importLinks = new HashSet<>();
    private Set<String> jsLinks = new HashSet<>();
    private Set<String> domainLinks = new HashSet<>();
    private Set<String> externalLinks = new HashSet<>();
    // url to start with
    private String domainUrl;

    @Inject
    private SpiderLeg spiderLeg;

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public void crawl() {
        //to trigger start
        System.out.println("Please Wait... It's crawling.\n\n");
        linksToVisit.add(domainUrl);
        //domainUrl is also a domain link!
        domainLinks.add(domainUrl);
        while (!linksToVisit.isEmpty()) {
            String nextUrl = getNextUrl();
            if (nextUrl != null) {
                spiderLeg.crawlLeg(nextUrl, this.domainUrl);
                this.linksToVisit.addAll(spiderLeg.getDomainLinks());
                this.domainLinks.addAll(spiderLeg.getDomainLinks());
                this.externalLinks.addAll(spiderLeg.getExternalLinks());
                this.images.addAll(spiderLeg.getImages());
                this.jsLinks.addAll(spiderLeg.getJsLinks());
                this.importLinks.addAll(spiderLeg.getImportLinks());
                //then clear spider leg for next url
                spiderLeg.clearAllForNext();
            }
        }
    }

    private String getNextUrl() {
        String nextUrl = linksToVisit.poll();
        while (this.visitedPages.contains(nextUrl)) {
            nextUrl = linksToVisit.poll();
        }
        this.visitedPages.add(nextUrl);
        return nextUrl;
    }

    public Set<String> getJsLinks() {
        return jsLinks;
    }

    public Set<String> getImportLinks() {
        return importLinks;
    }

    public Set<String> getImages() {
        return images;
    }

    public Set<String> getExternalLinks() {
        return externalLinks;
    }

    public Set<String> getDomainLinks() {
        return domainLinks;
    }
}
