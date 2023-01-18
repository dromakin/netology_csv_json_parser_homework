package org.dromakin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private static final String DATA_CSV = "data.csv";
    private static final String PATH_DATA_CSV = Paths.get(DATA_CSV).toAbsolutePath().toString();
    private static final String JSON_FILENAME = "data.json";


    public static void main(String[] args) {

        try {
            logger.info("Running parse csv");
            List<Employee> list = parseCSV(columnMapping, PATH_DATA_CSV);

            logger.info("List of Employee convert to json string");
            String json = listToJson(list);

            logger.info("write json string to file");

            writeString(json, JSON_FILENAME);
        } catch (ParserException e) {
            logger.error(e.getMessage(), e);
        }

    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) throws ParserException {

        List<Employee> csv;

        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            csv = new CsvToBeanBuilder<Employee>(csvReader).withMappingStrategy(strategy).build().parse();
        } catch (IOException e) {
            throw new ParserException(e.getMessage(), e);
        }

        return csv;
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    private static void writeString(String json, String jsonFilename) throws ParserException {
        try (FileWriter file = new FileWriter(jsonFilename)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            throw new ParserException(e.getMessage(), e);
        }
    }


}