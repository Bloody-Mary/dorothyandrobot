package ru.babushkina.dorothyandrobotapp.dataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {
    public List<String> readDataFromFile(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }
}
