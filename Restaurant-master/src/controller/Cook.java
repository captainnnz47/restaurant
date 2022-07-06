package controller;

import model.Order;
import model.Dish;
import model.Ingredients;
import view.CookView;
import view.TabletView;

import java.util.ArrayList;

/**
 * Cook class is the tool cook use to do work.
 */
public class Cook {
    /**
     * The Ingredients.
     */
    Ingredients ingredients;
    /**
     * The Orders waiting to cook.
     * Note: this is left for phase2, when the time for cooking is considered.
     */
    ArrayList<Order> ordersToCook;
    /**
     * The Tablet view.
     */
    ArrayList<TabletView> tabletViews;
    /**
     * The Cook view.
     */
    CookView cookView;

    ArrayList<String> cookOnSite;

    /**
     * Instantiates a new Cook.
     *
     * @param cookView1   the cook view 1
     * @param ingredients the ingredients
     */
    public Cook(ArrayList<TabletView> tabletViews1, CookView cookView1, Ingredients ingredients){
        ordersToCook = new ArrayList<Order>();
        tabletViews = tabletViews1;
        cookView = cookView1;
        this.ingredients = ingredients;
        cookOnSite = new ArrayList<String>();
    }

    public void addCook(String name) {
        cookOnSite.add(name);
    }

    public void removeCook(String name) {
        cookOnSite.remove(name);
    }

    /**
     * Confirm order.
     *
     * @param order the ordered order that is sent to be comfirmed.
     */
    public void confirmOrder(Order order) {
        ArrayList<String> problemDishes = new ArrayList<String>();
        ArrayList<Dish> dishesToCook = order.getDishes("ordered");
        for (Dish dish : dishesToCook) {
            if (!(confirmDish(dish, order))) {
                dishesToCook.remove(dish);
                problemDishes.add(dish.getDishName());
            }
        }
        if (problemDishes.size() < order.getDishes().size()) {
            order.updateStatus("confirmed");
            ordersToCook.add(order);
        } else {
            order.updateStatus("unavailable");
        }
        if(problemDishes.size() != 0) {
            tabletViews.get(order.getTabletNum()).showProblemOrder(order.getTableNum()[0], problemDishes);
        }
        cookView.viewDishesToCook(dishesToCook, order);
    }

    /**
     * Confirm dish boolean.
     *
     * @param dish  the dish to be checked.
     * @param order the order which contains this dish.
     * @return the boolean if the ingredient for this dish is available.
     */
    public boolean confirmDish(Dish dish, Order order){
        int[] amountsNeed = order.getIngredientAmount(dish);
        ArrayList<String> ingredientsName = dish.getIngredients();
                for (String ingredient: ingredientsName) {
                    if (amountsNeed[ingredientsName.indexOf(ingredient)] > ingredients.getQuantity(ingredient)) {
                        order.updateDishStatus(dish, "unavailable");
                        return false;
                    }else{
                        ingredients.useIngredient(ingredient, amountsNeed[ingredientsName.indexOf(ingredient)]);
                        order.updateDishStatus(dish, "confirmed");
                    }
                }
                return true;
    }

    /**
     * Cook.
     * Note: This is not used for phase1, but left for phase2.
     */
    public void cook(){
        for(Order order: ordersToCook){
            for(Dish dish:order.getDishes()){
                cookDish(dish, order);
            }
            order.updateStatus("prepared");
        }
    }

    /**
     * Cook dish.
     *
     * @param dish  the dish that is sent. Note: Since we need to keep track of every events, in phase1
     *              we directly call this method to cook a specific dish in an order.
     * @param order the order which contains this dish.
     */
    public void cookDish(Dish dish, Order order){
        order.updateDishStatus(dish, "prepared");
        tabletViews.get(order.getTabletNum()).notifyDish(order.getTableNum()[0], dish.getDishName());
        ArrayList<Dish> confirmed = order.getDishes("confirmed");
        if(confirmed.size() == 0){
            order.updateStatus("prepared");
        }
    }

    /**
     * Handle returned dish: to notify the cook with the information of the returned dish and
     *                       new required dish if there is one (new requirement).
     *
     * @param dish  the dish
     * @param order the order
     */
    public void handleReturned (Dish dish, Order order){
        if(confirmDish(dish, order)){
            cookDish(dish, order);
            tabletViews.get(order.getTabletNum()).notifyDish(order.getTableNum()[0], dish.getDishName());
        }

    }

}

