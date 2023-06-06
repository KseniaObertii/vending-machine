package util;

import model.Coin;
import model.Command;
import model.Product;
import service.VendingMachine;

import java.util.List;

public class CommandListRunner {

    private final VendingMachine vendingMachine;
    public CommandListRunner(VendingMachine vm) {
        vendingMachine = vm;
    }

    public void performAction(Command action, String item) {
        switch (action) {
            case DEPOSIT -> vendingMachine.depositCoin(Coin.valueOf(item));
            case SELECT -> vendingMachine.dispenseDrinkAndChange(Product.valueOf(item));
            case CANCEL -> vendingMachine.cancel();
            default -> throw new UnsupportedOperationException("Command " + action + " is not supported");
        }
    }

    public void runLine(List<String> line) {
        Command command = Command.valueOf(line.get(0));
        if (line.size() == 1) {
            performAction(command, null);
        } else {
            performAction(command, line.get(1));
        }
    }
}
