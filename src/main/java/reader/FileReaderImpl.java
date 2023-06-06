package reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReaderImpl implements FileReader {
    public List<List<String>> read(String fileName) throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new java.io.FileReader(fileName))) {
            String[] values;
            while ((values = reader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
