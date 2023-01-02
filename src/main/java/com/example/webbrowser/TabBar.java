package com.example.webbrowser;

import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabBar {
    private JTabbedPane tabbedPane;
    private JButton addTabButton;
    private List<Container> browserTabs;
    private String startPage;
    private BrowserTab browserTab;

    public TabBar(final String startPage) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException, FontFormatException {
        this.startPage = startPage;
        this.browserTabs = new ArrayList<>();

        tabbedPane = new JTabbedPane();
        addNewTab(startPage);

        addTabButton = new JButton("+");
        addTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addNewTab(startPage);
                } catch (UnsupportedPlatformException | CefInitializationException | IOException |
                         InterruptedException | FontFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public BrowserTab getBrowserTab() {
        return browserTab;
    }

    public JComponent getContentPane() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(addTabButton, BorderLayout.EAST);
        return panel;
    }

    public void addNewTab(String url) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException, FontFormatException {
        browserTab = new BrowserTab(url);
        browserTabs.add(browserTab.getContentPane());

        tabbedPane.addTab("New Tab", browserTab.getContentPane());
    }
}
