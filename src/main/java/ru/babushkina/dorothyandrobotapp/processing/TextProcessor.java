package ru.babushkina.dorothyandrobotapp.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextProcessor {
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

    public boolean hasNumbers(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllWords(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(word -> word.replaceAll("[^a-zA-Z]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }
}
