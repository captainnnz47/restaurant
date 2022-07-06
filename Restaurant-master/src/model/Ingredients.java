package model;

import com.sun.javafx.iio.ios.IosDescriptor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Ingredients which is used to store and manipulate all the ingredients information.
 */
public class Ingredients {
    /**
     * All the Ingredients in stock.
     */
    private ArrayList<String> ingredients;
    /**
     * The information[current quantity, holds, price] of all the Ingredients in stock.
     */
    private ArrayList<String[]> ingredientsInfo;

    /**
     * Instantiates a new Ingredients for a restaurant.
     */
    public Ingredients() {
        ingredients = new ArrayList<String>();
        ingredientsInfo = new ArrayList<String[]>();
    }

    /**
     * Gets all ingredients in stock.
     *
     * @return the ingredients
     */
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    /**
     * Gets information on current quantity, holds, price of all the Ingredients in stock.
     *
     * @return the ingredients info
     */
    public ArrayList<String[]> getIngredientsInfo() {
        return ingredientsInfo;
    }

    /**
     * Gets quantity of certain ingredient.
     *
     * @param ingredient the ingredient
     * @return the quantity
     */
    public int getQuantity(String ingredient) {
        int index = ingredients.indexOf(ingredient);
        return Integer.parseInt(ingredientsInfo.get(index)[0]);
    }

    /**
     * Gets price of certain ingredient.
     *
     * @param ingredient the ingredient
     * @return the price
     */
    public double getPrice(String ingredient) {
        int index = ingredients.indexOf(ingredient);
        return Double.parseDouble(ingredientsInfo.get(index)[2]);
    }

    /**
     * Check all ingredients to find is there any ingredient in shortage.
     */
    public void checkAllIngredients() {
        try {
            PrintWriter out = new PrintWriter("../../requests.txt");
            for (int i = 0; i < ingredients.size(); i++) {
                String[] info = ingredientsInfo.get(i);
                if (Integer.parseInt(info[0]) < Integer.parseInt(info[1])) {
                    out.println(ingredients.get(i) + " " + 20);
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * Gets all ingredients needed refill.
     *
     * @return ingredients needed refill
     */
    public ArrayList<String> getNeedRefill() {
        ArrayList<String> refillList = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            String[] info = ingredientsInfo.get(i);
            if (Integer.parseInt(info[0]) < Integer.parseInt(info[1])) {
                refillList.add(ingredients.get(i));
            }
        }
        return refillList;
    }

    /**
     * Add a new ingredient to warehouse.
     *
     * @param name   the name of ingredient
     * @param amount the amount of ingredient
     * @param holds  the holds of ingredient
     * @param price  the price of ingredient
     */
//holds = lower bound of ingredient quantity
    //when amount<holds, this ingredient needed to be refilled.
    public void addIngredient(String name, int amount, int holds, double price) {
        ingredients.add(name);
        ingredientsInfo.add(new String[]{Integer.toString(amount), Integer.toString(holds), Double.toString(price)});
    }


    /**
     * Reduce ingredient amount when cook use this ingredient to prepare dishes.
     *
     * @param name   the name of ingredient
     * @param amount the amount used
     */
    public void useIngredient(String name, int amount) {
        String[] info = ingredientsInfo.get(ingredients.indexOf(name));
        info[0] = Integer.toString(Integer.parseInt(info[0]) - amount);
        int hold = Integer.parseInt(info[1]);
        if (getQuantity(name) <= hold) {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("../../requests.txt"));
                out.println(name + " " + 20);
                out.close();
                DateFormat format = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String message = format.format(date) + "  " + name + " of quantity " + amount + " has been used.";
                BufferedWriter log = new BufferedWriter(new FileWriter("log.txt", true));
                log.append(message + "\n");
                log.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Add the refilled amount to total amount when refill ingredient.
     *
     * @param name   the name of ingredient
     * @param amount the amount added
     */
    public void refillIngredient(String name, int amount) {
        try {
            String[] info = ingredientsInfo.get(ingredients.indexOf(name));
            info[0] = Integer.toString(Integer.parseInt(info[0]) + amount);
            DateFormat format = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            String message = format.format(date) + "  " + name + " of quantity " + amount + " has been added to the inventory.";
            BufferedWriter log = new BufferedWriter(new FileWriter("log.txt", true));
            log.append(message + "\n");
            log.close();
        } catch (IOException e) {
        }
    }

    /**
     * Update record.
     *
     * @param name the name
     */
    public void updateRecord(String name) {
        try {
            FileWriter fileWriter = new FileWriter("ingredients.txt");
            PrintWriter ingre = new PrintWriter(fileWriter);
            ingre.print("List of All Ingredients in " + name);
            ingre.println("Name/Quantity/Holds/Price");
            for (String i : ingredients) {
                ingre.println(i);
                String[] info = ingredientsInfo.get(ingredients.indexOf(i));
                ingre.println(info[0]);
                ingre.println(info[1]);
                ingre.println(info[2]);
            }
            ingre.close();
        } catch (IOException e) {
        }
    }
}
