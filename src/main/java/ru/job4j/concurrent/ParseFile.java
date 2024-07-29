package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;
    private final File fileOutput;

    public ParseFile(File file, File fileOutput) {
        this.file = file;
        this.fileOutput = fileOutput;
    }

    public String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder(String.valueOf(fileOutput));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            int data;
            while ((data = reader.read()) != -1) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return content(character -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(character -> character < 0x80);
    }
}