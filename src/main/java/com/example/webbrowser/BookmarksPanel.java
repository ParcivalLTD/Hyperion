package com.example.webbrowser;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.json.JSONArray;
import org.json.JSONTokener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookmarksPanel extends JFrame {

    private String[] bookmarks;
    private JList list;
    private JPanel panel;
    boolean alreadyAdded;



    public BookmarksPanel(final CefBrowser browser, CefClient client, JButton addBookmarksButton) {
        setResizable(false);
        setTitle("Bookmarks");
        setSize(600, 300);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel listPanel = new JPanel();
        bookmarks = loadBookmarksFromJson();

        list = new JList<>(bookmarks);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String url = (String) list.getSelectedValue();
                    browser.loadURL(url);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(getWidth()-45, getHeight()-45));
        listPanel.add(scrollPane);

        addBookmarksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = browser.getURL();
                saveUrlToJson(url);

                bookmarks = loadBookmarksFromJson();
                list.setListData(bookmarks);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(okButton);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(closeButton);

        JButton deleteAllButton = new JButton("Delete All");
        buttonPanel.add(deleteAllButton);

        deleteAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookmarks = new String[0];
                list.setListData(bookmarks);
                try (FileWriter writer = new FileWriter("bookmarks.json")) {
                    writer.write(new JSONArray().toString());
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
        list.setPreferredSize(new Dimension(500, getHeight()));

        panel.add(listPanel, BorderLayout.CENTER);
        add(panel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }

    public void saveUrlToJson(String url) {
        try (FileReader file = new FileReader("bookmarks.json")) {
            JSONArray history = new JSONArray(new JSONTokener(file));

            alreadyAdded = false;
            for (int i = 0; i < history.length(); i++) {
                if (history.getString(i).equals(url)) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (!alreadyAdded) {
                history.put(url);
                try (FileWriter writer = new FileWriter("bookmarks.json")) {
                    writer.write(history.toString());
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] loadBookmarksFromJson() {
        String[] bookmarks = {};

        try (FileReader file = new FileReader("bookmarks.json")) {
            JSONArray jsonBookmarks = new JSONArray(new JSONTokener(file));

            bookmarks = new String[jsonBookmarks.length()];
            for (int i = 0; i < jsonBookmarks.length(); i++) {
                bookmarks[i] = jsonBookmarks.getString(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        return bookmarks;
    }
}
