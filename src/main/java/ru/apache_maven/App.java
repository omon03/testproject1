package ru.apache_maven;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class App 
{
    static final String[] marks = {"mark01", "mark17", "mark23", "mark35", "markFV", "markFX", "markFT"};

    public static void main( String[] args ) throws Exception {

        ClassLoader classLoader = App.class.getClassLoader();
        Path dir = Paths.get(Objects.requireNonNull(classLoader.getResource("files")).toURI());
        HashMap<String, ArrayList<Integer>> mapAll = new HashMap<>();
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        HashMap<String, ArrayList<Integer>> map3 = new HashMap<>();

        // заполнение ключами итогового словаря
        for (String key: marks) {
            if (!mapAll.containsKey(key))
                mapAll.put(key, null);
        }

        // парсинг файлов
        File[] arrFiles = dir.toFile().listFiles();
        List<File> lstFiles;
        {
            assert arrFiles != null;
            lstFiles = Arrays.asList(arrFiles);
        }
        for (File f : lstFiles) {
            List<String[]> arrBuf = oneByOne(f.toPath());
            
            for (String[] str: arrBuf) {
                String key = str[0];

                for (String mark: marks) {
                    if (mark.equalsIgnoreCase(key))
                        key = mark;
                }

                if (mapAll.get(key) == null) {
                    ArrayList<Integer> val = new ArrayList<>();
                    val.add(Integer.parseInt(str[1]));
                    mapAll.put(key, val);
                } else {
                    ArrayList<Integer> valOld = mapAll.get(key);
                    valOld.add(Integer.parseInt(str[1]));
                    Collections.sort(valOld);
                    Collections.reverse(valOld);
                    mapAll.put(key, valOld);
                }
            }
        }
        printMapArr("mapAll", mapAll);

        // заполнение map1
        for (Map.Entry entry: mapAll.entrySet()) {
            if (entry.getValue() != null) {
                Integer val = 0;
                for (Integer v :
                        (ArrayList<Integer>) entry.getValue()) {
                    val += v;
                }
                map1.put(entry.getKey().toString(), val);
            }
        }
        printMapInt("map1", map1);

        // заполнение map2
        for (Map.Entry entry: mapAll.entrySet()) {
            if (entry.getValue() != null) {
                Integer val = 0;
                for (Integer v :
                        (ArrayList<Integer>) entry.getValue()) {
                    val += v;
                }
                map2.put(entry.getKey().toString(), val);
            } else
                map2.put(entry.getKey().toString(), (Integer) entry.getValue());
        }
        printMapInt("map2", map2);

        // заполнение map3
        for (Map.Entry entry: mapAll.entrySet()) {
            if (entry.getValue() != null) {
                map3.put(entry.getKey().toString(), (ArrayList<Integer>)entry.getValue());
            }
        }
        printMapArr("map3", map3);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.putAll(map1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.putAll(map2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.putAll(map3);

        try (FileWriter file1 = new FileWriter("f:\\test1.json")) {
            file1.write(jsonObject1.toJSONString());
            file1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file2 = new FileWriter("f:\\test2.json")) {
            file2.write(jsonObject2.toJSONString());
            file2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file3 = new FileWriter("f:\\test3.json")) {
            file3.write(jsonObject3.toJSONString());
            file3.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> oneByOne(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        return CsvReader.oneByOne(reader);
    }

    public static void printMapInt(String str, Map<String, Integer> map) {
        System.out.println("\n" + str);
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void printMapArr(String str, Map<String, ArrayList<Integer>> map) {
        System.out.println("\n" + str);
        for (Map.Entry<String, ArrayList<Integer>> entry: map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
