package reader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @After
    public void doAfter() {
        fileReader = null;
    }

    @Test
    public void readFile_Ok() {
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("DEPOSIT","QUARTER"));
        expected.add(Arrays.asList("SELECT","DIET_COLA"));
        expected.add(Arrays.asList("CANCEL"));

        List<List<String>> actual;
        try {
            actual = fileReader.read("src/test/resources/file-reader-test.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void readNotExistedFileName_ThrowsEx() {
        Exception exception = assertThrows(FileNotFoundException.class,
                () -> fileReader.read("wrong-name.txt"));

        String expectedMessage = "File not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}