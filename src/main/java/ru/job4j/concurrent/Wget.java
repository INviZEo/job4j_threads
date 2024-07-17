package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
        long startAt = System.currentTimeMillis();
        File file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
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
        if (!url.contains("https://") && !url.contains("http://")) {
            throw new IllegalArgumentException("Wrong Url");
        }
        if (speed < 0) {
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
        if (args.length > 2) {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String fileName = args[2];
            validate(url, speed, fileName);
            Thread first = new Thread(new Wget(url, speed, fileName));
            first.start();
            first.join();
        }
    }
}