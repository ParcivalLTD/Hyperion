package com.example.webbrowser;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsPanel extends JFrame {
    public JComboBox<String> homepageComboBox;
    private JComboBox<String> searchComboBox;
    private JComboBox<String> themeComboBox;
    private JTextField homepageField;

    private JPanel panel;

    public SettingsPanel(String SearchEnginePre) {
        setResizable(false);
        setTitle("Settings");
        setSize(720, 300);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        row1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel homepageLabel = new JLabel("Homepage: ");
        homepageComboBox = new JComboBox<>(new String[]{"Google", "Netvibes", "Protopage", "My Yahoo", "My MSN", "Start.me", "Custom Url"});
        row1.add(homepageLabel);
        row1.add(homepageComboBox);

        homepageComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && "Custom".equals(homepageComboBox.getSelectedItem())) {
                    String customHomepage = JOptionPane.showInputDialog("Enter custom homepage URL: ");
                    homepageField = new JTextField(20);
                    homepageField.setText(customHomepage);
                }
            }
        });



        formPanel.add(row1);

        JPanel row2 = new JPanel();
        row2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel searchLabel = new JLabel("Search Engine: ");
        searchComboBox = new JComboBox<>(new String[]{"Google", "Bing", "DuckDuckGo", "Yahoo", "Ask", "AOL", "Baidu", "Yandex", "Ecosia", "StartPage", "Gigablast"});
        searchEngines(SearchEnginePre);
        row2.add(searchLabel);
        row2.add(searchComboBox);

        formPanel.add(row2);

        JPanel row3 = new JPanel();
        row3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel themeLabel = new JLabel("Theme: ");
        themeComboBox = new JComboBox<>(new String[]{"Light Theme", "Dark Theme", "Light Theme (Mac)", "Dark Theme (Mac)", "Dracula Theme", "IntelliJ Theme"});
        row3.add(themeLabel);
        row3.add(themeComboBox);

        String theme = "";
        try (FileReader file = new FileReader("settings.json")) {
            JSONObject settings = new JSONObject(new JSONTokener(file));
            theme = settings.getString("theme");
        } catch (IOException e) {
            e.printStackTrace();
        }
        themeComboBox.setSelectedItem(theme);

        themeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Restart Now", "Restart Later"};
                int choice = JOptionPane.showOptionDialog(SettingsPanel.this,
                        "Restart the browser to apply the theme to all elements.",
                        "Restart Required",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        String java = System.getProperty("java.home") + "/bin/java";
                        String classpath = System.getProperty("java.class.path");
                        String className = Main.class.getCanonicalName();

                        ArrayList<String> command = new ArrayList<String>();
                        command.add(java);
                        command.add("-cp");
                        command.add(classpath);
                        command.add(className);

                        ProcessBuilder builder = new ProcessBuilder(command);
                        builder.start();
                        System.exit(0);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        formPanel.add(row3);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsPanel.this.setVisible(false);
            }
        });
        buttonPanel.add(okButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);
        settingsListeners(SettingsPanel.this);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }

    public void searchEngines(String SearchEnginePre) {
        String searchEngine = (String) searchComboBox.getSelectedItem();

        switch (searchEngine) {
            case "Google":
                SearchEnginePre = "https://google.com/search?q=";
                break;
            case "Bing":
                SearchEnginePre = "https://www.bing.com/search?q=";
                break;
            case "DuckDuckGo":
                SearchEnginePre = "https://duckduckgo.com/?q=";
                break;
            case "Yahoo":
                SearchEnginePre = "https://search.yahoo.com/search?p=";
                break;
            case "Ask":
                SearchEnginePre = "https://www.ask.com/web?q=";
                break;
            case "AOL":
                SearchEnginePre = "https://search.aol.com/aol/search?q=";
                break;
            case "Baidu":
                SearchEnginePre = "https://www.baidu.com/s?wd=";
                break;
            case "Yandex":
                SearchEnginePre = "https://www.yandex.com/search/?text=";
                break;
            case "Ecosia":
                SearchEnginePre = "https://www.ecosia.org/search?q=";
                break;
            case "StartPage":
                SearchEnginePre = "https://www.startpage.com/do/search?q=";
                break;
            case "Gigablast":
                SearchEnginePre = "https://www.gigablast.com/search?q=";
                break;
            default:
                // set SearchEnginePre to a default value or throw an exception
                break;
        }

    }

    public void saveTheme(String theme) {
        JSONObject settings = new JSONObject();
        settings.put("theme", theme);

        try (FileWriter file = new FileWriter("settings.json")) {
            file.write(settings.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settingsListeners(final JFrame parentFrame) {
        themeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String theme = (String) themeComboBox.getSelectedItem();
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
                }
                SwingUtilities.updateComponentTreeUI(parentFrame);
                saveTheme(theme);
            }
        });
    }

    public JTextField getHomepageField() {
        return homepageField;
    }

    public JComboBox<String> getSearchComboBox() {
        return searchComboBox;
    }

    public JComboBox<String> getThemeComboBox() {
        return themeComboBox;
    }
}

