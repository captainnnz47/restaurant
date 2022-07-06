package view;
import model.Dish;
import model.Orders;
import model.Order;
import java.util.ArrayList;

/**
 * CookView class; a class used for cooks. Cooks can view information such as costumers confirm orders, dishes need to
 * be cooked, or dishes needed redo.
 */
public class CookView {

    //orders.
    Orders orders;

    CookPanel cookPanel;

    /**
     * Constructor, Instantiates a new Cook view.
     *
     * @param orders the orders
     */
    public CookView(Orders orders) {
        this.orders = orders;
        cookPanel = new CookPanel();
    }

    public CookPanel getCookPanel() {
        return cookPanel;
    }

    /**
     * Show cook the orders and table numbers that already be confirmed by customers.
     *
     * @param order the order
     */
    public void tabletConfirmOrder(Order order) {
        System.out.println(order.getTableNum() + " has ordered.");
    }

    /**
     * Show cook the dishes that waiting for cooking.
     *
     * @param dish  the dish
     * @param order the order
     */
    public void viewDishesToCook(ArrayList<Dish> dish, Order order) {
        System.out.println("Table Number: " + order.getTableNum());
        if (dish.size() == 0) {
            System.out.println("Lack of ingredients for this order.");
        } else {
            System.out.println("Dishes to cook");
            for (Dish d: dish) {
                System.out.println(d.getDishName());
                int[] amountList = order.getIngredientAmount(d);
                for (String ingredient: d.getIngredients()) {
                    System.out.println(ingredient + ":");
                    System.out.println(amountList[d.getIngredients().indexOf(ingredient)]);
                }
            }
        }
    }

    /**
     * Show cook the dishes with the status "Ordered".
     */
    public void printCookView() {
        for (Order order: orders.getOrders("Ordered")) {
            System.out.println("Order：");
            System.out.println("Table number: " + order.getTableNum());
            for (Dish dish: order.getDishes()) {
                System.out.println(dish.getDishName());
            }
        }
    }

    /**
     * Show cook the dishes that customers ask the restaurant to redo or recook.
     *
     * @param oldDishName name of old dish
     * @param newDish     new dish
     * @param amountInfo  amount info
     */
    public void notifyRedoDish(String oldDishName, Dish newDish, int[] amountInfo){
        System.out.println(oldDishName + "is returned. Please cook new dish:" + newDish.getDishName());
        for (String ingredient: newDish.getIngredients()) {
                    System.out.println(ingredient + ":");
                    System.out.println(amountInfo[newDish.getIngredients().indexOf(ingredient)]);
                }
    }

}
