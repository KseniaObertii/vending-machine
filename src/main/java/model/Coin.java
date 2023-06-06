package model;

public enum Coin {
    NICKEL("Nickel", 5),
    DIME("Dime", 10),
    QUARTER("Quarter", 25);

    private String name;
    private int denomination;

    Coin(String name, int denomination) {
        this.name = name;
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public String getName() {return name;}
}
