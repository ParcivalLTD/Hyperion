package com.example.webbrowser;

import org.cef.browser.CefBrowser;
import org.cef.callback.CefJSDialogCallback;
import org.cef.handler.CefJSDialogHandler;
import org.cef.misc.BoolRef;

import javax.swing.*;
import java.awt.*;

public class JSDialogHandler implements CefJSDialogHandler {

    private final CefBrowser browser;

    public JSDialogHandler(CefBrowser browser) {
        this.browser = browser;
    }

    @Override
    public boolean onJSDialog(CefBrowser browser, String origin_url, JSDialogType dialog_type, String message_text, String default_prompt_text, CefJSDialogCallback callback, BoolRef suppress_message) {
        // Create a new window for the JS dialog
        JFrame dialogFrame = new JFrame("JavaScript Dialog");
        dialogFrame.setSize(new Dimension(400, 300));
        dialogFrame.setLocationRelativeTo(null);

        // Display the JS dialog in the new window
        int userChoice = JOptionPane.showConfirmDialog(dialogFrame, "JavaScript Dialog", "dsfsd",JOptionPane.OK_CANCEL_OPTION);

        if (userChoice == JOptionPane.OK_OPTION) {
            String userInput = JOptionPane.showInputDialog(dialogFrame, "sda");
            callback.Continue(true, userInput);
        } else {
            try {
                callback.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if(dialog_type == JSDialogType.JSDIALOGTYPE_ALERT) {
            JOptionPane.showMessageDialog(dialogFrame, message_text, "JavaScript Alert", JOptionPane.WARNING_MESSAGE);
            callback.Continue(true, "");
        } else if (dialog_type == JSDialogType.JSDIALOGTYPE_CONFIRM) {
            userChoice = JOptionPane.showConfirmDialog(dialogFrame, message_text, "JavaScript Confirm", JOptionPane.OK_CANCEL_OPTION);
            if (userChoice == JOptionPane.OK_OPTION) {
                callback.Continue(true, "true");
            } else {
                callback.Continue(true, "false");
            }
        } else if (dialog_type == JSDialogType.JSDIALOGTYPE_PROMPT) {
            String userInput = JOptionPane.showInputDialog(dialogFrame, message_text, default_prompt_text);
            callback.Continue(true, userInput);
        }

        dialogFrame.dispose();
        return true;
    }

    @Override
    public boolean onBeforeUnloadDialog(CefBrowser browser, String message_text, boolean is_reload, CefJSDialogCallback callback) {
        return false;
    }

    @Override
    public void onResetDialogState(CefBrowser browser) {

    }

    @Override
    public void onDialogClosed(CefBrowser browser) {
        // Handle closing the dialog
    }
}
