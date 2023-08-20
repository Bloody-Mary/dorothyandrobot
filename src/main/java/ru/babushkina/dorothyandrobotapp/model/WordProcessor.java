package ru.babushkina.dorothyandrobotapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordProcessor {
    public List<String> findUnknownWords(List<String> lines) {
        List<String> unknownWords = new ArrayList<>();
        for (String line : lines) {
            String[] words = line.split(" ");
            for (String word : words) {
                if (hasNumbers(word)) {
                    unknownWords.add(word);
                }
            }
        }
        return unknownWords;
    }

    public List<String> getAllWords(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(word -> word.replaceAll("[^a-zA-Z]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    private boolean hasNumbers(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}