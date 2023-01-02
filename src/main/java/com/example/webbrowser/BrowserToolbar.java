package com.example.webbrowser;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    private ImageIcon menuIconLight;
    private ImageIcon menuIconDark;
    private ImageIcon backIconDark;
    private ImageIcon backIconLight;
    private ImageIcon forwardIconLight;
    private ImageIcon forwardIconDark;
    private ImageIcon refreshIconLight;
    private ImageIcon refreshIconDark;
    private ImageIcon bookmarkIconLight;
    private ImageIcon bookmarkIconDark;
    private ImageIcon homeIconLight;
    private ImageIcon homeIconDark;

    public BrowserToolbar() throws IOException, FontFormatException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        colorListeners();
        menuButtonIcons();
        backButtonIcons();
        forwardButtonIcons();
        refreshButtonIcons();
        bookmarkButtonIcons();
        homeButtonIcons();

        address_ = new JTextField();
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

    public void colorListeners() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (FlatLaf.isLafDark() == true) {
                    menuButton.setIcon(menuIconDark);
                    backButton.setIcon(backIconDark);
                    forwardButton.setIcon(forwardIconDark);
                    refreshButton.setIcon(refreshIconDark);
                    bookmarkButton.setIcon(bookmarkIconDark);
                    homeButton.setIcon(homeIconDark);
                } else {
                    menuButton.setIcon(menuIconLight);
                    backButton.setIcon(backIconLight);
                    forwardButton.setIcon(forwardIconLight);
                    refreshButton.setIcon(refreshIconLight);
                    bookmarkButton.setIcon(bookmarkIconLight);
                    homeButton.setIcon(homeIconLight);
                }
            }
        };

        new Timer(500, taskPerformer).start();
    }

    public void menuButtonIcons() {
        menuIconLight = new ImageIcon("images/ellipsis-vertical-solid.png");
        Image imgMenuIconLight = menuIconLight.getImage();
        Image newMenuIconLight = imgMenuIconLight.getScaledInstance(3, 13, java.awt.Image.SCALE_SMOOTH);
        menuIconLight = new ImageIcon(newMenuIconLight);
        menuIconDark = new ImageIcon("images/ellipsis-vertical-solid_white.png");
        Image imgMenuIconDark = menuIconDark.getImage();
        Image newMenuIconDark = imgMenuIconDark.getScaledInstance(3, 13, java.awt.Image.SCALE_SMOOTH);
        menuIconDark = new ImageIcon(newMenuIconDark);
        menuButton = new JButton(menuIconLight);
        menuButton.setOpaque(false);
        menuButton.setMargin(new Insets(5, 7, 5, 7));
    }

    public void backButtonIcons() {
        backIconLight = new ImageIcon("images/arrow-left-solid.png");
        Image imgBackIconLight = backIconLight.getImage();
        Image newBackIconLight = imgBackIconLight.getScaledInstance(10, 12, java.awt.Image.SCALE_SMOOTH);
        backIconLight = new ImageIcon(newBackIconLight);
        backIconDark = new ImageIcon("images/arrow-left-solid_white.png");
        Image imgBackIconDark = backIconDark.getImage();
        Image newBackIconDark = imgBackIconDark.getScaledInstance(10, 12, java.awt.Image.SCALE_SMOOTH);
        backIconDark = new ImageIcon(newBackIconDark);
        backButton = new JButton(backIconLight);
        backButton.setOpaque(false);
        backButton.setMargin(new Insets(5,  6, 5, 6));
        backButton.putClientProperty( "JButton.buttonType", "roundRect" );
    }

    public void forwardButtonIcons() {
        forwardIconLight = new ImageIcon("images/arrow-right-solid.png");
        Image imgForwardIconLight = forwardIconLight.getImage();
        Image newForwardIconLight = imgForwardIconLight.getScaledInstance(10, 12, java.awt.Image.SCALE_SMOOTH);
        forwardIconLight = new ImageIcon(newForwardIconLight);
        forwardIconDark = new ImageIcon("images/arrow-right-solid_white.png");
        Image imgForwardIconDark = forwardIconDark.getImage();
        Image newForwardIconDark = imgForwardIconDark.getScaledInstance(10, 12, java.awt.Image.SCALE_SMOOTH);
        forwardIconDark = new ImageIcon(newForwardIconDark);
        forwardButton = new JButton(forwardIconLight);
        forwardButton.setOpaque(false);
        forwardButton.setMargin(new Insets(5, 6, 5, 6));
        forwardButton.putClientProperty( "JButton.buttonType", "roundRect" );
    }

    public void refreshButtonIcons() {
        refreshIconLight = new ImageIcon("images/rotate-right-solid.png");
        Image imgRefreshIconLight = refreshIconLight.getImage();
        Image newRefreshIconLight = imgRefreshIconLight.getScaledInstance(13, 13, java.awt.Image.SCALE_SMOOTH);
        refreshIconLight = new ImageIcon(newRefreshIconLight);
        refreshIconDark = new ImageIcon("images/rotate-right-solid_white.png");
        Image imgRefreshIconDark = refreshIconDark.getImage();
        Image newRefreshIconDark = imgRefreshIconDark.getScaledInstance(13, 13, java.awt.Image.SCALE_SMOOTH);
        refreshIconDark = new ImageIcon(newRefreshIconDark);
        refreshButton = new JButton(refreshIconLight);
        refreshButton.setOpaque(false);
        refreshButton.setMargin(new Insets(5, 5, 5, 5));
    }

    public void bookmarkButtonIcons() {
        bookmarkIconLight = new ImageIcon("images/bookmark-regular.png");
        Image imgBookmarkIconLight = bookmarkIconLight.getImage();
        Image newBookmarkIconLight = imgBookmarkIconLight.getScaledInstance(9, 13, java.awt.Image.SCALE_SMOOTH);
        bookmarkIconLight = new ImageIcon(newBookmarkIconLight);
        bookmarkIconDark = new ImageIcon("images/bookmark-regular_white.png");
        Image imgBookmarkIconDark = bookmarkIconDark.getImage();
        Image newBookmarkIconDark = imgBookmarkIconDark.getScaledInstance(9, 13, java.awt.Image.SCALE_SMOOTH);
        bookmarkIconDark = new ImageIcon(newBookmarkIconDark);
        bookmarkButton = new JButton(bookmarkIconLight);
        bookmarkButton.setOpaque(false);
        bookmarkButton.setMargin(new Insets(5, 7, 5, 7));
        bookmarkButton.setBackground(new Color(0, 0, 0, 0));
    }

    public void homeButtonIcons() {
        homeIconLight = new ImageIcon("images/house-solid.png");
        Image imgHomeIconLight = homeIconLight.getImage();
        Image newHomeIconLight = imgHomeIconLight.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        homeIconLight = new ImageIcon(newHomeIconLight);
        homeIconDark = new ImageIcon("images/house-solid_white.png");
        Image imgHomeIconDark = homeIconDark.getImage();
        Image newHomeIconDark = imgHomeIconDark.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        homeIconDark = new ImageIcon(newHomeIconDark);
        homeButton = new JButton(homeIconLight);
        homeButton.setOpaque(false);
        homeButton.setMargin(new Insets(4, 4, 4, 4));
        homeButton.setBackground(new Color(0, 0, 0, 0));
    }



    public JTextField getAddressBar() {
        return address_;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getHomeButton() {
        return homeButton;
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

