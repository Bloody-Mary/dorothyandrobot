package ru.babushkina.dorothyandrobotapp.model;

import java.util.List;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        CustomFileReader fileReader = new CustomFileReader();
        List<String> lines = fileReader.readLines("src/main/resources/text_txt.txt");
        FileProcessor fileProcessor = new FileProcessor();
        List<String> unknownWords = fileProcessor.findWordsWithNumbers(lines);
        if (unknownWords.size() >= 2) {
            CustomFileWriter customFileWriter = new CustomFileWriter();
            String currentDate = customFileWriter.getCurrentDate();
            File currentFolder = customFileWriter.createCurrentFolder("data", currentDate);
            try {
                customFileWriter.createFiles(currentFolder, unknownWords);
                customFileWriter.createPunctuationFile(currentFolder);
                List<String> allWords = fileProcessor.extractWordsFromLines(lines);
                fileProcessor.processFiles(currentFolder, unknownWords, allWords);
                FileProcessor.processPunctuationFile(currentFolder, lines);
                System.out.println("Файлы успешно созданы и обработаны.");
            } catch (IOException e) {
                System.out.println("An error occurred during file creation or processing: " + e.getMessage());
            }
        }
    }
}
