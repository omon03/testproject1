package ru.apache_maven;

import com.opencsv.CSVReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

    public static List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public static List<String[]> oneByOne(Reader reader) throws Exception {
        List<String[]> list = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        String[]line;
        while ((line = csvReader.readNext()) != null) {
            if (!line[0].contains("#")) {
                list.add(line);
                System.out.println(Arrays.toString(line));
            }
            else System.out.println(line[0]);
        }
        reader.close();
        csvReader.close();
        return list;
    }
}
