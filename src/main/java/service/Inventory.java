package service;
import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private Map<T, Integer> inventory = new HashMap<>();

    public int getAmount(T t) {
        return inventory.getOrDefault(t, 0);
    }

    public void add(T t) {
        inventory.put(t, getAmount(t) + 1);
    }

    public void add(T t, int amount) {
        if (amount >= 0) {
            inventory.put(t, amount);
        }
    }

    public void deduct(T t) {
        if (inventory.containsKey(t)) {
            int newAmount = getAmount(t) - 1;
            if(newAmount <= 0) {
                inventory.remove(t);
            } else {
                inventory.put(t, newAmount);
            }
        }
    }

    public boolean isItemAvailableInStock(T t) {
        return getAmount(t) > 0;
    }

    @Override
    public String toString() {
        return inventory.toString();
    }
}
