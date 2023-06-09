# Soda Vending Machine Control Software

This project involves the creation of a control software for a soda vending machine. The software is designed to meet the following requirements:
-   Accept coins in denominations of 5¢, 10¢, or 25¢ (nickels, dimes, and quarters) from the customer.
-   Provide a selection of products for the customer to choose from, including Cola (25¢), Diet Cola (35¢), Lime Soda (25¢), and Water (45¢).
-   Dispense the selected product and any necessary change based on the customer's payment.
-   Allow customers to cancel their purchase prior to making a selection, returning all deposited funds.

The implementation models these functional requirements and maintain the internal state of the machine. Upon startup, the machine is initialized with 5 coins of each denomination and the following quantities of drinks: 10 Cola, 8 Diet Cola, 0 Lime Soda (out of stock), and 2 Water.

To effectively test the controller, a routine has been provided to read a command file containing commands in the following format:
|text.txt|
|----------------|
|DEPOSIT,QUARTER 
|DEPOSIT,DIME 
|SELECT,DIET_COLA 
|DEPOSIT,NICKEL 
|DEPOSIT,NICKEL 
|CANCEL 
|DEPOSIT,QUARTER 
|SELECT,LIME_SODA

After each command, the software outputs any relevant actions taken, such as dispensing a drink or returning specific change, along with the updated internal state of the machine.
