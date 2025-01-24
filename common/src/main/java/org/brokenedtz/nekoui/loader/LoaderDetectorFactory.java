package org.brokenedtz.nekoui.loader;

public class LoaderDetectorFactory {
    private static String loaderDetector;

    public static String getLoaderDetector() {
        if (loaderDetector == null) {
            throw new IllegalStateException("LoaderDetector not initialized!");
        }
        return loaderDetector;
    }

    public static void setLoaderDetector(String detector) {
        loaderDetector = detector;
    }
}
