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
import ru.apache_maven.*;

import static ru.apache_maven.Parser.*;

public class App 
{
    public static void main( String[] args ) throws Exception {

        ClassLoader classLoader = App.class.getClassLoader();
        Path dir = Paths.get(Objects.requireNonNull(classLoader.getResource("files")).toURI());
        HashMap<String, ArrayList<Integer>> mapAll = initMarksToMap();
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        HashMap<String, ArrayList<Integer>> map3 = new HashMap<>();

        // парсинг файлов
        File[] arrFiles = dir.toFile().listFiles();
        List<File> lstFiles;
        {
            assert arrFiles != null;
            lstFiles = Arrays.asList(arrFiles);
        }

        for (File f : lstFiles) {
            mapAll.putAll(csvToMap(f, mapAll));
        }
        printMapArr("-= mapAll =-", mapAll);

        for (Map.Entry entry: mapAll.entrySet()) {
            if (entry.getValue() != null) {
                Integer val = 0;
                for (Integer v :
                        (ArrayList<Integer>) entry.getValue()) {
                    val += v;
                }
                map1.put(entry.getKey().toString(), val);
                map2.put(entry.getKey().toString(), val);
                map3.put(entry.getKey().toString(), (ArrayList<Integer>)entry.getValue());
            } else
                map2.put(entry.getKey().toString(), (Integer) entry.getValue());
        }
        printMapInt("-= map1 =-", map1);
        printMapInt("-= map2 =-", map2);
        printMapArr("-= map3 =-", map3);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.putAll(map1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.putAll(map2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.putAll(map3);

        System.out.println("\njsonObject1: " + jsonObject1.toJSONString());
        System.out.println("jsonObject2: " + jsonObject2.toJSONString());
        System.out.println("jsonObject3: " + jsonObject3.toJSONString());

        try (FileWriter file1 = new FileWriter(dir.toString() + "/test1.json")) {
            file1.write(jsonObject1.toJSONString());
            file1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file2 = new FileWriter(dir.toString() + "/test2.json")) {
            file2.write(jsonObject2.toJSONString());
            file2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file3 = new FileWriter(dir.toString() + "/test3.json")) {
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
