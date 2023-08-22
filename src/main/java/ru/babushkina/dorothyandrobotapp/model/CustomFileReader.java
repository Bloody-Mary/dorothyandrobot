package ru.babushkina.dorothyandrobotapp.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomFileReader {
    public String readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
