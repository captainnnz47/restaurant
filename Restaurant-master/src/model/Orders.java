package model;

import java.util.ArrayList;

/**
 * The type Orders manipulates all the active orders.
 */
public class Orders {
    /**
     * The Orders.
     */
    private ArrayList<Order> orders;
    private double todayTotal;

    /**
     * Instantiates a new Orders.
     */
    public Orders() {
        orders = new ArrayList<Order>();
        todayTotal = 0;
    }

    /**
     * Add order.
     *
     * @param order the order
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Remove order.
     *
     * @param order the order
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Gets orders.
     *
     * @param type the type
     * @return the orders
     */
// for server & cook
    public ArrayList<Order> getOrders(String type) {
        ArrayList<Order> typeOrders = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.getStatus().equals(type))
                typeOrders.add(order);
        }
        return typeOrders;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Add to today total.
     *
     * @param order the order
     */
    public void addToTodayTotal(Order order) {
        todayTotal += order.getBillTotal();
    }

    /**
     * Get today total double.
     *
     * @return the double
     */
    public double getTodayTotal() {
        return todayTotal;
    }
}
