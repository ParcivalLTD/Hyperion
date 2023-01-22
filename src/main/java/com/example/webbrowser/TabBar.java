package com.example.webbrowser;

import com.formdev.flatlaf.FlatLaf;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefLoadHandler;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.network.CefRequest;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabBar {
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    private JTabbedPane tabbedPane;
    private JButton addTabButton;
    private JButton closeTabButton;
    private BrowserTab newTab;
    private JPanel mainPanel;
    private ImageIcon closeIconLight;
    private ImageIcon closeIconDark;
    private ImageIcon plusIconLight;
    private ImageIcon plusIconDark;

    public TabBar(boolean useOSR, final String startUrl) throws Exception {
        CefAppSingleton.getInstance(useOSR);
        tabbedPane = new JTabbedPane();
        addTab(startUrl);
        colorListeners();
        plusButtonIcons();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        addTabButton.setPreferredSize(new Dimension(30, 30));
        addTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addTab(startUrl);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        mainPanel.add(addTabButton);
        addTabButton.setBounds(mainPanel.getWidth()-addTabButton.getWidth()-10,10,addTabButton.getWidth(),addTabButton.getHeight());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(addTabButton, BorderLayout.EAST);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                String url = tabbedPane.getTitleAt(selectedIndex);
                BrowserTab currentTab = (BrowserTab) tabbedPane.getComponentAt(selectedIndex);
                currentTab.getBrowser().getBrowser_().loadURL(url);
            }
        });
    }



    public void addTab(String startUrl) throws Exception {
        final WebPageTitle[] webPageTitle = {new WebPageTitle(startUrl)};
        final String[] title = {webPageTitle[0].getTitle()};
        final WebPageIcon webPageIcon = new WebPageIcon(startUrl);
        final ImageIcon[] icon = {webPageIcon.getIcon()};
        newTab = null;
        try {
            newTab = new BrowserTab(startUrl);
        } catch (UnsupportedPlatformException ex) {
            throw new RuntimeException(ex);
        } catch (CefInitializationException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        tabbedPane.addTab(title[0], newTab.getContentPane());

        final JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tabPanel.setOpaque(false);

        final JLabel tabTitle = new JLabel(tabbedPane.getTitleAt(tabbedPane.getTabCount()-1));

        if (tabTitle.getText().length() > 22) {
            tabTitle.setText(tabTitle.getText().substring(0, 22) + "...");
        }
        tabTitle.setPreferredSize(new Dimension(150, 30));
        tabTitle.setFont(new Font(tabTitle.getFont().getName(), tabTitle.getFont().getStyle(), 11));
        tabTitle.setVerticalAlignment(JLabel.BOTTOM);
        tabTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 9, 0));
        tabPanel.add(tabTitle);

        if(icon[0] != null) {
            JLabel iconLabel = new JLabel(icon[0]);
            tabPanel.add(iconLabel);
        }

        closeButtonIcons();

        closeTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                tabbedPane.removeTabAt(selectedIndex);
                //BrowserTab currentTab = (BrowserTab) tabbedPane.getComponentAt(selectedIndex);
                //currentTab.getBrowser().getClient_().doClose(currentTab.getBrowser().getBrowser_());
            }
        });
        tabPanel.add(closeTabButton);

        tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, tabPanel);


        newTab.getBrowser().getClient_().addLoadHandler(new CefLoadHandler() {
            @Override
            public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
                if(!isLoading){
                    webPageTitle[0] = new WebPageTitle(browser.getURL());
                    try {
                        title[0] = webPageTitle[0].getTitle();
                        webPageIcon.url = browser.getURL();
                        icon[0] = webPageIcon.getIcon();
                        if(icon[0] != null) {
                            tabPanel.remove(1);
                            JLabel iconLabel = new JLabel(icon[0]);
                            tabPanel.add(iconLabel,1);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    tabTitle.setText(title[0]);
                    tabTitle.repaint();
                }
            }

            @Override
            public void onLoadStart(CefBrowser browser, CefFrame frame, CefRequest.TransitionType transitionType) {

            }

            @Override
            public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {

            }

            @Override
            public void onLoadError(CefBrowser browser, CefFrame frame, ErrorCode errorCode, String errorText, String failedUrl) {

            }
        });

    }
    public BrowserTab getBrowserTab() {
        return null;
    }

    public void colorListeners() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (FlatLaf.isLafDark() == true) {
                    closeTabButton.setIcon(closeIconDark);
                    addTabButton.setIcon(plusIconDark);
                } else {
                    closeTabButton.setIcon(closeIconLight);
                    addTabButton.setIcon(plusIconLight);
                }
            }
        };

        new Timer(500, taskPerformer).start();
    }

    public void closeButtonIcons() {
        closeIconLight = new ImageIcon("images/xmark-solid.png");
        Image imgBackIconLight = closeIconLight.getImage();
        Image newBackIconLight = imgBackIconLight.getScaledInstance(7, 7, java.awt.Image.SCALE_SMOOTH);
        closeIconLight = new ImageIcon(newBackIconLight);
        closeIconDark = new ImageIcon("images/xmark-solid_white.png");
        Image imgBackIconDark = closeIconDark.getImage();
        Image newBackIconDark = imgBackIconDark.getScaledInstance(7, 7, java.awt.Image.SCALE_SMOOTH);
        closeIconDark = new ImageIcon(newBackIconDark);
        closeTabButton = new JButton(closeIconLight);
        closeTabButton.setOpaque(false);
        closeTabButton.setMargin(new Insets(0,  0, 0, 0));
        closeTabButton.setBorderPainted(false);
        closeTabButton.setContentAreaFilled(false);
        closeTabButton.setFocusPainted(false);
        closeTabButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void plusButtonIcons() {
        plusIconLight = new ImageIcon("images/plus-solid.png");
        Image imgBackIconLight = plusIconLight.getImage();
        Image newBackIconLight = imgBackIconLight.getScaledInstance(7, 7, java.awt.Image.SCALE_SMOOTH);
        plusIconLight = new ImageIcon(newBackIconLight);
        plusIconDark = new ImageIcon("images/plus-solid_white.png");
        Image imgBackIconDark = plusIconDark.getImage();
        Image newBackIconDark = imgBackIconDark.getScaledInstance(7, 7, java.awt.Image.SCALE_SMOOTH);
        plusIconDark = new ImageIcon(newBackIconDark);
        addTabButton = new JButton(plusIconLight);
        addTabButton.setOpaque(false);
        addTabButton.setMargin(new Insets(0,  0, 0, 0));
        addTabButton.setBorderPainted(false);
        addTabButton.setContentAreaFilled(false);
        addTabButton.setFocusPainted(false);
        addTabButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

