package reader;

public class FileReaderFactory {
    public static FileReader createFileReader() {
        return new FileReaderImpl();
    }
}
