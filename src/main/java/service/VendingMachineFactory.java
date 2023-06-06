package service;

import model.Coin;
import model.Product;

public class VendingMachineFactory {
    public static VendingMachine createVendingMachine() {

        Inventory<Coin> moneyInventory =  new Inventory<>();
        Inventory<Product> productInventory =  new Inventory<>();

        moneyInventory.add(Coin.QUARTER, 5);
        moneyInventory.add(Coin.DIME, 5);
        moneyInventory.add(Coin.NICKEL, 5);
        productInventory.add(Product.COLA, 10);
        productInventory.add(Product.DIET_COLA, 8);
        productInventory.add(Product.LIME_SODA, 0);
        productInventory.add(Product.WATER, 2);

        return new VendingMachineImpl(moneyInventory, productInventory);
    }
}
