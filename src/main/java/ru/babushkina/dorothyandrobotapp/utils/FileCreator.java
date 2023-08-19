package ru.babushkina.dorothyandrobotapp.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreator {
    public void createDataFolderIfNotExists() {
        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
    }

    public File createCurrentFolder() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        File currentFolder = new File("data/" + currentDate);
        currentFolder.mkdirs();
        return currentFolder;
    }

    public void createFileInFolder(File folder, String fileName) throws IOException {
        File file = new File(folder, fileName);
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(file);
            writer.close();
        } else {
            System.out.println("Файл " + fileName + " уже существует в каталоге \"" + folder.getName() + "\".");
        }
    }

    public void createPunctuationFileInFolder(File folder) throws IOException {
        File file = new File(folder, "punctuation");
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(file);
            writer.close();
        } else {
            System.out.println("Файл punctuation уже существует в каталоге \"" + folder.getName() + "\".");
        }
    }
}
