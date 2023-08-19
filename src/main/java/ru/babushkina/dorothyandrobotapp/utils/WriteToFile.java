package ru.babushkina.dorothyandrobotapp.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteToFile {
    public void writeDataToFile(File file, String data) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.close();
    }

    public void writePunctuationToFile(File file, Map<Character, Integer> punctuationCounts) throws IOException {
        FileWriter writer = new FileWriter(file);
        for (Map.Entry<Character, Integer> entry : punctuationCounts.entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue() + System.lineSeparator());
        }
        writer.close();
    }
}
