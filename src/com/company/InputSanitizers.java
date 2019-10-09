package com.company;

public class InputSanitizers {

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int convertToInt(String str) {
        try {
            int number = Integer.parseInt(str);
            return number;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isAlphabet(String s){
        return s != null && s.chars().allMatch(Character::isAlphabetic);
    }
}
