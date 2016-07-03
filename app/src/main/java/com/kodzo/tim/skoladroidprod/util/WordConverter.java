package com.kodzo.tim.skoladroidprod.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Milos PC on 26-Jun-16.
 */
public class WordConverter {

    private static HashMap<String, String> words = new HashMap<String, String>();

    public static void initLetters () {

//        words.

        words.put("а", "a");
        words.put("б", "b");
        words.put("в", "v");
        words.put("г", "g");
        words.put("д", "d");
        words.put("ђ", "đ");
        words.put("е", "e");
        words.put("ж", "ž");
        words.put("з", "z");
        words.put("и", "i");
        words.put("ј", "j");
        words.put("к", "k");
        words.put("л", "l");
        words.put("љ", "lj");
        words.put("м", "m");
        words.put("н", "n");
        words.put("њ", "nj");
        words.put("о", "o");
        words.put("п", "p");
        words.put("р", "r");
        words.put("с", "s");
        words.put("т", "t");
        words.put("ћ", "ć");
        words.put("у", "u");
        words.put("ф", "f");
        words.put("х", "h");
        words.put("ц", "c");
        words.put("ч", "č");
        words.put("џ", "dž");
        words.put("ш", "š");

        words.put("А", "A");
        words.put("Б", "B");
        words.put("В", "V");
        words.put("Г", "G");
        words.put("Д", "D");
        words.put("Ђ", "Đ");
        words.put("Е", "E");
        words.put("Ж", "Ž");
        words.put("З", "Z");
        words.put("И", "I");
        words.put("Ј", "J");
        words.put("К", "K");
        words.put("Л", "L");
        words.put("Љ", "LJ");
        words.put("М", "M");
        words.put("Н", "N");
        words.put("Њ", "NJ");
        words.put("О", "O");
        words.put("П", "P");
        words.put("Р", "R");
        words.put("С", "S");
        words.put("Т", "T");
        words.put("Ћ", "Ć");
        words.put("У", "U");
        words.put("Ф", "F");
        words.put("Х", "H");
        words.put("Ц", "C");
        words.put("Ч", "Č");
        words.put("Џ", "DŽ");
        words.put("Ш", "Š");

    }

    public  static String cirylicToLatin (String word) {

        if (words.size() == 0)
            initLetters();

        Iterator it = words.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String param1 = (String) pair.getKey();
            String param2 = (String) pair.getValue();

            word = word.replace(param1, param2);

            it.remove(); // avoids a ConcurrentModificationException
        }

        return word;
    }


    public  static String latinToCirylic (String word) {

        if (words.size() == 0)
            initLetters();


        Iterator it = words.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String param1 = (String) pair.getKey();
            String param2 = (String) pair.getValue();

            word = word.replace(param2, param1);

            it.remove(); // avoids a ConcurrentModificationException
        }

        return word;
    }

}

