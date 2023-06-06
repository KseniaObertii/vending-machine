import model.Coin;
import model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.Inventory;
import service.VendingMachineImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

public class VendingMachineImplTest {
    private VendingMachineImpl vendingMachine;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    Inventory<Coin> moneyInventory =  new Inventory<>();
    Inventory<Product> productInventory =  new Inventory<>();

    @Before
    public void setUp() {
        moneyInventory.add(Coin.QUARTER, 5);
        moneyInventory.add(Coin.DIME, 5);
        moneyInventory.add(Coin.NICKEL, 5);
        productInventory.add(Product.COLA, 10);
        productInventory.add(Product.DIET_COLA, 8);
        productInventory.add(Product.LIME_SODA, 0);
        productInventory.add(Product.WATER, 2);
        vendingMachine = new VendingMachineImpl(moneyInventory, productInventory);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void doAfter() {
        vendingMachine = null;
        outContent = new ByteArrayOutputStream();
        System.setOut(originalOut);
    }

    @Test
    public void depositAllKindsOfCoins_Ok() {
        int nickelBeforeDeposit = moneyInventory.getAmount(Coin.NICKEL);
        int dimeBeforeDeposit = moneyInventory.getAmount(Coin.DIME);
        int quarterBeforeDeposit = moneyInventory.getAmount(Coin.QUARTER);

        vendingMachine.depositCoin(Coin.DIME);
        vendingMachine.depositCoin(Coin.DIME);
        vendingMachine.depositCoin(Coin.QUARTER);

        assertEquals(nickelBeforeDeposit, moneyInventory.getAmount(Coin.NICKEL));
        assertEquals(dimeBeforeDeposit + 2, moneyInventory.getAmount(Coin.DIME));
        assertEquals(quarterBeforeDeposit + 1, moneyInventory.getAmount(Coin.QUARTER));
    }

    @Test
    public void buyProductWithExactPrice_Ok() {
        int deposit = Coin.QUARTER.getDenomination();
        int productBeforeDispense = productInventory.getAmount(Product.COLA);
        List<Coin> change = new ArrayList<>();

        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.dispenseDrinkAndChange(Product.COLA);

        assertEquals(deposit, Product.COLA.getPrice());
        assertEquals(productBeforeDispense - 1, productInventory.getAmount(Product.COLA));
        assertEquals("Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                "Dispense " + Product.COLA.getName() + ", return change: 0\r\n", outContent.toString());
    }

    @Test
    public void buyProductAndGetChange_Ok() {
        int expectedAmountInStock = productInventory.getAmount(Product.WATER) - 1;
        List<Coin> expectedChange = Arrays.asList(Coin.NICKEL);

        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.dispenseDrinkAndChange(Product.WATER);

        assertEquals(expectedAmountInStock, productInventory.getAmount(Product.WATER));
        assertEquals(5, moneyInventory.getAmount(Coin.DIME));
        assertEquals(7, moneyInventory.getAmount(Coin.QUARTER));
        assertEquals(4, moneyInventory.getAmount(Coin.NICKEL));
        assertEquals("Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                "Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                "Dispense " + Product.WATER.getName() + ", return change: " + expectedChange + "\r\n",
                outContent.toString());
    }

    @Test
    public void buyProductOutOfStock() {
        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.dispenseDrinkAndChange(Product.LIME_SODA);

        assertEquals(productInventory.getAmount(Product.LIME_SODA), 0);
        assertEquals("Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                        "Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                        "Sorry we don't have this product now!" + "\r\n",
                outContent.toString());
    }

    @Test
    public void buyProductNotFullPaid() {
        int productBeforeDispense = productInventory.getAmount(Product.COLA);

        vendingMachine.depositCoin(Coin.DIME);
        vendingMachine.dispenseDrinkAndChange(Product.COLA);

        assertEquals(productBeforeDispense, productInventory.getAmount(Product.COLA));
        assertEquals("Deposited coin: " + Coin.DIME.getName() + "\r\n" +
                        "You didn't insert enough money!" + "\r\n",
                outContent.toString());
    }

    @Test
    public void buyProductMachineHasNotEnoughChange() {
        moneyInventory.add(Coin.NICKEL, 0);
        moneyInventory.add(Coin.DIME, 0);
        int productBeforeDispense = productInventory.getAmount(Product.WATER);

        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.depositCoin(Coin.QUARTER);
        vendingMachine.dispenseDrinkAndChange(Product.WATER);

        assertEquals(productBeforeDispense, productInventory.getAmount(Product.WATER));
        assertEquals("Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                        "Deposited coin: " + Coin.QUARTER.getName() + "\r\n" +
                        "Cant give change, try another product" + "\r\n",
                outContent.toString());
    }


    @Test
    public void cancelOperation_Ok() {
        int nickelBeforeDeposit = moneyInventory.getAmount(Coin.NICKEL);
        int dimeBeforeDeposit = moneyInventory.getAmount(Coin.DIME);
        List<Coin> expectedRefund = Arrays.asList(Coin.DIME, Coin.NICKEL);

        vendingMachine.depositCoin(Coin.DIME);
        vendingMachine.depositCoin(Coin.NICKEL);

        List<Coin> actualRefund = vendingMachine.cancel();

        assertEquals(nickelBeforeDeposit, moneyInventory.getAmount(Coin.NICKEL));
        assertEquals(dimeBeforeDeposit, moneyInventory.getAmount(Coin.DIME));
        assertEquals(expectedRefund, actualRefund);
    }
}