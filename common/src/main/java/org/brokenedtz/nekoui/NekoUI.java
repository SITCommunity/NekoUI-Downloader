package org.brokenedtz.nekoui;

import static org.brokenedtz.nekoui.core.Constants.GAME_LOADER;
import org.brokenedtz.nekoui.downloader.NekoUIDownloader;
import org.brokenedtz.nekoui.loader.LoaderDetectorFactory;

public class NekoUI {
    public static void init() {
        LoaderDetectorFactory.setLoaderDetector(GAME_LOADER);

        String tag = "v1.0-alpha";
        String version = "1.0-alpha";

        NekoUIDownloader.download(tag, version);
    }
}