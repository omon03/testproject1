package ru.apache_maven;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Parser {
    final static String[] marks = {"mark01", "mark17", "mark23", "mark35", "markFV", "markFX", "markFT"};

    private static List<String[]> oneByOne(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        return CsvReader.oneByOne(reader);
    }

    public static HashMap<String, ArrayList<Integer>> initMarksToMap() {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();

        // заполнение ключами итогового словаря
        for (String key: marks) {
            if (!map.containsKey(key))
                map.put(key, null);
        }

        System.out.println("Предварительно заполненный словарь:\n" + map);
        return map;
    }

    public static HashMap<String, ArrayList<Integer>> csvToMap(File f, HashMap<String, ArrayList<Integer>> map) throws Exception {

        List<String[]> arrBuf = oneByOne(f.toPath());

        for (String[] str: arrBuf) {
            String key = str[0];

            for (String mark: marks) {
                if (mark.equalsIgnoreCase(key))
                    key = mark;
            }

            if (map.get(key) == null) {
                ArrayList<Integer> val = new ArrayList<>();
                val.add(Integer.parseInt(str[1]));
                map.put(key, val);
            } else {
                ArrayList<Integer> valOld = map.get(key);
                valOld.add(Integer.parseInt(str[1]));
                Collections.sort(valOld);
                Collections.reverse(valOld);
                map.put(key, valOld);
            }
        }

        return map;
    }
}
