import reader.FileReader;
import reader.FileReaderFactory;
import service.VendingMachine;
import service.VendingMachineFactory;
import util.CommandListRunner;

import java.io.FileNotFoundException;
import java.util.List;

public class VendingMachineApplication {
    public static void main(String[] args) {
        VendingMachine vendingMachine = VendingMachineFactory.createVendingMachine();
        FileReader fileReader = FileReaderFactory.createFileReader();
        CommandListRunner commandListRunner = new CommandListRunner(vendingMachine);

        List<List<String>> read;
        try {
            read = fileReader.read("src/main/resources/text.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        read.forEach(line -> {
            commandListRunner.runLine(line);
            vendingMachine.printStatus();
        });
    }
}