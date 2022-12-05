package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private List<Product> listOfItems;

    public VendingMachine() {

        listOfItems = new ArrayList<>();
        File newFile = new File("vendingmachine.csv");
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(newFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] productLineSplit = line.split("\\|");
                String type = productLineSplit[3];
                String quantity = "5";

                if (type.equals("Chip")) {
                    listOfItems.add(new Chips(productLineSplit[0], productLineSplit[1], productLineSplit[2], productLineSplit[3], quantity));

                } else if (type.equals("Candy")) {
                    listOfItems.add(new Candy(productLineSplit[0], productLineSplit[1], productLineSplit[2], productLineSplit[3], quantity));

                }else if (type.equals("Drink")) {
                    listOfItems.add(new Drinks(productLineSplit[0], productLineSplit[1], productLineSplit[2], productLineSplit[3], quantity));

                }else if (type.equals("Gum")) {
                    listOfItems.add(new Gum(productLineSplit[0], productLineSplit[1], productLineSplit[2], productLineSplit[3], quantity));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Product> getListOfItems() {
        return listOfItems;
    }

    public void printListOfItems() {

        for (int i = 0; i < listOfItems.size(); i++) {
            System.out.println(listOfItems.get(i).getMenuID() + " | " + listOfItems.get(i).getName() + " | " + listOfItems.get(i).getCost() + " | " + listOfItems.get(i).getType() + " | " + listOfItems.get(i).getQuantity());
          }
    }
}
