package model;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Order which each represent an independent order.
 */
public class Order {
    /**
     * The Table num.
     */
    private int[] tableNum; // if take out, # == 0

    /**
     * The Tablet num.
     */
    private int tabletNum;

    /**
     * The Num people.
     */
    private int numPeople;
    /**
     * The Dishes.
     */
    private ArrayList<Dish> dishes;
    /**
     * The Dishes info.
     */
// dish status: ordered, prepared, delivered, canceled, unavailable
    private ArrayList<String[]> dishesInfo; // [status, comment, price, person, #, #, # ...]
    /**
     * The Status.
     */
    private String status = "";
    // waiting, ordering, ordered, confirmed(cook seen), prepared(cook done), unavailable (cook can't do), delivered, paid
    /**
     * The Total.
     */
    private double total;

    /**
     * Instantiates a new Order.
     */
    public Order() {
        dishes = new ArrayList<Dish>();
        dishesInfo = new ArrayList<String[]>();
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return status.equals("");
    }

    /**
     * Instantiates a new Order.
     *
     * @param table     the table
     * @param num       the num
     * @param tabletNum the tablet num
     */
    public Order(int[] table, int num, int tabletNum) {
        tableNum = table;
        this.tabletNum = tabletNum;
        numPeople = num;
        dishes = new ArrayList<Dish>();
        dishesInfo = new ArrayList<String[]>();
        status = "ordering";
    }

    /**
     * Gets tablet num.
     *
     * @return the tablet num
     */
    public int getTabletNum() {
        return tabletNum;
    }

    /**
     * Gets dish info.
     *
     * @return the dish info
     */
    public ArrayList<String[]> getDishInfo() {
        return dishesInfo;
    }

    /**
     * Get dish info string [ ].
     *
     * @param d the d
     * @return the string [ ]
     */
    public String[] getDishInfo(Dish d) {
        return dishesInfo.get(dishes.indexOf(d));
    }

    /**
     * Gets dishes.
     *
     * @return the dishes
     */
    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    /**
     * Gets dishes.
     *
     * @param status the status
     * @return the dishes
     */
    public ArrayList<Dish> getDishes(String status) {
        ArrayList<Dish> dishList = new ArrayList<Dish>();
        for (Dish dish : dishes) {
            if (dishesInfo.get(dishes.indexOf(dish))[0].equals(status))
                dishList.add(dish);
        }
        return dishList;
    }

    /**
     * Gets table num.
     *
     * @return the table num
     */
    public int[] getTableNum() {
        return tableNum;
    }

