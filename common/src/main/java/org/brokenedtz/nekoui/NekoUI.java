package org.brokenedtz.nekoui;

import static org.brokenedtz.nekoui.core.Constants.*;
import org.brokenedtz.nekoui.downloader.NekoUIDownloader;
import org.brokenedtz.nekoui.loader.LoaderDetectorFactory;

public class NekoUI {
    public static void init() {
        LoaderDetectorFactory.setLoaderDetector(GAME_LOADER);

        NekoUIDownloader.download(tag, version);
    }
}