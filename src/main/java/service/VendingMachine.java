package service;

import model.Coin;
import model.Product;

import java.util.List;

public interface VendingMachine {
    void depositCoin(Coin coin);

    void dispenseDrinkAndChange(Product product);

    List<Coin> cancel();

    void printStatus();
}