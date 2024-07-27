package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        File file = new File(this.file);
        String url = this.url;
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[512];
            int bytesRead;
            int bytesDataRead = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                bytesDataRead += bytesRead;
                output.write(dataBuffer, 0, bytesRead);
                if (bytesDataRead >= speed) {
                    long interval = System.currentTimeMillis() - start;
                    if (interval < 1000) {
                        try {
                            Thread.sleep(1000 - interval);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    bytesDataRead = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String url, int speed, String fileName) {
        if (url.isBlank()) {
            throw new IllegalArgumentException("Url is empty!");
        }
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            throw new IllegalArgumentException("Wrong Url");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Invalid speed of download");
        }
        if (!fileName.contains(".")) {
            throw new IllegalArgumentException("Wrong name of file");
        }
        if (fileName.length() < 2) {
            throw new IllegalArgumentException("Wrong name of file");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 3) {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String fileName = args[2];
            validate(url, speed, fileName);
            Thread first = new Thread(new Wget(url, speed, fileName));
            first.start();
            first.join();
        } else {
            throw new IllegalArgumentException("Incorrect data");
        }
    }
}