package org.brokenedtz.nekoui.downloader;

import org.brokenedtz.nekoui.core.Constants;
import org.brokenedtz.nekoui.loader.LoaderDetectorFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class NekoUIDownloader {
    public static void download(String tag, String version) {
        try {
            String fileURL = URLGenerator.generateDownloadURL(tag, version);
            Constants.LOG.info("Starting Downloading NekoUI...");

            Path destination = Path.of("mods/nekoui-" + version + "-" +
                    LoaderDetectorFactory.getLoaderDetector() + ".jar");

            final int[] previousProgress = {-1};

            FileDownloader.downloadFile(fileURL, destination, progress -> {
                if (progress != previousProgress[0]) {
                    previousProgress[0] = progress;

                    int barLength = 20;
                    int completedBars = progress * barLength / 100;
                    String progressBar = "[" + "#".repeat(completedBars) + " ".repeat(barLength - completedBars) + "]";

                    Constants.LOG.info("Downloading NekoUI... {} {}%", progressBar, progress);
                    Constants.LOG.warn("Minecraft will crashing after installation...");
                    if (progress == 100) {
                        System.out.println();
                    }
                }
            });
            Constants.LOG.info("\nDownload complete at: {}", destination.toAbsolutePath());

            removeDownloaderIfExists();
        } catch (Exception e) {
            Constants.LOG.error("Failed to download file: {}", e.getMessage());
        }
    }

    private static void removeDownloaderIfExists() {
        String[] downloaderFiles = {
                "nekoui_downloader_fabric.jar",
                "nekoui_downloader_forge.jar",
                "nekoui_downloader_quilt.jar"
        };

        for (String downloader : downloaderFiles) {
            Path downloaderPath = Path.of("mods/" + downloader);
            try {
                if (Files.exists(downloaderPath)) {
                    Files.delete(downloaderPath);
                    Constants.LOG.info("Downloader removed: {}", downloaderPath.toAbsolutePath());
                    Constants.LOG.warn("This is normal crash when remove nekoui downloader, please dont report it. Just relaunch the client, and it will loaded normally.");
                    return;
                }
            } catch (Exception e) {
                Constants.LOG.error("Failed to remove downloader {}: {}", downloader, e.getMessage());
            }
        }
        Constants.LOG.warn("No downloader files found to remove.");
    }
}
