package com.example.webbrowser;

import javax.swing.*;
import java.awt.*;

public interface Tab {
    public void loadPage(String url);
    public void goBack();
    public void goForward();

    String getTitle();
}
