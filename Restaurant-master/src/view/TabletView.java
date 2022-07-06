package view;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.Order;
import model.Orders;
import model.Tables;
import model.Dish;
import model.Dishes;
import javax.swing.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * TabletView class; The view of the application.
 */
public class TabletView {

    //orders
    Orders orders;

    //dishes
    Dishes dishes;

    //tables
    Tables tables;

    MenuPanel menuPanel;
    OrderPanel orderPanel;
    TabletPanel tabletPanel;

    /**
     * Constructor, Instantiates a new Tablet view.
     *
     * @param dishes the dishes
     * @param orders the orders
     * @param tables the tables
     */
    public TabletView(Dishes dishes, Orders orders, Tables tables) {
        this.dishes = dishes;
        this.orders = orders;
        this.tables = tables;
        menuPanel = new MenuPanel(dishes);
        orderPanel = new OrderPanel(dishes);
        tabletPanel = new TabletPanel();
    }


    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public OrderPanel getOrderPanel() {
        return orderPanel;
    }

    public TabletPanel getTabletPanel() {
        return tabletPanel;
    }

    /**
     * print the message when customers start ordering.
     *
     * @param table table number
     */
    public void startOrder(int table) {
        System.out.println("Table #: " + table + ", new customer started order.");
    }

    /**
     * Display the orders that have already been confirmed.
     *
     * @param table table number
     */
    public void confirmOrder(int table) {
        System.out.println("Table #: " + table + ", order successfully completed.");
        showOrder(table);
    }

    /**
     * Display the orders that have already been canceled.
     *
     * @param table table number
     */
    public void cancelOrder(int table) {
        System.out.println("Table #: " + table + ", order has been canceled.");
    }

    /**
     * Notify server the dishes that are prepared and waiting for delivery.
     *
     * @param table table number
     * @param dish  dish
     */
    public void notifyDish(int table, String dish) {
        System.out.println("Table #: " + table + ", " + dish + " has been prepared, please deliver");
    }

    /**
     * Display the message contains the dishes that has been delivered.
     *
     * @param table table number
     * @param dish  dish
     */
    public void dishDelivered(int table, String dish) {
        System.out.println("Table #: " + table + ", " + dish + " has been delivered.");
    }

    /**
     * Display the dishes that customers want to return include table number, dish name and returned reason.
     *
     * @param table   table number
     * @param dish    dish
     * @param comment reason to return
     * @param request request
     */
    public void dishReturned(int table, String dish, String comment, String request) {
        System.out.println("Table #: " + table + ", " + dish + " is returned, reason: " + comment + ".");
        System.out.println("Customer request: " + request + ".");
    }

    /**
     * Display the dishes that customers ordered, with the table number.
     *
     * @param table   table number
     */
    private void showOrder(int table) {
        System.out.println("Table #: " + table + ", customer has ordered: ");
        for (Dish name: tables.getOrder(table).getDishes()) {
            System.out.println(name.getDishName());
        }
    }

    /**
     * Display the dishes that are unavailable to cook, with table number.
     *
     * @param tableNum      table number
     * @param problemDishes problem dishes
     */
    public void showProblemOrder(int tableNum, ArrayList<String> problemDishes) {
        System.out.println("Table Number: " + tableNum + " has dishes unavailable! Please inform customer.");
        System.out.println("Problem Dishes: ");
        for (String problem: problemDishes) {
            System.out.println(problem);
        }
    }

    /**
     * Display the bill with its table number.
     *
     * @param table table number
     */
    public void showBill(int table) {
        for(String line: tables.getOrder(table).getBill()) {
            System.out.println(line);
        }
    }

    /**
     * Display the message that customers have already paid the bills.
     *
     * @param table table number
     */
    public void billPaid(int table) {
        System.out.println("Table #: " + table + ", customer has paid the bill");
    }

//    public void clearOrder() {
//        order = null;
//        printTabletsView();
//    }

    /**
     * Print the menu for customers to order.
     */
    public void printMenu() {
        for (String type: dishes.getTypes()) {
            System.out.println("Menu" + ":" + "\n" + " " + type + ":" );
            for (Dish d : dishes.getDishOfType(type)) {
                System.out.println("   " + d.getDishName() + ", " + d.getPrice());
                for(int i = 0; i < d.getIngredients().size(); i++) {
                    if(d.getIngredientQuantityRange().get(i)[1] != d.getIngredientQuantityRange().get(i)[2]) {
                        System.out.print("     " + d.getIngredients().get(i) + " -- ");
                        System.out.print("default amount: " + d.getIngredientQuantityRange().get(i)[0] + ", ");
                        System.out.print("min amount: " + d.getIngredientQuantityRange().get(i)[1] + ", ");
                        System.out.println("max amount: " + d.getIngredientQuantityRange().get(i)[2]);
                    }
                    else
                        System.out.println("     " + d.getIngredients().get(i));
                }
            }
        }
    }

    /**
     * Display all the orders with different types.
     */
    public void viewAllOrders() {
        System.out.println("Already Ordered Orders: need to be cooked.");
        for (Order order: orders.getOrders("ordered")) {
            System.out.println("Table Number: " + order.getTableNum());
            for (Dish dish: order.getDishes()) {
                System.out.println(dish.getDishName());
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("Prepared Orders: deliver to customers");
        for (Order order: orders.getOrders("prepared")) {
            System.out.println("Table Number: " + order.getTableNum());
            for (Dish dish: order.getDishes()) {
                System.out.println(dish.getDishName());
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("Problem Orders: need to tell customers.");
        for (Order order: orders.getOrders("problem")) {
            System.out.println("Table Number: " + order.getTableNum());
            for (Dish dish: order.getDishes()) {
                System.out.println(dish.getDishName());
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("Returned Orders: need to get dish back to cook");
        for (Order order: orders.getOrders("returned")) {
            System.out.println("Table Number: " + order.getTableNum());
            for (Dish dish: order.getDishes()) {
                System.out.println(dish.getDishName());
            }
        }
    }
}
