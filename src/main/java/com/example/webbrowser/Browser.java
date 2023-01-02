package com.example.webbrowser;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import java.awt.*;
import java.io.IOException;

public class Browser {
    private static final long serialVersionUID = -5570653778104813836L;

    private final CefApp cefApp_;
    private final CefClient client_;
    private final CefBrowser browser_;
    private final Component browerUI_;

    Browser(String startURL, boolean useOSR, boolean isTransparent) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        CefAppBuilder builder = new CefAppBuilder();
        builder.getCefSettings().windowless_rendering_enabled = useOSR;
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }
        });
        cefApp_ = builder.build();
        client_ = cefApp_.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        client_.addMessageRouter(msgRouter);

        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browerUI_ = browser_.getUIComponent();
    }

    public CefApp getCefApp_() {
        return cefApp_;
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
