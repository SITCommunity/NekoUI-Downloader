package org.brokenedtz.nekoui.downloader;

import net.minecraft.client.Minecraft;
import org.brokenedtz.nekoui.core.Constants;
import org.brokenedtz.nekoui.loader.LoaderDetectorFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class NekoUIDownloader {
    public static void download(String tag, String version) {
        try {
            final int[] previousProgress = {-1};

            String loader = LoaderDetectorFactory.getLoaderDetector();
            Path modsFolder = Path.of("mods");
            Path destination = Path.of(modsFolder.toString(), "nekoui-" + version + "-" + loader + ".jar");
            String fileURL = URLGenerator.generateDownloadURL(tag, version);

            if (isMainModAlreadyExists(modsFolder, version, loader)) {
                Constants.LOG.warn("NekoUI mod file already exists: {}", destination.toAbsolutePath());
                Constants.LOG.info("Attempting to remove NekoUI Downloader...");

                removeDownloaderIfExists();
                return;
            }
            Constants.LOG.info("Starting Downloading NekoUI...");

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
            Constants.LOG.warn("This is normal crash when remove nekoui downloader, please dont report it. Just relaunch the client, and it will loaded normally.");

            removeDownloaderIfExists();
            Minecraft.getInstance().destroy();
        } catch (Exception e) {
            Constants.LOG.error("Failed to download file: {}", e.getMessage());
        }
    }

    private static boolean isMainModAlreadyExists(Path modsFolder, String version, String loader) {
        try (Stream<Path> files = Files.list(modsFolder)) {
            return files
                    .filter(Files::isRegularFile)
                    .anyMatch(file -> {
                        String fileName = file.getFileName().toString();
                        return fileName.matches("nekoui-" + version + "-" + loader + "\\.jar");
                    });
        } catch (Exception e) {
            Constants.LOG.error("Failed to check existing mod files: {}", e.getMessage());
            return false;
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
                    return;
                }
            } catch (Exception e) {
                Constants.LOG.error("Failed to remove downloader {}: {}", downloader, e.getMessage());
            }
        }
        Constants.LOG.warn("No downloader files found to remove.");
    }
}
