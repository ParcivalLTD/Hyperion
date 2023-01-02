package com.example.webbrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BrowserToolbar extends Toolbar {

    private JTextField address_;
    private JButton backButton;
    private JButton forwardButton;
    private JButton refreshButton;
    private JButton bookmarkButton;
    private JButton menuButton;
    private JButton homeButton;

    public BrowserToolbar() throws IOException, FontFormatException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        address_ = new JTextField();
        backButton = new JButton("Back");
        forwardButton = new JButton("Forward");
        refreshButton = new JButton("Refresh");
        bookmarkButton = new JButton("add Bookmark");
        homeButton = new JButton("Home");
        menuButton = new JButton("Menu");

        setBorder(new EmptyBorder(5, 0, 5, 0));
        Dimension size = address_.getPreferredSize();
        address_.setPreferredSize(new Dimension(size.width, 35));

        add(Box.createHorizontalStrut(7));
        add(backButton);
        add(Box.createHorizontalStrut(5));
        add(forwardButton);
        add(Box.createHorizontalStrut(5));
        add(refreshButton);
        add(Box.createHorizontalStrut(100));
        add(bookmarkButton);
        add(Box.createHorizontalStrut(5));
        add(homeButton);
        add(Box.createHorizontalStrut(5));
        add(address_);
        add(Box.createHorizontalStrut(100));
        add(menuButton);
        add(Box.createHorizontalStrut(7));
    }

    public JTextField getAddressBar() {
        return address_;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getForwardButton() {
        return forwardButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getBookmarkButton() {
        return bookmarkButton;
    }

    public JButton getMenuButton() {
        return menuButton;
    }
}

