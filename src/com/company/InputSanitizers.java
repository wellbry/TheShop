package com.company;

/**
 * A class to check user inputs. Ensures that inputs meet specific requirements and/or doesn't crash the program
 *
 * @author Magnus Wellbring
 */
public class InputSanitizers {

    /**
     * Checks if the input String is an int
     *
     * @param str The string to check
     * @return True if the input String is a number
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attempts to convert str to an int, primarily used with isNumber where negative ints are acceptable
     *
     * @param str The String to attempt to convert
     * @return An int
     */
    public static int convertToInt(String str) {
        try {
            int number = Integer.parseInt(str);
            return number;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Converts the input String to int if possible
     *
     * @param str Input String
     * @return The int if the string can be parsed to int, returns -1 if not
     */
    public static int convertToIntPositive(String str) {
        try {
            int number = Integer.parseInt(str);
            return number;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Checks if the input string only contains alphabetic characters
     *
     * @param s The String to check
     * @return True if the input String only contains alphabetic characters
     */
    public static boolean isAlphabetic(String s) {
        return s != null && s.chars().allMatch(Character::isAlphabetic);
    }
}
