package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * Saves and loads objects from/to files
 *
 * @author
 */
public class FileUtils {

    /**
     * Saves an object to file
     *
     * @param o
     * @param filename
     */
    public static void saveObject(Object o, String filename) {
        Path path = Paths.get(filename);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an object from a file
     *
     * @param filename
     * @return
     */
    public static Object loadObject(String filename) {  //typeCasta objektet vid load
        Path path = Paths.get(filename);
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return in.readObject();
        } catch (NoSuchFileException e) {
            System.out.println(String.format("%s not found", filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // hgf(StandardOpenOption... option);  //för att skicka med fler options

    public static List<String> readAllLines(String fileName) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeAllLines(String saveFileName, List<String> lines) {

        try {
            Path path = Paths.get(saveFileName);
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
