package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.*;

/**
 * Saves and loads objects to/from files
 *
 * @author
 */
public class FileUtils {

    /**
     * Saves an object to file
     *
     * @param o        Object to save
     * @param filename Name of the file to save
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
     * @param filename Name of the file to load
     * @return The object read
     */
    public static Object loadObject(String filename) {
        Path path = Paths.get(filename);
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
