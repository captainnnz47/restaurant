package controller;

import jdk.nashorn.internal.scripts.JO;
import model.*;
import view.MenuPanel;
import view.OrderPanel;
import view.TabletPanel;
import view.TabletView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * OrderTablet class is controlled by servers to make orders and do other work.
 */
public class OrderTablet implements ActionListener, ListSelectionListener {
    /**
     * The Tables.
     */
//Order order;
    //int table;
    Tables tables;
    Orders orders;
    /**
     * The Tablet view.
     */
    TabletView tabletView;

    MenuPanel menuPanel;
    OrderPanel orderPanel;
    TabletPanel tabletPanel;

    String server;

    int tabletNum;

    private String comment;

    /**
     * Instantiates a new Order tablet.
     *
     * @param view1  the view 1
     * @param tables the tables
     */
    public OrderTablet(TabletView view1, Tables tables, Orders orders, int tabletNum) {
        //order = null;
        tabletView = view1;
        this.tables = tables;
        this.tabletNum = tabletNum;
        this.orders = orders;
        menuPanel = tabletView.getMenuPanel();
        menuPanel.getAdd().addActionListener(this);
        menuPanel.getSubtract().addActionListener(this);
        menuPanel.getReset().addActionListener(this);
        menuPanel.getConfirm().addActionListener(this);
        menuPanel.getIngredientsName().addListSelectionListener(this);
        menuPanel.getIngredientsNum().addListSelectionListener(this);
        menuPanel.getViewOrder().addActionListener(this);
        menuPanel.getRequirement().addActionListener(this);
        menuPanel.getBack().addActionListener(this);
        for (JButton button : menuPanel.getDishNames()) {
            button.addActionListener(this);
        }
        orderPanel = tabletView.getOrderPanel();
        orderPanel.getBack().addActionListener(this);
        orderPanel.getDishesName().addListSelectionListener(this);
        orderPanel.getCancelOrder().addActionListener(this);
        orderPanel.getComment().addActionListener(this);
        orderPanel.getConfirmOrder().addActionListener(this);
        orderPanel.getDelete().addActionListener(this);
        orderPanel.getDeliver().addActionListener(this);
        orderPanel.getReturnDish().addActionListener(this);

        tabletPanel = tabletView.getTabletPanel();
        tabletPanel.getNewOrder().addActionListener(this);
        tabletPanel.updateButtonList(tables);
        for(JButton button: tabletPanel.getButtons()) {
            button.addActionListener(this);
        }
    }

