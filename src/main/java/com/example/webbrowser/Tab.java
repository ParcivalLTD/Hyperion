package com.example.webbrowser;

public interface Tab {
    public void loadPage(String url);

    public void goBack();

    public void goForward();

    String getTitle();
}
