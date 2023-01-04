package com.example.webbrowser;

import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

public class BrowserTab extends JFrame implements Tab {
    private Browser browser;
    private boolean useOsr;
    private boolean useTransparency;
    private JPanel contentPanel;
    private BrowserToolbar toolbar;
    private CefClient client_;
    private JTextField address_;
    private CefBrowser browser_;
    private JButton backButton;
    private JButton forwardButton;
    private JButton refreshButton;
    private JButton bookmarkButton;
    private JButton menuButton;
    private JButton homeButton;
    private String searchEnginePre = "www.google.com/search?q=";
    private BrowserMenu menu;
    private boolean browserFocus_ = true;
    public BrowserTab(String url) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException, FontFormatException {
        //url = homePageListener(url);

        useOsr = false;
        useTransparency = false;

        toolbar = new BrowserToolbar();
        browser = new Browser(url, useOsr, useTransparency);

        add(toolbar, BorderLayout.NORTH);

        add(browser.getBrowserUI_(), BorderLayout.CENTER);

        addressListeners();
        backButtonListeners();
        forwardButtonListeners();
        refreshButtonListeners();
        menuPopup(url);
        bookmarkButtonListeners();
        homeButtonListeners(url);

    }
    /**public String homePageListener(final String url) {
        final String[] url2 = new String[1];
        url2[0] = "";

        final JComboBox homepageComboBox = menu.getSettingsPanel().homepageComboBox;
        if (homepageComboBox != null) {
            homepageComboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    url2[0] = (String) homepageComboBox.getSelectedItem();
                }
            });
        }


        if (url2[0] != "") {
            return url2[0];
        } else if (url != null) {
            return url;
        } else {
            return "";
        }
    }**/

    public BrowserMenu getMenu() {
        return menu;
    }

    private void refreshButtonListeners() {
        refreshButton = toolbar.getRefreshButton();

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.reload();
            }
        });

        refreshButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                refreshButton.requestFocus();
            }
        });
    }

    private void homeButtonListeners(final String startPageurl) {
        homeButton = toolbar.getHomeButton();

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.getBrowser_().loadURL(startPageurl);
            }
        });

        homeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                homeButton.requestFocus();
            }
        });
    }

    private void bookmarkButtonListeners() {
        bookmarkButton = toolbar.getBookmarkButton();

        bookmarkButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                bookmarkButton.requestFocus();
            }
        });
    }

    private void menuPopup(String startPage) {
        menuButton = toolbar.getMenuButton();
        menu = new BrowserMenu(browser.getBrowser_(), browser.getClient_(), toolbar.getBookmarkButton(), searchEnginePre);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(!menu.isVisible());

                if (menu.isVisible()) {
                    menu.show(menuButton, -menu.getPreferredSize().width + menuButton.getWidth() + 10, menuButton.getHeight() + 10);
                }
            }
        });


        menuButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                menuButton.requestFocus();
            }
        });
    }

    private void backButtonListeners() {
        backButton = toolbar.getBackButton();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    browser_.goBack();
            }
        });

        backButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                backButton.requestFocus();
            }
        });
    }

    private void forwardButtonListeners() {
        forwardButton = toolbar.getForwardButton();

        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    browser_.goForward();
            }
        });

        forwardButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                forwardButton.requestFocus();
            }
        });
    }

    private void addressListeners() {
        client_ = browser.getClient_();
        browser_ = browser.getBrowser_();
        address_ = toolbar.getAddressBar();

        address_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = address_.getText();
                if (!url.contains(".")) {
                    url = searchEnginePre + url;
                }
                browser_.loadURL(url);

                if (browser_.canGoBack()) {
                    backButton.setEnabled(true);
                } else {
                    backButton.setEnabled(false);
                }

                if (browser_.canGoForward()) {
                    forwardButton.setEnabled(true);
                } else {
                    forwardButton.setEnabled(false);
                }
            }
        });
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                String strippedUrl = url.substring(url.indexOf("://") + 3);
                address_.setText(strippedUrl);
            }
        });
        address_.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                address_.requestFocus();
            }
        });
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus_) return;
                browserFocus_ = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus_ = false;
                address_.selectAll();
            }
        });

        address_.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                address_.selectAll();
            }
        });
    }


    @Override
    public void loadPage(String url) {
        browser.getBrowser_().loadURL(url);
    }

    @Override
    public void goBack() {
        browser.getBrowser_().goBack();
    }

    @Override
    public void goForward() {
        browser.getBrowser_().goForward();
    }

    @Override
    public String getTitle() {
        String url = browser.getBrowser_().getURL();
        return url;
    }

    public Component getBrowserUI() {
        return browser.getBrowserUI_();
    }
}