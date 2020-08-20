package ru.apache_maven;

import java.io.File;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class App 
{
//    CSVParser parser = new CSVParserBuilder()
//            .withSeparator(',')
//            .withIgnoreQuotations(true)
//            .build();
//
//    CSVReader csvReader = new CSVReaderBuilder(reader)
//            .withSkipLines(0)
//            .withCSVParser(parser)
//            .build();

    public static void main( String[] args ) throws Exception {

        ClassLoader classLoader = App.class.getClassLoader();
        Path dir = Paths.get(Objects.requireNonNull(classLoader.getResource("files")).toURI());

        File[] arrFiles = dir.toFile().listFiles();
        List<File> lst;
        {
            assert arrFiles != null;
            lst = Arrays.asList(arrFiles);
        }

        System.out.println(lst.toString());
        for (File f : lst) {
            oneByOne(f.toPath());
        }
    }

    public static String oneByOne(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        return CsvReader.oneByOne(reader).toString();
    }

    public String readAll(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(
                Paths.get(ClassLoader.getSystemResource("csv/twoColumn.csv").getPath()));
        return CsvReader.readAll(reader).toString();
    }
}
