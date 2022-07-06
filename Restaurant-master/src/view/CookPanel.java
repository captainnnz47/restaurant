package view;
import model.Orders;
import model.Order;
import model.Dish;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Cook panel.
 */
public class CookPanel extends JPanel {
    private JButton confirm;
    private ArrayList<JCheckBox> checkBoxesList;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JOptionPane confirmPanel;

    /**
     * Instantiates a new Cook panel.
     */
    public CookPanel() {
        setLayout(null);
        confirmPanel = new JOptionPane();
        confirm = new JButton();
        confirm.setText("Confirm Order");
        confirm.setFont(new Font("Times New Roman", Font.BOLD, 15));
        checkBoxesList = new ArrayList<>();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel1.setBounds(0,0,333,400);
        panel2.setBounds(333,0, 333, 400);
        panel3.setBounds(666,0, 333, 400);
        panel4.setBounds(0, 400,333,400);
        panel5.setBounds(333, 400, 333, 400);
        panel6.setBounds(666, 400, 333, 400);
        for (JCheckBox box: checkBoxesList) {
            panel1.add(box);
        }
    }

    /**
     * Display dishes.
     *
     * @param orders the orders
     */
    public void displayDishes(Orders orders) {
        Order order = orders.getOrders("Ordered").get(0);
        for (Dish dish: order.getDishes()) {
            JCheckBox checkbox = new JCheckBox();
            checkbox.setText(dish.getDishName());
            checkbox.setFont(new Font("Times New Roman", Font.BOLD, 15));
            checkBoxesList.add(checkbox);
        }
        for (JCheckBox box: checkBoxesList) {
            panel1.add(box);
        }
    }

    /**
     * Move to new order.
     *
     * @param orders the orders
     */
    public void moveToNewOrder(Orders orders) {
        if(orders.getOrders("Ordered").size() > 1) {
            String one = "<html>";
            Order order = orders.getOrders().get(1);
            ArrayList<Dish> dish1 = order.getDishes();
            for (Dish d: dish1) {
                one += d.getDishName() + "<br>";
            }
            one += "<html>";
            JLabel label1 = new JLabel();
            label1.setText(one);
            label1.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel1.add(label1);
        }

        if(orders.getOrders("Ordered").size() > 2) {
            String two = "<html>";
            Order order = orders.getOrders().get(2);
            ArrayList<Dish> dish2 =  order.getDishes();
            for (Dish d: dish2) {
                two += d.getDishName() + "<br>";
            }
            two += "<html>";
            JLabel label2 = new JLabel();
            label2.setText(two);
            label2.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel2.add(label2);
        }

        if(orders.getOrders("Ordered").size() > 3) {
            String three = "<html>";
            Order order = orders.getOrders().get(3);
            ArrayList<Dish> dish3 =  order.getDishes();
            for (Dish d: dish3) {
                three += d.getDishName() + "<br>";
            }
            three += "<html>";
            JLabel label3 = new JLabel();
            label3.setText(three);
            label3.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel3.add(label3);
        }

        if(orders.getOrders("Ordered").size() > 4) {
            String four = "<html>";
            Order order = orders.getOrders().get(4);
            ArrayList<Dish> dish4 =  order.getDishes();
            for (Dish d: dish4) {
                four += d.getDishName() + "<br>";
            }
            four += "<html>";
            JLabel label4 = new JLabel();
            label4.setText(four);
            label4.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel4.add(label4);
        }

        if(orders.getOrders("Ordered").size() > 5) {
            String five = "<html>";
            Order order = orders.getOrders().get(5);
            ArrayList<Dish> dish5 =  order.getDishes();
            for (Dish d: dish5) {
                five += d.getDishName() + "<br>";
            }
            five += "<html>";
            JLabel label5 = new JLabel();
            label5.setText(five);
            label5.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel5.add(label5);
        }

        if(orders.getOrders("Ordered").size() > 6) {
            String six = "<html>";
            Order order = orders.getOrders().get(6);
            ArrayList<Dish> dish6 =  order.getDishes();
            for (Dish d: dish6) {
                six += d.getDishName() + "<br>";
            }
            six += "<html>";
            JLabel label6 = new JLabel();
            label6.setText(six);
            label6.setFont(new Font("Times New Roman", Font.BOLD, 15));
            panel6.add(label6);
        }

    }

    /**
     * Confirm new order.
     *
     * @param order the order
     */
    public void confirmNewOrder(Order order) {
        String orderName = "<html>";
        ArrayList<String> dish = new ArrayList<>();
        for (Dish d: order.getDishes()) {
            dish.add(d.getDishName());
        }
        for (String name: dish) {
            orderName += name + "<br>";
        }
        orderName += "<html>";
        confirmPanel.showMessageDialog(null, "You have a new Order!" + "<br>" + orderName);
    }
}


