package ru.job4j.concurrent;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (int i = 0; i < content.length(); i++) {
                writer.write(content.charAt(i));
            }
        }
    }
}
