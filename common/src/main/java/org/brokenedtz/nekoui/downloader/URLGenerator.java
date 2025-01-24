package org.brokenedtz.nekoui.downloader;

import static org.brokenedtz.nekoui.core.Constants.BASE_URL;
import org.brokenedtz.nekoui.loader.LoaderDetectorFactory;

public class URLGenerator {
    public static String generateDownloadURL(String tag, String version) {
        String loader = LoaderDetectorFactory.getLoaderDetector();

        return String.format("%s%s/nekoui-%s-%s.jar", BASE_URL, tag, version, loader);
    }
}
