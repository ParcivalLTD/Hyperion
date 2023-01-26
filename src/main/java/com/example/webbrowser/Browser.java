package com.example.webbrowser;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefCookieVisitor;
import org.cef.handler.CefCookieAccessFilter;
import org.cef.network.CefCookie;
import org.cef.network.CefCookieManager;
import org.json.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

public class Browser {
    private static final long serialVersionUID = -5570653778104813836L;
    private final CefClient client_;
    private final CefBrowser browser_;
    private final Component browerUI_;

    public Browser(String startURL, boolean useOSR, boolean isTransparent) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        CefApp cefApp = CefAppSingleton.getInstance(false);
        client_ = cefApp.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        client_.addMessageRouter(msgRouter);

        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browerUI_ = browser_.getUIComponent();

        client_.addJSDialogHandler(new JSDialogHandler(browser_));
    }

    public CefClient getClient_() {
        return client_;
    }

    public CefBrowser getBrowser_() {
        return browser_;
    }

    public Component getBrowserUI_() {
        return browerUI_;
    }
}
