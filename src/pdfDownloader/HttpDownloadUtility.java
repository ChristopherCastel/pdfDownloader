package pdfDownloader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HttpDownloadUtility {

    private static final String DEFAULT_PATH = "pdfDownloads/";
    private String url;
    private String basePath;

    public HttpDownloadUtility(String url) {
        this(url, DEFAULT_PATH);
    }

    public HttpDownloadUtility(String url, String basePath) {
        this.url = url;
        this.basePath = basePath;
    }

    public void download(String siteUrl, String href) {
        System.out.println(siteUrl);
        try (InputStream in = new URL(siteUrl).openStream()) {
            String path = getPath(siteUrl, href);
            createFileAndParents(path);
            Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public boolean isDownloadable(String href) {
        String ext = href.substring(href.lastIndexOf('.') + 1);
        return ext.equalsIgnoreCase("pdf");
    }

    private String getPath(String siteUrl, String href) {
        String path = "";
        try {
            siteUrl = siteUrl.replaceFirst(href, "");
            siteUrl = siteUrl.replaceAll(url, "");
            siteUrl = URLDecoder.decode(siteUrl, "UTF-8");
            siteUrl.replaceAll("[:*\"?|<>']", "");
            String filename = URLDecoder.decode(href, "UTF-8");
            filename = filename.replaceAll("[:\\\\/*\"?|<>']", "");
            path = siteUrl + filename;
            path = path.replaceAll(" +", " ").trim();
            path = path.replace(" /", "");
            return basePath + path;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return path;
    }

    private void createFileAndParents(String path) {
        File targetFile = new File(path);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parent);
        }
    }
}
