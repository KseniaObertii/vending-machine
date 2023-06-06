package util;

import model.Coin;
import model.Command;
import model.Product;
import org.junit.After;
import org.junit.Test;
import service.VendingMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommandListRunnerTest {

    private VendingMachine vendingMachine = mock(VendingMachine.class);
    private CommandListRunner commandListRunner = new CommandListRunner(vendingMachine);

    @After
    public void doAfter() {
        vendingMachine = null;
    }

    @Test
    public void performAction_Ok() {
        commandListRunner.performAction(Command.DEPOSIT, "NICKEL");
        commandListRunner.performAction(Command.SELECT, "COLA");
        commandListRunner.performAction(Command.CANCEL, null);

        verify(vendingMachine,times(1)).depositCoin(any(Coin.class));
        verify(vendingMachine,times(1)).dispenseDrinkAndChange(any(Product.class));
        verify(vendingMachine,times(1)).cancel();
    }

    @Test
    public void performActionSecondCommandIsNotSupported() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> commandListRunner.performAction(Command.DEPOSIT, "WRONG"));

        String expectedMessage = "No enum constant model.Coin.WRONG";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(vendingMachine,times(0)).depositCoin(any(Coin.class));
    }
}