    /**
     * New customer.
     *
     * @param tableNum the table num
     */
    public void newCustomer(int[] tableNum, int numPeople) {
        Order order = new Order(tableNum, numPeople, tabletNum);
        if (tableNum.length != 0)
            tables.updateTable(tableNum, order);
        for (int num : tableNum) {
            tabletView.startOrder(num);
        }
        orders.addOrder(order);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Add dish.
     *
     * @param table             the table
     * @param dishes            the dishes
     * @param name              the name
     * @param ingredientsAmount the ingredients amount
     */
    public void addDish(int table, Dishes dishes, String name, int[] ingredientsAmount, String requirement, int person) {
        Order order = tables.getOrder(table);
        int dishIndex = dishes.getDishesName().indexOf(name);
        Dish dish = dishes.getDishes().get(dishIndex);
        String[] info = new String[dish.getIngredients().size() + 4];
        info[0] = "ordered";
        info[1] = requirement;
        info[2] = Double.toString(dish.getPrice());
        info[3] = Integer.toString(person);
        int i = 0;
        for (int[] quantity : dish.getIngredientQuantityRange()) {
            info[i + 4] = Integer.toString(quantity[0] + ingredientsAmount[i]);
            //System.out.print(info[i+3] + " ");
            i++;
        }
        order.addDish(dish, info);
    }

    /**
     * Cancel dish.
     *
     * @param table the table
     * @param dish  the dish
     */
    public void cancelDish(int table, Dish dish) {
        tables.getOrder(table).updateDishStatus(dish, "canceled");
    }

    /**
     * Finish ordering.
     *
     * @param table the table
     */
    public void finishOrdering(int table) {
        tables.getOrder(table).updateStatus("ordered");
        tabletView.confirmOrder(table);
    }

    /**
     * Deliver dish.
     *
     * @param table the table
     * @param dish  the dish
     */
    public void deliverDish(int table, Dish dish) {
        Order order = tables.getOrder(table);
        order.updateDishStatus(dish, "delivered");
        if (order.getStatus().equals("prepared")) {
            order.updateStatus("delivered");
        }
        tabletView.dishDelivered(table, dish.getDishName());
    }

    /**
     * Deliver order.
     *
     * @param table the table
     */
    public void deliverOrder(int table) {
        Order order = tables.getOrder(table);
        order.updateStatus("delivered");
        for (Dish dish : order.getDishes("prepared"))
            order.updateDishStatus(dish, "delivered");
    }

    /**
     * Return dish.
     *
     * @param table   the table
     * @param dish    the dish
     * @param comment the comment
     * @param request the request
     */
// request: cancel dish, redo dish, new dish: <dish name>
    public void returnDish(int table, Dish dish, String comment, String request) {
        Order order = tables.getOrder(table);
        order.updateDishStatus(dish, "returned");
        order.addComment(dish, comment);
        tabletView.dishReturned(table, dish.getDishName(), comment, request);
    }

    /**
     * Cancel order.
     *
     * @param table the table
     */
    public void cancelOrder(int[] table) {
        for (int num : table) {
            tables.getOrder(num).updateStatus("canceled");
            tabletView.cancelOrder(num);
        }
        tables.updateTable(table, null);
    }

    /**
     * Gets order.
     *
     * @param table the table
     * @return the order
     */
    public Order getOrder(int table) {
        return tables.getOrder(table);
    }

//    public void endOrder(int table) {
//        order = null;
//        tabletView.clearOrder();
//    }

    /**
     * Pay order.
     *
     * @param table the table
     */
    public void payOrder(int[] table) {
        try {
            tabletView.showBill(table[0]);
            Order o = tables.getOrder(table[0]);
            orders.addToTodayTotal(o);
            Double t = o.getBillTotal();
            BufferedWriter revenue = new BufferedWriter(new FileWriter("revenue.txt", true));
            revenue.append(t.toString() + "\n");
            revenue.close();
            for (int num : table) {
                tables.getOrder(num).updateStatus("paid");
                tabletView.billPaid(num);
            }
            tables.updateTable(table, new Order());
        } catch (IOException e) {
        }
    }

    /**
     * Gets tablet view.
     *
     * @return the tablet view
     */
    public TabletView getTabletView() {
        return tabletView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dish dish;
        Order order;
        String name;
        if (menuPanel.getDishNames().contains(e.getSource())) {
            menuPanel.editDish(e.getActionCommand());
        } else if (e.getSource().equals(menuPanel.getAdd())) {
            dish = menuPanel.getSelectedDish();
            String selected = menuPanel.getIngredientsName().getSelectedValue();
            int max = dish.getIngredientQuantityRange().get(dish.getIngredients().indexOf(selected))[2];
            int current = Integer.parseInt(menuPanel.getIngredientsNum().getSelectedValue());
            if (current < max) {
                menuPanel.getAmounts().setElementAt(Integer.toString(current + 1), menuPanel.getIngredientsNum().getSelectedIndex());
            }
            menuPanel.repaint();
        } else if (e.getSource().equals(menuPanel.getSubtract())) {
            dish = menuPanel.getSelectedDish();
            String selected = menuPanel.getIngredientsName().getSelectedValue();
            int min = dish.getIngredientQuantityRange().get(dish.getIngredients().indexOf(selected))[1];
            int current = Integer.parseInt(menuPanel.getIngredientsNum().getSelectedValue());
            if (current > min) {
                menuPanel.getAmounts().setElementAt(Integer.toString(current - 1), menuPanel.getIngredientsNum().getSelectedIndex());
            }
            menuPanel.repaint();
        } else if (e.getSource().equals(menuPanel.getReset())) {
            dish = menuPanel.getSelectedDish();
            int index = 0;
            for (int[] info : dish.getIngredientQuantityRange()) {
                if (info[1] != info[2]) {
                    menuPanel.getAmounts().setElementAt(Integer.toString(info[0]), index);
                    index++;
                }
            }
        } else if (e.getSource().equals(menuPanel.getRequirement())) {
            dish = menuPanel.getSelectedDish();
            comment = JOptionPane.showInputDialog(menuPanel, "Please enter any special requirements:");
            //System.out.println(requirement);
        }
        else if(e.getSource().equals(menuPanel.getConfirm())) {
            menuPanel.getViewOrder().setEnabled(true);
            dish = menuPanel.getSelectedDish();
            int[] info = new int[dish.getIngredients().size()];
            int index = 0;
            for(int i = 0; i < info.length; i++) {
                int[] ingredients = dish.getIngredientQuantityRange().get(i);
                info[i] = ingredients[0];
                if(ingredients[1] != ingredients[2]){
                    info[i] = Integer.parseInt(menuPanel.getIngredientsNum().getModel().getElementAt(index));
                    index++;
                }
            }
            addDish(menuPanel.getTable(), menuPanel.getDishes(), dish.getDishName(), info, comment, menuPanel.getPeopleNum());
            menuPanel.getIngredients().clear();
            ((DefaultListModel<String>) menuPanel.getIngredientsNum().getModel()).clear();
            menuPanel.getAdd().setEnabled(false);
            menuPanel.getSubtract().setEnabled(false);
            menuPanel.getRequirement().setEnabled(false);
            menuPanel.getReset().setEnabled(false);
            menuPanel.getConfirm().setEnabled(false);
        }
        else if(e.getSource().equals(menuPanel.getViewOrder())){
            orderPanel.setBounds(0,0,1000,800);
            Container c = menuPanel.getParent();
            c.remove(menuPanel);
            c.add(orderPanel);
            c.revalidate();
            c.repaint();
//            menuPanel.setEnabled(false);
//            menuPanel.setVisible(false);
//            orderPanel.setVisible(true);
//            orderPanel.setEnabled(true);
            order = tables.getOrder(tabletNum);
            orderPanel.viewOrder(order);
        }
        else if(e.getSource().equals(menuPanel.getBack())){
            tabletPanel.setBounds(0,0,1000,800);
            Container c = menuPanel.getParent();
            c.remove(menuPanel);
            c.add(tabletPanel);
            c.revalidate();
            c.repaint();
        }


        else if (e.getSource().equals(orderPanel.getDelete())) {
            order = orderPanel.getOrder();
            if (order.getStatus().equals("ordering")) {
                order.removeDish(orderPanel.getSelectedDish());
            } else if (order.getStatus().equals("ordered")) {
                cancelDish(order.getTableNum()[0], orderPanel.getSelectedDish());
            }
            if(order.getDishes().size() > 0)
                orderPanel.viewOrder(order);
            else{
                Container c = orderPanel.getParent();
                c.remove(orderPanel);
                c.add(tabletPanel);
                c.revalidate();
                c.repaint();
            }
        } else if (e.getSource().equals(orderPanel.getComment())) {
            order = orderPanel.getOrder();
            String comment = "";
            if (order.getStatus().equals("ordering"))
                comment = JOptionPane.showInputDialog(menuPanel, "Please enter any special requirements:");
            else
                comment += JOptionPane.showInputDialog(menuPanel, "Please enter any comments:");
            order.addComment(orderPanel.getSelectedDish(), comment);
        } else if (e.getSource().equals(orderPanel.getCancelOrder())) {
            order = orderPanel.getOrder();
            if (order.getStatus().equals("ordering")) {
                orders.removeOrder(order);
            } else {
                cancelOrder(order.getTableNum());
            }
            Container c = orderPanel.getParent();
            c.remove(orderPanel);
            c.add(menuPanel);
            c.revalidate();
            c.repaint();
        } else if (e.getSource().equals(orderPanel.getReturnDish())) {
            order = orderPanel.getOrder();
            String choice = "";
            String message = "Please enter the request by number:" + System.getProperty(System.lineSeparator());
            message += "1 - cancel dish" + System.getProperty(System.lineSeparator());
            message += "2 - redo dish" + System.getProperty(System.lineSeparator());
            message += "3 - request new dish" + System.getProperty(System.lineSeparator());
            choice = JOptionPane.showInputDialog(orderPanel, message);
            if (choice.equals("1")) {
                order.updateDishStatus(orderPanel.getSelectedDish(), "canceled");
                if (order.getDishes("ordered").size() == 0 && order.getDishes("prepared").size() == 0)
                    order.updateStatus("delivered");
            } else if (choice.equals("2")) {
                order.updateDishStatus(orderPanel.getSelectedDish(), "ordered");
                if (order.getStatus().equals("delivered"))
                    order.updateStatus("ordered");
            } else if (choice.equals("3")) {
                order.updateDishStatus(orderPanel.getSelectedDish(), "canceled");
            } else {
                JOptionPane.showMessageDialog(orderPanel, "Invalid input!");
            }
        } else if (e.getSource().equals(orderPanel.getDeliver())) {
            order = orderPanel.getOrder();
            deliverDish(order.getTableNum()[0], orderPanel.getSelectedDish());
        } else if (e.getSource().equals(orderPanel.getConfirmOrder())) {
            order = orderPanel.getOrder();
            if (order.getStatus().equals("ordering")) {
                finishOrdering(order.getTableNum()[0]);
                for(int i: order.getTableNum())
                    tabletPanel.changeTableColor(i, "Ordered");
                Container c = orderPanel.getParent();
                c.remove(orderPanel);
                c.add(tabletPanel);
                c.revalidate();
                c.repaint();
            }
            else if (order.getStatus().equals("prepared")) {
                deliverOrder(order.getTableNum()[0]);
            } else if (order.getStatus().equals("delivered")) {
                payOrder(order.getTableNum());
            }
        }
        else if (e.getSource().equals(orderPanel.getBack())) {
            menuPanel.setBounds(0,0,1000,800);
            Container c = orderPanel.getParent();
            c.remove(orderPanel);
            if (orderPanel.getOrder().getStatus().equals("ordering"))
                c.add(menuPanel);
            else
                c.add(tabletPanel);
            c.revalidate();
            c.repaint();
        }

        else if (e.getSource().equals(tabletPanel.getNewOrder())) {
            int numPeople = Integer.parseInt(JOptionPane.showInputDialog(tabletPanel,
                    "New Order! \n Please enter the number of customers:"));
            int numTables = Integer.parseInt(JOptionPane.showInputDialog(tabletPanel,
                    "New Order! \n Please enter the number of tables:"));
            int[] table = new int[numTables];
            for(int i = 0; i < table.length; i++){
                table[i] = Integer.parseInt(JOptionPane.showInputDialog(tabletPanel,
                        "New Order! \n Please enter the table number of table " + (i+1) + ":"));
            }
            order = new Order(table, numPeople, tabletNum);
            tables.updateTable(table, order);
            orders.addOrder(order);
            for(int i: table)
                tabletPanel.changeTableColor(i, "Ordering");
            Container c = tabletPanel.getParent();
            c.remove(tabletPanel);
            c.add(menuPanel);
            menuPanel.startOrder(table[0], numPeople);
            c.revalidate();
            c.repaint();
        }
        else if (tabletPanel.getButtons().contains(e.getSource())){
            Container c = tabletPanel.getParent();
            c.remove(tabletPanel);
            c.add(orderPanel);
            orderPanel.viewOrder(tables.getOrder(tabletPanel.getButtons().indexOf(e.getSource())+1));
            c.revalidate();
            c.repaint();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(menuPanel.getIngredientsName())) {
            menuPanel.getIngredientsNum().setSelectedIndex(menuPanel.getIngredientsName().getSelectedIndex());
        } else if (e.getSource().equals(orderPanel.getDishesName()) &&
                orderPanel.getDishesName().getSelectedIndex() >= 0) {
            orderPanel.setDishInfo(orderPanel.getDishesName().getSelectedValue());
            if (orderPanel.getOrder().getStatus().equals("ordering"))
                orderPanel.viewOrdering();
            else if (orderPanel.getOrder().getDishes("delivered").contains(orderPanel.getSelectedDish()))
                orderPanel.viewDelivered();
            else
                orderPanel.viewOrdered();
        }
    }
}
