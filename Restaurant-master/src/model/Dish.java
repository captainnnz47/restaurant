package model;

import java.util.*;

/**
 * The type Dish objects represent a dish offering in the restaurant.
 * Dish object always has 5 attributes: dishName,dishType,ingredients,ingredientQuantityRange,price.
 */
public class Dish {
    /**
     * The name of Dish.
     */
    private String dishName;
    /**
     * The type of Dish,refers to beverages,meals,sides,etc.
     */
    private String dishType;
    /**
     * The dish is or not daily special.
     */
    private boolean isSpecial;
    /**
     * The ingredients used for preparing Dish.
     */
    private ArrayList<String> ingredients;
    /**
     * The quantity range [default quantity,min,max] for each ingredients used.
     */
    private ArrayList<int[]> ingredientQuantityRange;
    /**
     * The Price of Dish.
     */
    private double price;
    /**
     * The number of times this Dish has been ordered.
     */
    private int numOrdered;

    /**
     * Instantiates a new Dish.
     *
     * @param name           the dish name
     * @param type           the dish type
     * @param allIngredients all ingredients needed
     * @param range          the possible quantity range of ingredients used in that dish
     * @param p              the dish price
     */
    public Dish(String name, String type, ArrayList<String> allIngredients, ArrayList<int[]> range, double p) {
        dishName = name;
        dishType = type;
        isSpecial = false;
        ingredients = allIngredients;
        ingredientQuantityRange = range;
        price = p;
        numOrdered = 0;
    }

    /**
     * Gets dish type.
     *
     * @return the dish type
     */
    public String getDishType() {
        return dishType;
    }

    /**
     * Gets dish name.
     *
     * @return the dish name
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * Gets ingredient quantity range.
     *
     * @return the ingredient quantity range
     */
    public ArrayList<int[]> getIngredientQuantityRange() {
        return ingredientQuantityRange;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets num ordered.
     *
     * @return the num ordered
     */
    public int getNumOrdered() {
        return numOrdered;
    }


    /**
     * Add ingredient.
     *
     * @param newIngredient the new ingredient added
     * @param newRange      the quantity range for the added ingredient
     */
    public void addIngredient(String newIngredient, int[] newRange) {
        ingredients.add(newIngredient);
        ingredientQuantityRange.add(newRange);
    }

    /**
     * Delete ingredient.
     *
     * @param ingredient the ingredient to be deleted
     */
    public void deleteIngredient(String ingredient) {
        if (ingredients.contains(ingredient)) {
            ingredients.remove(ingredient);
            ingredientQuantityRange.remove(ingredients.indexOf(ingredient));
        }
    }

    /**
     * Change the possible quantity range of certain ingredient used in that dish.
     *
     * @param ingredient the ingredient we want to change quantity
     * @param range      the new range assigned to ingredients
     */
    public void changeRange(String ingredient, int[] range) {
        if (ingredients.contains(ingredient)) {
            ingredientQuantityRange.set(ingredients.indexOf(ingredient), range);
        }
    }

    /**
     * Change price of dish.
     *
     * @param newPrice the new price assigned to dish
     */
    public void changePrice(double newPrice) {
        price = newPrice;
    }

    /**
     * Set dish to be daily special.
     *
     * @param newPrice the new price
     */
    public void setSpecial(double newPrice) {
        isSpecial = true;
        price = newPrice;
    }

    /**
     * Restart popularity record.
     */
    public void restartPopularityRecord() {
        numOrdered = 0;
    }

    /**
     * Being ordered.
     */
    public void beingOrdered() {
        numOrdered += 1;
    }

    /**
     * No less than boolean.
     *
     * @param dish the dish
     * @return the boolean
     */
    public boolean noLessThan(Dish dish) {
        if (this.getNumOrdered() >= dish.getNumOrdered()) {
            return true;
        } else {
            return false;
        }
    }
}
