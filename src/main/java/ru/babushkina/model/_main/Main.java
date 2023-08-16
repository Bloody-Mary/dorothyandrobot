package ru.babushkina.model._main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        File dataFolder = new File("data");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        File currentFolder = new File(dataFolder, currentDate);

        if (currentFolder.exists()) {
            if (deleteDirectory(currentFolder)) {
                System.out.println("Каталог \"" + currentDate + "\" успешно удален в папке \"data\".");
            } else {
                System.out.println("Ошибка при удалении каталога \"" + currentDate + "\" в папке \"data\".");
                return;
            }
        }

        boolean currentFolderCreated = currentFolder.mkdirs();
        if (currentFolderCreated) {
            System.out.println("Каталог \"" + currentDate + "\" успешно создан в папке \"data\".");
        } else {
            System.out.println("Ошибка при создании каталога \"" + currentDate + "\" в папке \"data\".");
            return;
        }

        String filePath = "src/main/resources/text_txt.txt";
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            List<String> unknownWords = findUnknownWords(lines);

            if (unknownWords.size() >= 2) {
                createFileInFolder(currentFolder, unknownWords.get(0));
                createFileInFolder(currentFolder, unknownWords.get(1));
                createPunctuationFileInFolder(currentFolder);
                List<String> allWords = getAllWords(lines);
                processFileInFolder(currentFolder, unknownWords.get(0), allWords, true);
                processFileInFolder(currentFolder, unknownWords.get(1), allWords, false);
                processPunctuationFileInFolder(currentFolder, lines);
                System.out.println("Файлы успешно созданы и обработаны.");
            } else {
                System.out.println("В тексте недостаточно непонятных слов.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> findUnknownWords(List<String> lines) {
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

    private static boolean hasNumbers(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private static void createFileInFolder(File folder, String fileName) throws IOException {
        File file = new File(folder, fileName);
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(file);
            writer.close();
        } else {
            System.out.println("Файл " + fileName + " уже существует в каталоге \"" + folder.getName() + "\".");
        }
    }

    private static void createPunctuationFileInFolder(File folder) throws IOException {
        File file = new File(folder, "punctuation");
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(file);
            writer.close();
        } else {
            System.out.println("Файл punctuation уже существует в каталоге \"" + folder.getName() + "\".");
        }
    }

    private static List<String> getAllWords(List<String> lines) {
        List<String> allWords = new ArrayList<>();
        for (String line : lines) {
            BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
            iterator.setText(line);
            int start = iterator.first();

            for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
                String word = line.substring(start, end).trim().replaceAll("[^a-zA-Z]", "");
                if (word.length() > 0) {
                    allWords.add(word);
                }
            }
        }
        return allWords;
    }

    private static void processFileInFolder(File folder, String fileName, List<String> allWords, boolean isEvenVowels) throws IOException {
        File file = new File(folder, fileName);
        List<String> outputWords = new ArrayList<>();
        for (String word : allWords) {
            if (!hasNumbers(word)) {
                int vowelsCount = countVowels(word);
                if ((isEvenVowels && vowelsCount % 2 == 0) || (!isEvenVowels && vowelsCount % 2 != 0)) {
                    outputWords.add(word);
                }
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(String.join(" ", outputWords));
        writer.close();
    }

    private static int countVowels(String word) {
        String vowels = "aeiouAEIOU";
        int count = 0;
        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    private static void processPunctuationFileInFolder(File folder, List<String> lines) throws IOException {
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

    private static boolean isPunctuation(char c) {
        return !Character.isLetterOrDigit(c) && !Character.isWhitespace(c);
    }

    private static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }
}
