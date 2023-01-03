package com.example.webbrowser;

import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowserMenu extends JPopupMenu {
    private static final long serialVersionUID = 1L;

    private JMenuItem settings;
    private JMenuItem history;
    private JMenuItem bookmarks;
    private SettingsPanel settingsPanel;
    private HistoryPanel historyPanel;
    private BookmarksPanel bookmarksPanel;

    public BrowserMenu(CefBrowser browser, CefClient client_, JButton bookmarksButton, String searchEnginePre) {
        settings = new JMenuItem("Settings");
        history = new JMenuItem("History");
        bookmarks = new JMenuItem("Bookmarks");

        setPreferredSize(new Dimension(150, 75));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBorder(new FlatDropShadowBorder());

        add(Box.createHorizontalGlue());
        add(settings);
        add(Box.createHorizontalGlue());
        add(history);
        add(Box.createHorizontalGlue());
        add(bookmarks);
        add(Box.createHorizontalGlue());
        setVisible(false);

        settingsPanel = new SettingsPanel(searchEnginePre);
        historyPanel = new HistoryPanel(browser, client_);
        bookmarksPanel = new BookmarksPanel(browser, client_, bookmarksButton);

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                settingsPanel.setVisible(true);
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                historyPanel.setVisible(true);
            }
        });

        bookmarks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bookmarksPanel.setVisible(true);
            }
        });
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    public BookmarksPanel getBookmarksPanel() {
        return bookmarksPanel;
    }

    public JMenuItem getSettings() {
        return settings;
    }

    public JMenuItem getHistory() {
        return history;
    }

    public JMenuItem getBookmarks() {
        return bookmarks;
    }
}