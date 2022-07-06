package model;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The type Dishes which manipulates the collections with all the dishes offered in the restaurant.
 */
public class Dishes {
    /**
     * The name list of all the dishes offered in the restaurant.
     */
    private ArrayList<String> dishesName;
    /**
     * The list of all the dishes offered in the restaurant.
     */
    private ArrayList<Dish> dishes;
    /**
     * The list of all the existing dish types in the restaurant.
     */
    private ArrayList<String> types;

    /**
     * Instantiates a new Dishes for a restaurant.
     */
    public Dishes() {
        dishesName = new ArrayList<String>();
        dishes = new ArrayList<Dish>();
        types = new ArrayList<String>();
    }

    /**
     * Add a new dish to existing dishes.
     *
     * @param newDish the new dish
     */
    public void addDish(Dish newDish) {
        dishes.add(newDish);
        dishesName.add(newDish.getDishName());
        if (!types.contains(newDish.getDishType())) {
            types.add(newDish.getDishType());
        }
    }

    /**
     * Delete a dish from available dishes.
     *
     * @param dish the dish to be deleted
     */
    public void deleteDish(Dish dish) {
        dishes.remove(dish);
        dishesName.remove(dish.getDishName());
        String t = dish.getDishType();
        if (getDishOfType(t).size() != 1) {
            types.remove(t);
        }
    }

    /**
     * Gets all the dishes belong to certain type.
     *
     * @param type one dish type
     * @return all the dishes of this type
     */
    public ArrayList<Dish> getDishOfType(String type) {
        ArrayList<Dish> dishesSet = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getDishType().equals(type)) {
                dishesSet.add(d);
            }
        }
        return dishesSet;
    }

    /**
     * Gets dish which matches the string name.
     *
     * @param name the name of dish
     * @return the dish
     */
    public Dish getDish(String name) {
        return dishes.get(dishesName.indexOf(name));
    }

    /**
     * Gets name list of all the dishes offered in the restaurant.
     *
     * @return the dishes name list
     */
    public ArrayList<String> getDishesName() {
        return dishesName;
    }

    /**
     * Gets list of all the dishes offered in the restaurant.
     *
     * @return the dishes list
     */
    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    /**
     * Gets list of all the existing dish types in the restaurant.
     *
     * @return the types list
     */
    public ArrayList<String> getTypes() {
        return types;
    }

    /**
     * Update record.
     *
     * @param name the name
     */
    public void updateRecord(String name) {
        try {
            FileWriter fileWriter = new FileWriter("menu.txt");
            PrintWriter menu = new PrintWriter(fileWriter);
            menu.print("MENU of " + name);
            menu.println("Dish/Type/Price/Ingredients/Ingredients flexible range/Number being ordered");
            for (Dish d : dishes) {
                menu.println(d.getDishName());
                menu.println(d.getDishType());
                menu.println(d.getPrice());
                menu.println(d.getIngredients());
                menu.println(d.getIngredientQuantityRange());
                menu.println(d.getNumOrdered());
            }
            menu.close();
        } catch (IOException e) {
        }
    }

    /**
     * Get type popularity dish [ ].
     *
     * @param type the type
     * @return the dish [ ]
     */
    public Dish[] getTypePopularity(String type) {
        ArrayList<Dish> typeDishes = new ArrayList<Dish>();
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type)) {
                typeDishes.add(dishes.get(i));
            }
        }
        Dish[] result = new Dish[typeDishes.size()];
        result[0] = typeDishes.get(0);
        for (int a = 1; a <= typeDishes.size(); a++) {
            for (int b = 1; b <= a; b++) {
                if (typeDishes.get(a).noLessThan(result[b])) {
                    Dish item = result[b];
                    result[b] = typeDishes.get(a);
                    for (int c = a; c > b; c--) {
                        result[c + 1] = result[c];
                    }
                    result[b + 1] = item;
                    break;
                }
                result[a] = typeDishes.get(a);
            }
        }
        return result;
    }


}
