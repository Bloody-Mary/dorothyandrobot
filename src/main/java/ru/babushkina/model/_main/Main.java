package ru.babushkina.model._main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String fileName = "path/to/text_txt.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String firstUnknownWord = null;
            String secondUnknownWord = null;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (!isWord(word)) {
                        if (firstUnknownWord == null) {
                            firstUnknownWord = word;
                        } else if (secondUnknownWord == null) {
                            secondUnknownWord = word;
                        }

                        createFile(word, countVowels(word));
                        break;
                    }
                }
            }

            reader.close();

            if (firstUnknownWord != null) {
                removeWordFromFile(firstUnknownWord, "path/to/even_vowels.txt");
                removeWordFromFile(firstUnknownWord, "path/to/odd_vowels.txt");
            }

            if (secondUnknownWord != null) {
                removeWordFromFile(secondUnknownWord, "path/to/even_vowels.txt");
                removeWordFromFile(secondUnknownWord, "path/to/odd_vowels.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWord(String word) {
        Pattern pattern = Pattern.compile("\\p{L}+");
        return pattern.matcher(word).matches();
    }

    private static int countVowels(String word) {
        int count = 0;
        String vowels = "aeiouAEIOU";

        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }

        return count;
    }

    private static void createFile(String fileName, int vowelCount) {
        try {
            FileWriter writer;
            if (vowelCount % 2 == 0) {
                writer = new FileWriter("path/to/even_vowels.txt", true);
            } else {
                writer = new FileWriter("path/to/odd_vowels.txt", true);
            }

            String cleanedWord = cleanWord(fileName);

            Set<String> wordsSet = readWordsFromFile(writer);

            if (!wordsSet.contains(cleanedWord)) {
                writer.write(cleanedWord + " ");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> readWordsFromFile(FileWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(writer.toString()));
        Set<String> wordsSet = new HashSet<>();
        String line = reader.readLine();
        reader.close();

        if (line != null) {
            line = cleanWord(line);
            String[] words = line.trim().split("\\s+");
            for (String word : words) {
                wordsSet.add(word);
            }
        }

        return wordsSet;
    }

    private static String cleanWord(String word) {
        return word.trim().replaceAll("[^\\p{L} ]", "");
    }

    private static void removeWordFromFile(String word, String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            reader.close();

            line = line.replaceAll("\\b" + word + "\\b", "");

            FileWriter writer = new FileWriter(fileName);
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}