import controller.Manager;
import controller.*;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Restaurant class represents an independent restaurant and has its own menu and staff system.
 */
public class Restaurant extends JFrame implements WindowListener, ActionListener{
    private Dishes dishes;
    private Orders orders;
    private Ingredients ingredients;
    private Tables tables;
    private Employees employees;
    private String restaurantName;
    private ManagerView managerView;
    private ReceiverView receiverView;
    private CookView cookView;
    private ArrayList<TabletView> tabletViews;
    private Manager manager;
    private Cook cook;
    private ArrayList<OrderTablet> orderTablets;
    private Receiver receiver;
    private WelcomePanel welcomePanel;

    /**
     * Instantiates a new Restaurant.
     *
     * @param name the name 1
     */
    public Restaurant(String name) {
        super("Welcome");
        setVisible (true);
        setSize (1000,800);
        setResizable(false);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        requestFocusInWindow();

        restaurantName = name;
        tables = new Tables();
        orders = new Orders();
        dishes = new Dishes();
        ingredients = new Ingredients();
        employees = new Employees();
        managerView = new ManagerView(ingredients);
        receiverView = new ReceiverView(ingredients);
        cookView = new CookView(orders);
        manager = new Manager(managerView, dishes, orders, employees);
        cook = new Cook(tabletViews, cookView, ingredients);
        receiver = new Receiver(ingredients, managerView);
        orderTablets = new ArrayList<OrderTablet>();
        tabletViews = new ArrayList<TabletView>();
        welcomePanel = new WelcomePanel();
        ArrayList<OrderTablet> orderTablets = new ArrayList<OrderTablet>();
        addWindowListener(this);
        revalidate();
    }

    public void newDay() throws IOException{
        setDishes("menu.txt");
        setIngredients("ingredients.txt");
        //menuPanel = new MenuPanel(dishes);
        setEmployees("employees.txt");
        setTables("tables.txt");
        System.out.println(tables.getTablesInfo().size());
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        BufferedWriter log = new BufferedWriter(new FileWriter("log.txt", true));
        log.append(format.format(date)+"\n");
        log.close();
        BufferedWriter ordersRecord = new BufferedWriter(new FileWriter("orders.txt"));
        ordersRecord.append(format.format(date)+"\n");
        ordersRecord.close();
    }

    public void callItADay() throws IOException{
        manager.callItADay(restaurantName, dishes, ingredients, tables, employees);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        BufferedWriter log = new BufferedWriter(new FileWriter("log.txt", true));
        log.append("=========" + format.format(date) + " END========="+"\n");
        log.close();
        BufferedWriter ordersRecord = new BufferedWriter(new FileWriter("orders.txt"));
        double total = orders.getTodayTotal();
        ordersRecord.append("TOTAL INCOME FOR "+ format.format(date) + ": " + Double.toString(total)+"\n");
        ordersRecord.close();
    }

    public void viewMenu() {
        add(tabletViews.get(0).getMenuPanel());
        int[] num = {1};
        orderTablets.get(0).newCustomer(num, 3);
        tabletViews.get(0).getMenuPanel().startOrder(1, 3);
        revalidate();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
//        if(e.get){
//
//        }
    }

