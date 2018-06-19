package pdfDownloader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    private static Set<String> visited = new HashSet<>();

    public static void main(String[] args) throws IOException {
        String url = "";
        String path = "";
        HttpDownloadUtility downloader;
        switch (args.length) {
            case 1:
                url = args[0];
                downloader = new HttpDownloadUtility(url);
                break;
            case 2:
                url = args[0];
                path = args[1];
                downloader = new HttpDownloadUtility(url, path);
                break;
            default:
                throw new IllegalArgumentException("Usage: java -jar [url] [path]");
        }
        dfsTraversal(url, downloader);
    }

    private static void dfsTraversal(String url, HttpDownloadUtility downloader) throws IOException {
        if (!url.endsWith("/")) {
            url += "/";
        }
        if (visited.contains(url)) {
            return;
        }
        visited.add(url);
        try {
            Document document = Jsoup.connect(url).get();
            Element body = document.selectFirst("body");
            Elements links = body.select("a[href]");
            for (Element link : links) {
                String absHref = link.attr("abs:href");
                String href = link.attr("href");
                if (downloader.isDownloadable(href)) {
                    downloader.download(absHref, href);
                } else if (absHref.contains("repository.root-me.org") && !href.contains("?")) {
                    dfsTraversal(absHref, downloader);
                }
            }
        } catch (Exception ignore) {
        }
    }
}
