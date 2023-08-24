package ru.babushkina.dorothyandrobotapp.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessor {
    public void processFiles(File currentFolder, List<String> fileNames, List<String> allWords) throws IOException {
        for (String fileName : fileNames) {
            boolean isEvenVowels = countVowels(fileName) % 2 == 0;
            processFile(currentFolder, fileName, allWords, isEvenVowels);
        }
    }

    private void processFile(File folder, String fileName, List<String> allWords, boolean isEvenVowels) throws IOException {
        File file = new File(folder, fileName);
        List<String> outputWords = new ArrayList<>();
        for (String word : allWords) {
            if (!hasNumbers(word)) {
                int vowelsCount = countVowels(word);
                if ((isEvenVowels && vowelsCount % 2 == 0) || (!isEvenVowels && vowelsCount % 2 != 0)) {
                    if (!outputWords.contains(word)) {
                        outputWords.add(word);
                    }
                }
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.join(" ", outputWords));
        }
    }

    public static void processPunctuationFile(File folder, List<String> lines) throws IOException {
        Map<Character, Integer> punctuationCounts = new HashMap<>();
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                if (isPunctuation(c)) {
                    punctuationCounts.put(c, punctuationCounts.getOrDefault(c, 0) + 1);
                }
            }
        }
        File file = new File(folder, "punctuation");
        FileWriter writer = new FileWriter(file);
        for (Map.Entry<Character, Integer> entry : punctuationCounts.entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue() + "\n");
        }
        writer.close();
    }

    private int countVowels(String word) {
        String vowels = "aeiouyAEIOUY";
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (vowels.indexOf(word.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    private static boolean isPunctuation(char c) {
        return !Character.isLetterOrDigit(c) && !Character.isWhitespace(c);
    }

    public List<String> findWordsWithNumbers(List<String> lines) {
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

    public List<String> extractWordsFromLines(List<String> lines) {
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