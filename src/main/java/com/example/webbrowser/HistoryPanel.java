package com.example.webbrowser;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandler;
import org.cef.network.CefRequest;
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

public class HistoryPanel extends JFrame {

    private String[] history;
    private JList list;
    private JPanel panel;

    public HistoryPanel(final CefBrowser browser, CefClient client) {
        setResizable(false);
        setTitle("History");
        setSize(600, 300);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel listPanel = new JPanel();
        history = loadHistoryFromJson();

        list = new JList<>(history);
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
        listPanel.add(scrollPane);

        client.addLoadHandler(new CefLoadHandler() {
            @Override
            public void onLoadingStateChange(CefBrowser cefBrowser, boolean b, boolean b1, boolean b2) {

            }

            @Override
            public void onLoadStart(CefBrowser cefBrowser, CefFrame cefFrame, CefRequest.TransitionType transitionType) {

            }

            @Override
            public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
                String url = browser.getURL();
                saveUrlToJson(url);

                history = loadHistoryFromJson();
                list.setListData(history);
            }

            @Override
            public void onLoadError(CefBrowser cefBrowser, CefFrame cefFrame, ErrorCode errorCode, String s, String s1) {

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
                history = new String[0];
                list.setListData(history);
                try (FileWriter writer = new FileWriter("history.json")) {
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
        try (FileReader file = new FileReader("history.json")) {
            JSONArray history = new JSONArray(new JSONTokener(file));
            if (history.length() == 0 || !history.getString(history.length() - 1).equals(url)) {
                history.put(url);

                try (FileWriter writer = new FileWriter("history.json")) {
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
    public String[] loadHistoryFromJson() {
        String[] history = {};

        try (FileReader file = new FileReader("history.json")) {
            JSONArray jsonHistory = new JSONArray(new JSONTokener(file));

            history = new String[jsonHistory.length()];
            for (int i = 0; i < jsonHistory.length(); i++) {
                history[i] = jsonHistory.getString(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        return history;
    }
}
