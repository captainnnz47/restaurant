package view;
import javax.swing.*;
import model.Orders;
import model.Order;
import model.Dish;
import java.awt.*;
import java.util.ArrayList;

public class ManagerPanel extends JPanel{
    private JButton employee;
    private JButton viewOrder;
    private JButton changeMenu;
    private JButton changeTable;
    private JButton confirmViewOrder;
    private JPanel orderView;
    private JPanel menuView;
    private JPanel tableView;
    private DefaultListModel OrderHistory;
    private DefaultListModel OrderInProgress;

    public ManagerPanel() {
        setLayout(null);
        employee = new JButton("View Employees");
        employee.setBounds(100, 100, 350, 250);
        add(employee);
        viewOrder = new JButton("View Orders");
        viewOrder.setBounds(100, 450, 350, 250);
        add(viewOrder);
        changeMenu = new JButton("Change Menu");
        changeMenu.setBounds(550, 100, 350, 250);
        add(changeMenu);
        changeTable = new JButton("Change Table");
        changeTable.setBounds(550, 450, 350, 250);
        add(changeTable);
        confirmViewOrder = new JButton();
        confirmViewOrder.setBounds(850, 700, 100, 50);
        add(confirmViewOrder);

        orderView = new JPanel();
        orderView.setVisible(false);
        orderView.setBounds(0, 0, 1000, 800);
        add(orderView);

        menuView = new JPanel();
        menuView.setVisible(false);
        menuView.setBounds(0, 0, 1000, 800);
        add(menuView);

        tableView = new JPanel();
        tableView.setVisible(false);
        tableView.setBounds(0, 0, 1000, 800);
        add(tableView);
    }

    public JButton getChangeMenu() {
        return changeMenu;
    }

    public JButton getConfirmViewOrder() {
        return confirmViewOrder;
    }

    public JButton getEmployee() {
        return employee;
    }

    public JButton getViewOrder() {
        return viewOrder;
    }

    public JButton getChangeTable() {
        return changeTable;
    }

    public JPanel getMenuView() {
        return menuView;
    }

    public JPanel getOrderView() {
        return orderView;
    }

    public JPanel getTableView() {
        return tableView;
    }


    public void viewEmployee() {
        employee.setVisible(false);
        employee.setEnabled(false);
        viewOrder.setEnabled(false);
        viewOrder.setVisible(false);
        changeTable.setVisible(false);
        changeTable.setEnabled(false);
        changeMenu.setEnabled(false);
        changeMenu.setVisible(false);
        confirmViewOrder.setVisible(false);

    }

    public void viewPastOrders(Orders orders) {
        employee.setVisible(false);
        employee.setEnabled(false);
        viewOrder.setEnabled(false);
        viewOrder.setVisible(false);
        changeTable.setVisible(false);
        changeTable.setEnabled(false);
        changeMenu.setEnabled(false);
        changeMenu.setVisible(false);
        orderView.setVisible(true);
        confirmViewOrder.setVisible(true);

        JScrollPane historyScrollPane = new JScrollPane();
        historyScrollPane.setBounds(0, 200, 500, 600);
        orderView.add(historyScrollPane);
        historyScrollPane.add(confirmViewOrder);
        String[] years = new String[] {"2018", "2017", "2016"};
        JComboBox<String> year = new JComboBox<>(years);
        historyScrollPane.add(year);
        year.setBounds(0, 0, 100, 50);
        String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        JComboBox<String> month = new JComboBox<>(months);
        historyScrollPane.add(month);
        month.setBounds(100, 0, 100, 50);
        String[] days = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        JComboBox<String> day = new JComboBox<>(days);
        historyScrollPane.add(day);
        day.setBounds(200, 0, 100, 50);
    }

    public void viewOrderInProgress(Orders orders) {
        employee.setVisible(false);
        employee.setEnabled(false);
        viewOrder.setEnabled(false);
        viewOrder.setVisible(false);
        changeTable.setVisible(false);
        changeTable.setEnabled(false);
        changeMenu.setEnabled(false);
        changeMenu.setVisible(false);
        orderView.setVisible(true);
        confirmViewOrder.setVisible(false);

        JLabel label1 = new JLabel();
        label1.setBounds(500, 200, 500, 600);
        orderView.add(label1);
        String orderDetail = "<html>";
        for (int i = 0; i < orders.getOrders("Ordered").size(); i++) {
            Order order = orders.getOrders().get(i);
            ArrayList<Dish> dish1 = order.getDishes();
            for (Dish d: dish1) {
                orderDetail += d.getDishName() + "<br>";
            }
            //if (order/////////////////)
            orderDetail += "Order " + Integer.toString(i + 1) + "<br>";
        }
        orderDetail += "<html>";
        label1.setText(orderDetail);
        label1.setFont(new Font("Times New Roman", Font.BOLD, 15));
    }

    public String getPopularityType() {
        String type = JOptionPane.showInputDialog(menuView, "Please enter type to check popularity.");
        return type;
    }

    public void giveManagerPopularity(Dish[] result) {
        JLabel popularityList = new JLabel();
        popularityList.setBounds(600, 450, 300, 300);
        menuView.add(popularityList);
        String popularity = "<html>";
        for (Dish d: result) {
            popularity += d.getDishName() + "<br>";
        }
        popularity += "<html>";
        popularityList.setText(popularity);
        popularityList.setFont(new Font("Times New Roman", Font.BOLD, 15));
    }

    public String managerLogout() {
        String managerName = JOptionPane.showInputDialog(this, "Please enter your name to log out.");
        return managerName;
    }

}
