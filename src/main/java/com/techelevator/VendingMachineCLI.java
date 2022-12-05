package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	private static final String SECOND_MENU_OPTION_FEED_MONEY = "Deposit Money to the vending machine.";
	private static final String SECOND_MENU_OPTION_SELECT_PRODUCT = "Type in the Product Key for the item you wish to buy";
	private static final String SECOND_MENU_OPTION_FINISH_TRANSACTION = "Exit Program";
	private static final String[] SECOND_MENU_OPTIONS = {SECOND_MENU_OPTION_FEED_MONEY, SECOND_MENU_OPTION_SELECT_PRODUCT, SECOND_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachine machine = new VendingMachine();
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
		machine.getListOfItems();
	}

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws IOException {

		try {
			File transactionLog = new File("Log.txt");
			if (!transactionLog.exists()) {
				transactionLog.createNewFile();
			}
			PrintWriter log = new PrintWriter(transactionLog);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd//yyyy HH:mm:ss a");
			LocalDateTime dateTime = LocalDateTime.now();
			String dateTimeString = dateTime.format(formatter);



			boolean vending = true;
			UserMoney balance = new UserMoney();
			Scanner amountToDeposit = new Scanner(System.in);
			Scanner desiredProduct = new Scanner(System.in);

			while (vending) {
				String firstChoice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
				VendingMachine machine = new VendingMachine();

				if (firstChoice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					machine.printListOfItems();
					System.out.println("----------------------\n");

				} else if (firstChoice.equals(MAIN_MENU_OPTION_EXIT)) {
					System.out.println("--------------------------------------------------");
					System.out.println("\nThank you for shopping with Team 3 Vending Machine");
					break;

				} else if (firstChoice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					System.out.println("----------------------------");
					System.out.println("\nYour current balance is $" + balance.getBalance());
					String purchaseChoice = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS);
					if (purchaseChoice.equals(SECOND_MENU_OPTION_FEED_MONEY)) {
						System.out.println("How much would you like to deposit? \n(1) $1.00, \n(5) $5.00 \n(10) $10.00\n");
						double depositAmount = amountToDeposit.nextDouble();
						if (depositAmount == 1) {
							balance.setBalance(1.00);
							System.out.println("Your current balance is $" + balance.getBalance());
							log.println(dateTimeString + " FEED MONEY: " + "$1.00" + " $" + balance.getBalance()); //logging feed money to Log.txt
						} else if (depositAmount == 5) {
							balance.setBalance(5.00);
							System.out.println("Your current balance is $" + balance.getBalance());
							log.println(dateTimeString + " FEED MONEY: " + "$5.00" + " $" + balance.getBalance()); //logging feed money to Log.txt
						} else if (depositAmount == 10) {
							balance.setBalance(10.00);
							System.out.println("Your current balance is $" + balance.getBalance());
							log.println(dateTimeString + " FEED MONEY: " + "$10.00" + " $" + balance.getBalance()); //logging feed money to Log.txt
						} else {
							System.out.println("Please select one of 3 options");
						}

					} else if (purchaseChoice.equals(SECOND_MENU_OPTION_FINISH_TRANSACTION)) {
						System.out.println("\nThank you for shopping with Team 3 Vending Machine");
						break;
					} else if (purchaseChoice.equals(SECOND_MENU_OPTION_SELECT_PRODUCT)) {
						machine.printListOfItems();
						boolean codeExist = false;
						System.out.println("----------------------\n");
						System.out.println("Please enter product's code. Ex A1\n");
						String codeSelected = desiredProduct.nextLine();

						for (int i = 0; i < machine.getListOfItems().size(); i++) {
							if (codeSelected.equals(machine.getListOfItems().get(i).getMenuID())) {
								codeExist = true;
								String productName = machine.getListOfItems().get(i).getName();
								double productCost = machine.getListOfItems().get(i).getCost();
								int productQuantity = machine.getListOfItems().get(i).getQuantity();

								boolean inStock = false;
								if (productQuantity > 0) {
									inStock = true;
								} else {
									System.out.println("Product is out of stock.");
								}
								boolean enoughMoney = false;
								if (productCost <= balance.getBalance()) {
									enoughMoney = true;
									balance.setBalance(-productCost);
									machine.getListOfItems().get(i).setQuantity(machine.getListOfItems().get(i).getQuantity() - 1); //updating product quantity

									String code = machine.getListOfItems().get(i).getMenuID();
									String name = machine.getListOfItems().get(i).getName();
									String cost = Double.toString(machine.getListOfItems().get(i).getCost());
									String type = machine.getListOfItems().get(i).getType();
									log.println(dateTimeString + " " + name + " " + code + " $"  + cost + " $" + balance.getBalance()); //logging purchases to Log.txt

									log.flush();
									log.close();
									if (type.equals("Chip")) {
										System.out.println(name + " cost you a total of " + cost);
										System.out.println("\nYour remaining balance is $" + balance.getBalance());
										System.out.println("\nCrunch Crunch, Yum!");
									} else if (type.equals("Candy")) {
										System.out.println(name + " cost you a total of " + cost);
										System.out.println("\nYour remaining balance is $" + balance.getBalance());
										System.out.println("\nMunch Munch, Yum!");
									} else if (type.equals("Drink")) {
										System.out.println(name + " cost you a total of " + cost);
										System.out.println("\nYour remaining balance is $" + balance.getBalance());
										System.out.println("\nGlug Glug, Yum!");
									} else if (type.equals("Gum")) {
										System.out.println(name + " cost you a total of " + cost);
										System.out.println("\nYour remaining balance is $" + balance.getBalance());
										System.out.println("\nChew Chew, Yum!");
									}
								} else {
									System.out.println("Not enough funds, please Feed Money or select other product:");
								}
							}
						}
						if (codeExist == false) {
							System.out.println("Code invalid, please make other selection.");
						}
					} else {
						System.out.println("Selection invalid, please choose again:");
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}

/*
	Below run
	try {
	File transactionLog = new File("Log.txt);
	if (!transactionLog.exists()) {
		transactionLog.createNewFile();
	}
	PrintWriter logs = new PrintWriter(transactionLog)
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm:ss a");    //to change format
	LocalDateTime dateTime = LocalDateTime.now();
	String dateTimeString = dateTime.format(formatter);		//formatting the current time


 */

