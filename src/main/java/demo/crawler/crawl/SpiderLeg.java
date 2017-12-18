package demo.crawler.crawl;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class SpiderLeg {

    private Set<String> images = new HashSet<>();
    private Set<String> importLinks = new HashSet<>();
    private Set<String> jsLinks = new HashSet<>();
    private Set<String> domainLinks = new HashSet<>();
    private Set<String> externalLinks = new HashSet<>();
    @Inject
    private ReadUrl readUrl;
    private static final String MEDIA = "[src]";
    private static final String IMPORTS = "link[href]";
    private static final String LINKS = "a[href]";


    public void crawlLeg(String url, String domainUrl) {
        Document document = readUrl.getDocument(url);
        Elements media = document.select(MEDIA);
        extractImages(media);
        extractJs(media);
        Elements imports = document.select(IMPORTS);
        importLinks.addAll(imports.stream().map(a -> a.attr("abs:href")).collect(Collectors.toList()));
        Elements links = document.select(LINKS);
        List<String> allLinks = links.stream().map(a -> a.attr("abs:href")).collect(Collectors.toList());
        List<String> domainUrls = allLinks.stream().filter(a -> a.contains(domainUrl)).collect(Collectors.toList());
        this.domainLinks.addAll(domainUrls);
        allLinks.removeAll(domainUrls);
        this.externalLinks.addAll(allLinks);
    }

    private void extractImages(Elements media) {
        images.addAll(media.stream()
                .filter(a -> "img".equals(a.tagName()))
                .map(a -> a.attr("abs:src"))
                .collect(Collectors.toList()));
    }

    private void extractJs(Elements media) {
        jsLinks.addAll(media.stream()
                .filter(a -> "script".equals(a.tagName()))
                .map(a -> a.attr("abs:src"))
                .collect(Collectors.toList()));
    }

    public Set<String> getDomainLinks() {
        return domainLinks;
    }

    public Set<String> getExternalLinks() {
        return externalLinks;
    }

    public Set<String> getImages() {
        return images;
    }

    public Set<String> getImportLinks() {
        return importLinks;
    }

    public Set<String> getJsLinks() {
        return jsLinks;
    }

    public void clearAllForNext() {
        this.images.clear();
        this.domainLinks.clear();
        this.importLinks.clear();
        this.jsLinks.clear();
        this.externalLinks.clear();
    }
}
