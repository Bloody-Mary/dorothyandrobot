package ru.babushkina.dorothyandrobotapp.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CustomFileReader {
    public List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            throw new RuntimeException("Ошибка при чтении файла", e);
        }
    }
}