    /**
     * Sets dishes.
     *
     * @param menuFile the menu file
     * @throws IOException the io exception
     */
    public void setDishes(String menuFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(menuFile));
        String line = in.readLine(); //restaurant restaurantName
        if (!(line.equals(restaurantName))) {
            return;
        }
        String name = in.readLine();
        while (name != null) {
            String type = in.readLine();
            ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(in.readLine().split(",")));
            String[] info = in.readLine().split(",");
            ArrayList<int[]> ranges = new ArrayList<int[]>();
            int[] range = new int[3];
            for (String amounts : info) {
                String[] temp = amounts.split(" ");
                range[0] = Integer.parseInt(temp[0]);
                range[1] = Integer.parseInt(temp[1]);
                range[2] = Integer.parseInt(temp[2]);
                ranges.add(range);
                range = new int[3];
            }
            double price = Double.parseDouble(in.readLine());
            dishes.addDish(new Dish(name, type, ingredients, ranges, price));
            name = in.readLine();
        }
    }

    /**
     * Sets ingredients with the ingredients file.
     *
     * @param ingredientsFile the ingredients file
     * @throws IOException the io exception
     */
    public void setIngredients(String ingredientsFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(ingredientsFile));
        String line = in.readLine(); //restaurant restaurantName
        if (!(line.equals(restaurantName))) {
            return;
        }
        String name = in.readLine();
        while (name != null) {
            int quantity = Integer.parseInt(in.readLine());
            int holds = Integer.parseInt(in.readLine());
            double price = Double.parseDouble(in.readLine());
            ingredients.addIngredient(name, quantity, holds, price);
            name = in.readLine();
        }
    }

    public void setTables(String tablesFile) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(tablesFile));
        String line = in.readLine(); //restaurant restaurantName and the file title
        if (!(line.equals(restaurantName))) {
            return;
        }
        String infoName = in.readLine(); //table number, table size, which is the title of the infos
        String tableNum = in.readLine();
        while (tableNum != null) {
            int tableSize = Integer.parseInt(in.readLine());
            tables.addTables(1, tableSize);
            tableNum = in.readLine();
        }
    }
    public void setEmployees(String employeesFile) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(employeesFile));
        String line = in.readLine(); //restaurant restaurantName
        if (!(line.equals(restaurantName))) {
            return;
        }
        String infoName = in.readLine(); //info title: "name, occupation, status, working hours, unpaid tips"
        String name = in.readLine();
        while (name != null) {
            String occupation = in.readLine();
            String status = in.readLine();
            double workingHours = Double.parseDouble(in.readLine());
            double unpaidTips = Double.parseDouble(in.readLine());
            employees.addNewEmployee(name, occupation, status, workingHours, unpaidTips);
            name = in.readLine();
        }
    }

    public void enter(){
        if(employees.isEmpty()) {
            employees.addNewEmployee(welcomePanel.getUser(), "Manager", "on", 0., 0.);
            enterManager(welcomePanel.getUser());
        }
        else {
            String userName = welcomePanel.getUser();
            String userType = employees.getPersonType(userName);
            if(userType == null){
                welcomePanel.inputTypo();
                welcomePanel.setUser();
                enter();
            }
            else if (userType.equals("Server")) {
                enterNewTablet(userName);
            } else if (userType.equals("Cook")) {
                enterCook(userName);
            } else if (userType.equals("Manager")) {
                enterManager(userName);
            } else if (userType.equals("Receiver")) {
                enterReceiver(userName);
            } else {
                welcomePanel.inputTypo();
                welcomePanel.setUser();
                enter();
            }
        }
    }


    public void enterNewTablet(String userName) {
        int numTablets = orderTablets.size();
        TabletView tabletView = new TabletView(dishes, orders, tables);
        tabletViews.add(tabletView);
        OrderTablet orderTablet = new OrderTablet(tabletView, tables, orders, numTablets + 1);
        orderTablets.add(orderTablet);
        orderTablet.setServer(userName);
        employees.changeStatus(userName, "on");
        remove(welcomePanel);
        add(tabletView.getTabletPanel());
        revalidate();
        repaint();
    }

    public void enterManager(String userName){
        remove(welcomePanel);
        add(managerView.getManagerPanel());
        revalidate();
        repaint();
        manager.setName(userName);
        employees.changeStatus(userName, "on");
    }

    public void enterCook(String userName){
        remove(welcomePanel);
        add(cookView.getCookPanel());
        revalidate();
        repaint();
        cook.addCook(userName);
        employees.changeStatus(userName, "on");
    }

    public void enterReceiver(String userName){
        remove(welcomePanel);
        add(receiverView.getReceiverPanel());
        revalidate();
        repaint();
        receiver.setName(userName);
        employees.changeStatus(userName, "on");
    }


    /**
     * Get all the orders.
     *
     * @return the orders
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * Get all the dishes.
     *
     * @return the dishes
     */
    public Dishes getDishes() {
        return dishes;
    }

    /**
     * Get restaurant name string.
     *
     * @return the string
     */
    public String getRestaurantName() {
        return restaurantName;
    }

    /**
     * Get all the ingredients.
     *
     * @return the ingredients
     */
    public Ingredients getIngredients() {
        return ingredients;
    }

    /**
     * Get information on tables.
     *
     * @return the tables
     */
    public Tables getTables() {
        return tables;
    }

    /**
     * Gets manager view.
     *
     * @return the manager view
     */
    public ManagerView getManagerView() {
        return managerView;
    }

    /**
     * Gets cook view.
     *
     * @return the cook view
     */
    public CookView getCookView() {
        return cookView;
    }

    /**
     * Gets receiver view.
     *
     * @return the receiver view
     */
    public ReceiverView getReceiverView() {
        return receiverView;
    }

    /**
     * Gets tablet view.
     *
     * @return the tablet view
     */
    public ArrayList<TabletView> getTabletViews() {
        return tabletViews;
    }

    /**
     * Gets order tablet.
     *
     * @return the order tablet
     */
    public ArrayList<OrderTablet> getOrderTablets() {
        return orderTablets;
    }

    /**
     * Gets manager.
     *
     * @return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Gets cook.
     *
     * @return the cook
     */
    public Cook getCook() {
        return cook;
    }

    /**
     * Gets receiver.
     *
     * @return the receiver
     */
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
}

