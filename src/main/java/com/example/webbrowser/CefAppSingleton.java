package com.example.webbrowser;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;

import java.io.IOException;

public class CefAppSingleton {
    private static CefApp instance = null;
    private static String clientKey;
    private static String clientSecret;

    private CefAppSingleton() {
    }

    public static CefApp getInstance(boolean useOSR) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        if (instance == null) {
            CefAppBuilder builder = new CefAppBuilder();
            builder.getCefSettings().windowless_rendering_enabled = useOSR;
            // initialize clientKey and clientSecret
            clientKey = "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";
            clientSecret = "AIzaSyCiCfQAE72Jx2A71C-2MZVFOIWoPSCcOLA";
            builder.setAppHandler(new MavenCefAppHandlerAdapter() {
                @Override
                public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                    if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
                }
            });
            instance = builder.build();
        }
        return instance;
    }
}
