package com.example.webbrowser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.net.URL;

public class WebPageIcon {
    String url;

    public WebPageIcon(String url) {
        if (!url.startsWith("http")) {
            this.url = "http://" + url;
        } else {
            this.url = url;
        }
    }

    public ImageIcon getIcon() throws Exception {
        Document doc = Jsoup.connect(url).get();
        String iconUrl = "";
        // try to find icon using link tag with rel attribute
        Elements links = doc.select("link[rel~=icon]");
        if (!links.isEmpty()) {
            iconUrl = links.attr("href");
        }
        // try to find icon using link tag with href attribute that ends with .ico, .png, .jpeg, .jpg or .svg
        if (iconUrl.isEmpty()) {
            links = doc.select("link[href~=.*\\.(ico|png|jpeg|jpg|svg)]");
            iconUrl = links.attr("href");
        }
        // try to find icon using meta tag with itemprop attribute
        if (iconUrl.isEmpty()) {
            links = doc.select("meta[itemprop=image]");
            iconUrl = links.attr("content");
        }
        // if icon is not found, return null
        if (iconUrl.isEmpty())
        {
            return null;
        } else {
// check if the icon url is relative or not
            URL iconUrlObject;
            if (!iconUrl.startsWith("http")) {
// if it's relative, add the protocol and domain to the url
                URL urlObject = new URL(url);
                iconUrl = "http://" + urlObject.getHost() + iconUrl;
                iconUrlObject = new URL(iconUrl);
            } else {
                iconUrlObject = new URL(iconUrl);
            }
            return new ImageIcon(iconUrlObject);
        }
    }


}


