package com.example.webbrowser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageTitle {

    private String url;

    public WebPageTitle(String url) {
        this.url = url;
    }

    public String getTitle() throws Exception {
        Document doc = Jsoup.connect(url).get();
        return doc.title();
    }
}
