package com.example.webbrowser;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;

public class MainFrame extends JFrame {
    String theme;
    private String startPage;
    private TabBar tabBar;

    public MainFrame() throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException, FontFormatException {

        getSavedTheme();

        UIManager.put("TextComponent.arc", 12);
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("Component.arrowType", "chevron");

        startPage = "https://google.com";
        tabBar = new TabBar(startPage);

        getContentPane().add(tabBar.getContentPane());

        setTitle("Hyperion");
        String filePath = "images/icon.png";
        ImageIcon icon = new ImageIcon(filePath);
        setIconImage(icon.getImage());
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });

        tabBar.getBrowserTab().getMenu().getSettingsPanel().settingsListeners(MainFrame.this);
    }

    public void getSavedTheme() {
        try (FileReader file = new FileReader("settings.json")) {
            JSONObject settings = new JSONObject(new JSONTokener(file));
            theme = settings.getString("theme");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (theme.equals("Dark Theme (Mac)")) {
                try {
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (theme.equals("Light Theme (Mac)")) {
                try {
                    UIManager.setLookAndFeel(new FlatMacLightLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (theme.equals("Light Theme")) {
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (theme.equals("Dark Theme")) {
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (theme.equals("Dracula Theme")) {
                try {
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (theme.equals("IntelliJ Theme")) {
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            }
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
}
