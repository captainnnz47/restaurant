package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The type Tables is mainly used to show server the current activity on all the tables.
 */
public class Tables {

    private ArrayList<Order> tablesInfo;
    private ArrayList<Integer> numOfSeats;
    private int numAvailability;

    /**
     * Instantiates a new Tables.
     */
    public Tables() {
        tablesInfo = new ArrayList<Order>();
        numOfSeats = new ArrayList<Integer>();
        numAvailability = 0;
    }

    /**
     * Add table.
     *
     * @param number the number
     * @param size   the size
     */
    public void addTables(int number, int size) {
        for (int i = 0; i < number; i++) {
            tablesInfo.add(new Order());
            numOfSeats.add(size);
            numAvailability += 1;
        }
    }

    /**
     * Gets total number of tables.
     *
     * @return the total
     */
    public int getTotal() {
        return numOfSeats.size();
    }

    /**
     * Check availability boolean.
     *
     * @param tableNumber the table number
     * @return is the table available or not
     */
    public boolean checkAvailability(int tableNumber) {
        Order order = new Order();
        return tablesInfo.get(tableNumber - 1).equals(order);
    }

    /**
     * Gets available table.
     *
     * @return the available table
     */
    public ArrayList getAvailableTable() {
        ArrayList<Integer> available = new ArrayList<>();
        for (int i = 0; i < available.size(); i++) {
            if (checkAvailability(i + 1)) {
                available.add(i + 1);
            }
        }
        return available;
    }

    /**
     * Gets unavailable table.
     *
     * @return the unavailable table
     */
    public ArrayList getUnavailableTable() {
        ArrayList<Integer> unavailable = new ArrayList<>();
        for (int i = 0; i < unavailable.size(); i++) {
            if (!(checkAvailability(i + 1))) {
                unavailable.add(i + 1);
            }
        }
        return unavailable;
    }

    /**
     * Update table.
     *
     * @param tableNum the table number
     * @param order    the order
     */
    public void updateTable(int[] tableNum, Order order) {
        for (int num : tableNum)
            tablesInfo.set(num - 1, order);
    }

    public int getSize(int tableNum){
        return numOfSeats.get(tableNum-1);
    }

    /**
     * Gets order.
     *
     * @param tableNumber the table number
     * @return the order
     */
    public Order getOrder(int tableNumber) {
        return tablesInfo.get(tableNumber - 1);
    }

    /**
     * Get the number of available numbers.
     *
     * @return the number of available numbers
     */
    public int getNumAvailability() {
        return numAvailability;
    }

    /**
     * Gets tables information.
     *
     * @return the tables information
     */
    public ArrayList<Order> getTablesInfo() {
        return tablesInfo;
    }

    /**
     * Update record.
     *
     * @param name the name
     */
    public void updateRecord(String name) {
        try {
            FileWriter fileWriter = new FileWriter("tables.txt");
            PrintWriter table = new PrintWriter(fileWriter);
            table.print(name);
            table.println("Table Number/Number of Seats");
            for (int n : numOfSeats) {
                table.println(numOfSeats.indexOf(n) + 1);
                table.println(n);
            }
            table.close();
        } catch (IOException e) {
        }
    }

}
