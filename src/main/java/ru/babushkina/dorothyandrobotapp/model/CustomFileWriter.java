package ru.babushkina.dorothyandrobotapp.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomFileWriter {
    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    public File createCurrentFolder(String parentFolder, String currentDate) {
        File dataFolder = new File(parentFolder);
        createPath(parentFolder);

        File currentFolder = new File(dataFolder, currentDate);
        if (currentFolder.exists()) {
            if (!deleteDirectory(currentFolder)) {
                System.out.println("Ошибка при удалении каталога \"" + currentDate + "\" в папке \"data\".");
                return null;
            }
        }
        if (currentFolder.mkdirs()) {
            System.out.println("Каталог \"" + currentDate + "\" успешно создан в папке \"data\".");
            return currentFolder;
        } else {
            System.out.println("Ошибка при создании каталога \"" + currentDate + "\" в папке \"data\".");
            return null;
        }
    }

    public void createPath(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Каталог \"" + path + "\" успешно создан.");
            } else {
                System.out.println("Ошибка при создании каталога \"" + path + "\".");
            }
        }
    }

    public boolean deleteDirectory(File directory) {
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
        return directory.delete();
    }

    public void createFiles(File currentFolder, List<String> fileNames) throws IOException {
        for (String fileName : fileNames) {
            createFile(currentFolder, fileName);
        }
    }

    public void createFile(File folder, String fileName) throws IOException {
        File file = new File(folder, fileName);
        if (file.exists()) {
            System.out.println("Файл " + fileName + " уже существует в каталоге \"" + folder.getName() + "\".");
            return;
        }
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file)) {
            }
        } else {
            System.out.println("Ошибка при создании файла " + fileName + " в каталоге \"" + folder.getName() + "\".");
        }
    }

    public void createPunctuationFile(File folder) throws IOException {
        File file = new File(folder, "punctuation");
        if (file.exists()) {
            System.out.println("Файл punctuation уже существует в каталоге \"" + folder.getName() + "\".");
            return;
        }
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file)) {
            }
        } else {
            System.out.println("Ошибка при создании файла punctuation в каталоге \"" + folder.getName() + "\".");
        }
    }
}