package reader;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReader {
    List<List<String>> read(String fileName) throws FileNotFoundException;
}
