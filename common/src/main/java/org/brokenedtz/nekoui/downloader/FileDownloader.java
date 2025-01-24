package org.brokenedtz.nekoui.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownloader {
    public static void downloadFile(String fileURL, Path destination, ProgressListener listener) throws IOException {
        try (InputStream in = new URL(fileURL).openStream()) {
            long totalBytes = new URL(fileURL).openConnection().getContentLengthLong();
            long downloadedBytes = 0;

            Files.createDirectories(destination.getParent());

            try (var outputStream = Files.newOutputStream(destination)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    downloadedBytes += bytesRead;

                    if (listener != null) {
                        int progress = (int) ((downloadedBytes * 100) / totalBytes);
                        listener.onProgress(progress);
                    }
                }
            }
        }
    }

    @FunctionalInterface
    public interface ProgressListener {
        void onProgress(int percent);
    }
}
