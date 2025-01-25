package org.brokenedtz.nekoui.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownloader {
    public static void downloadFile(String fileURL, Path destination, ProgressListener listener) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(fileURL).openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new IOException("File not found at URL: " + fileURL);
        } else if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to access file at URL: " + fileURL + " (Response Code: " + responseCode + ")");
        }

        long totalBytes = connection.getContentLengthLong();

        try (InputStream in = new URL(fileURL).openStream()) {
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
