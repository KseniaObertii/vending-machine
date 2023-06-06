package service;

import model.Coin;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {
    private final Inventory<Coin> moneyInventory;
    private final Inventory<Product> productInventory;
    private Integer currentBalance = 0;

    public VendingMachineImpl(Inventory<Coin> moneyInventory, Inventory<Product> productInventory) {
        this.moneyInventory = moneyInventory;
        this.productInventory = productInventory;
    }

    @Override
    public void depositCoin(Coin coin) {
        currentBalance += coin.getDenomination();
        moneyInventory.add(coin);
        System.out.println("Deposited coin: " + coin.getName());
    }

    @Override
    public void dispenseDrinkAndChange(Product product) {
        if (!productInventory.isItemAvailableInStock(product)) {
            System.out.println("Sorry we don't have this product now!");
            return;
        }

        if (currentBalance < product.getPrice()) {
            System.out.println("You didn't insert enough money!");
            return;
        }

        int expectedChange = currentBalance - product.getPrice();
        if (expectedChange == 0) {
            productInventory.deduct(product);
            System.out.println("Dispense " + product.getName() + ", return change: 0");
        } else {
            List<Coin> change = getChange(expectedChange);
            if (change.isEmpty()) {
                System.out.println("Cant give change, try another product");
                return;
            }

            productInventory.deduct(product);
            currentBalance = 0;
            System.out.println("Dispense " + product.getName() + ", return change: " + change);
        }

    }

    @Override
    public List<Coin> cancel() {
        List<Coin> refund = getChange(currentBalance);
        currentBalance = 0;
        System.out.println("Operation canceled. Return money: " + refund);
        return refund;
    }

    @Override
    public void printStatus() {
        System.out.println("Money left in the machine: " + moneyInventory);
        System.out.println("Drinks left in the machine: " + productInventory);
        System.out.println("Your total deposit: " + currentBalance);
    }


    private List<Coin> getChange(int amount) {
        List<Coin> change = new ArrayList<>();
        int balance = amount;
        while (balance > 0) {
            if (balance >= Coin.QUARTER.getDenomination() && moneyInventory.isItemAvailableInStock(Coin.QUARTER)) {
                balance = updateBalance(change, Coin.QUARTER, balance);
            } else if (balance >= Coin.DIME.getDenomination() && moneyInventory.isItemAvailableInStock(Coin.DIME)) {
                balance = updateBalance(change, Coin.DIME, balance);
            } else if (balance >= Coin.NICKEL.getDenomination() && moneyInventory.isItemAvailableInStock(Coin.NICKEL)) {
                balance = updateBalance(change, Coin.NICKEL, balance);
            } else {
                change.forEach(moneyInventory::add);
                return new ArrayList<>();
            }
        }
        return change;
    }

    private int updateBalance(List<Coin> change, Coin quarter, int balance) {
        change.add(quarter);
        balance -= quarter.getDenomination();
        moneyInventory.deduct(quarter);
        return balance;
    }
}