    /**
     * Get ingredient amount int [ ].
     *
     * @param dish the dish
     * @return the int [ ]
     */
    public int[] getIngredientAmount(Dish dish) {
        String[] info = dishesInfo.get(dishes.indexOf(dish));
        int[] amount = new int[info.length - 4];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = Integer.parseInt(info[i + 4]);
            //System.out.print(amount[i]+" ");
        }
        return amount;
    }

    /**
     * Add dish.
     *
     * @param dish the dish
     * @param info the info
     */
    public void addDish(Dish dish, String[] info) {
        dishes.add(dish);
        dish.beingOrdered();
        dishesInfo.add(info);
    }

    /**
     * Remove dish.
     *
     * @param dish the dish
     */
    public void removeDish(Dish dish) {
        dishesInfo.remove(dishes.indexOf(dish));
        dishes.remove(dish);
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Update table.
     *
     * @param table the table
     */
    public void updateTable(int[] table) {
        tableNum = table;
    }

    /**
     * Update status.
     *
     * @param status the status
     */
    public void updateStatus(String status) {
        this.status = status;
    }

    /**
     * Gets num people.
     *
     * @return the num people
     */
    public int getNumPeople() {
        return numPeople;
    }

    /**
     * Update dish status. Record the change in log.txt.
     *
     * @param dish   the dish
     * @param status the status
     */
    public void updateDishStatus(Dish dish, String status) {
//        String[] info = dishesInfo.get(dishes.indexOf(dish));
//        info[0] = status;
//        dishesInfo.set(dishes.indexOf(dish), info);
        try {
            dishesInfo.get(dishes.indexOf(dish))[0] = status;
            DateFormat format = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            String message = format.format(date) + "  The following dish has been" + status + ":" + dish.getDishName();
            BufferedWriter log = new BufferedWriter(new FileWriter("log.txt", true));
            log.append(message + System.getProperty(System.lineSeparator()));
            log.close();
        } catch (IOException e) {
        }
    }

    /**
     * Add comment.
     *
     * @param dish    the dish
     * @param comment the comment
     */
    public void addComment(Dish dish, String comment) {
        String[] info = dishesInfo.get(dishes.indexOf(dish));
        info[1] += comment;
        dishesInfo.set(dishes.indexOf(dish), info);
    }

    /**
     * Get the bill total.
     *
     * @return the bill total
     */
    public double getBillTotal() {
        double total = 0;
        for (int x = 0; x < dishes.size(); x++) {
            if (dishesInfo.get(x)[0].equals("delivered")) {
                total += Double.parseDouble(dishesInfo.get(x)[2]);
            }
        }
        double tax = total * 0.13;
        double tip = 0;
        if (numPeople >= 8) {
            tip = total * 0.15;
        }
        total += tax + tip;
        return total;
    }

    /**
     * Gets bill.
     *
     * @return the bill
     */
    public ArrayList<String> getBill() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        ArrayList<String> bill = new ArrayList<String>();
        bill.add("Restaurant");
        bill.add(format.format(date));
        bill.add("table #: " + tableNum[0]);
        total = 0;
        for (int x = 0; x < dishes.size(); x++) {
            if (dishesInfo.get(x)[0].equals("delivered")) {
                bill.add(dishes.get(x).getDishName() + ":     $" + dishesInfo.get(x)[2]);
                total += Double.parseDouble(dishesInfo.get(x)[2]);
            }
        }
        bill.add("Total:          $" + total);
        double tax = total * 0.13;
        bill.add("HST:            $" + tax);
        double tip = 0;
        if (numPeople >= 8) {
            tip = total * 0.15;
            bill.add("Tip:            $" + tip);
        }
        total += tax + tip;
        bill.add("Bill:           $" + total);
        return bill;
    }

    /**
     * Split bill string [ ].
     *
     * @return the string [ ]
     */
    public ArrayList<String> splitBillEvenly() {
        ArrayList<String> bill = getBill();
        bill.remove(bill.size() - 1);
        bill.add("Split by: " + numPeople + " people.");
        bill.add("Bill:           $" + Math.round((total / (double) numPeople) * 100) / 100);
        return bill;
    }

    /**
     * Split bill array list.
     *
     * @param personNum the person num
     * @return the array list
     */
    public ArrayList<String> splitBill(int personNum) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        ArrayList<String> bill = new ArrayList<String>();
        bill.add("Restaurant");
        bill.add(format.format(date));
        bill.add("table #: " + tableNum[0]);
        total = 0;
        for (int x = 0; x < dishes.size(); x++) {
            if (dishesInfo.get(x)[0].equals("delivered") && Integer.parseInt(dishesInfo.get(x)[3]) == personNum) {
                bill.add(dishes.get(x).getDishName() + ":     $" + dishesInfo.get(x)[2]);
                total += Double.parseDouble(dishesInfo.get(x)[2]);
            }
        }
        bill.add("Total:          $" + total);
        double tax = total * 0.13;
        bill.add("HST:            $" + tax);
        double tip = 0;
        if (numPeople >= 8) {
            tip = total * 0.15;
            bill.add("Tip:            $" + tip);
        }
        total += tax + tip;
        bill.add("Bill:           $" + total);
        return bill;
    }
}
