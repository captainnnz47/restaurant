package view;

import model.Dish;
import model.Dishes;
import model.Order;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel{
    //private String status;
    private Order order;
    private Dishes dishes;
    private JList<String> dishesName;
    private DefaultListModel<String> dish;
    private JLabel dishInfo;
    private JButton delete;
    private JButton comment;
    private JButton returnDish;
    private JButton deliver;
    private JButton confirmOrder;
    private JButton cancelOrder;
    private JButton back;

    public OrderPanel(Dishes dishes){
        this.dishes = dishes;
        setLayout(null);
        //this.order = order;
        dish = new DefaultListModel<String>();
//        for(Dish d: order.getDishes()){
//            dish.addElement(d.getDishName());
//        }
        dishesName = new JList<String>(dish);
        dishesName.setBounds(0,0,400,800);
        dishesName.setFont(new Font("Times New Roman", Font.BOLD, 20));
        dishesName.setSelectedIndex(0);
        add(dishesName);

        dishInfo = new JLabel();
        dishInfo.setBounds(450,0,600,400);
        dishInfo.setFont(new Font("Times New Roman", Font.BOLD, 17));
        add(dishInfo);

        delete = new JButton("delete");
        delete.setBounds(450,450,200,50);
        delete.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(delete);

        comment = new JButton("comment");
        comment.setBounds(700,450,200,50);
        comment.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(comment);

        returnDish = new JButton("return dish");
        returnDish.setBounds(450,525,200,50);
        returnDish.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(returnDish);

        deliver = new JButton("delivered");
        deliver.setBounds(700,525,200,50);
        deliver.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(deliver);

        confirmOrder = new JButton("confirm");
        confirmOrder.setBounds(450,600,200,50);
        confirmOrder.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(confirmOrder);

        cancelOrder = new JButton("cancel order");
        cancelOrder.setBounds(700,600,200,50);
        cancelOrder.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(cancelOrder);

        back = new JButton("back");
        back.setBounds(850,700,75,50);
        back.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(back);

        revalidate();
    }

    public void viewOrder(Order order) {
        this.order = order;
        dish.clear();
        for(Dish d: order.getDishes()){
            dish.addElement(d.getDishName());
        }
        dishesName.setSelectedIndex(0);
        setDishInfo(dishesName.getSelectedValue());
        repaint();
    }

    public void setDishInfo(String name) {
        String info = "<html>" + name + "<br><br>";
        Dish dish = dishes.getDish(name);
        String[] dishIn = order.getDishInfo().get(order.getDishes().indexOf(dish));
        info += "status: " + dishIn[0]+ "<br>";
        info += "ordered by customer: " + dishIn[3] + "<br>";
        info += "comment: " + dishIn[1]==null?"none" : dishIn[1] + "<br><br>";
        info += "Ingredients:<br>";
        for(int i = 0; i < dish.getIngredients().size(); i++) {
            info += dish.getIngredients().get(i);
            info += " * " + order.getIngredientAmount(dish)[0] + "<br>";
        }
        info += "</html>";
        dishInfo.setText(info);
        repaint();
    }

    public void viewOrdering(){
        delete.setEnabled(true);
        comment.setEnabled(true);
        comment.setText("special requirement");
        returnDish.setVisible(false);
        deliver.setVisible(false);
        confirmOrder.setText("confirm order");
        cancelOrder.setEnabled(true);
        repaint();
    }

    public void viewOrdered(){
        delete.setEnabled(true);
        comment.setEnabled(false);
        comment.setText("comment");
        returnDish.setVisible(true);
        returnDish.setEnabled(false);
        deliver.setVisible(true);
        if(order.getDishes("prepared").contains(getSelectedDish()))
            deliver.setEnabled(true);
        else
            deliver.setEnabled(false);
        if(order.getStatus().equals("ordered") || order.getStatus().equals("confirmed")){
            confirmOrder.setEnabled(false);
        }
        else if(order.getStatus().equals("prepared")){
            confirmOrder.setEnabled(true);
            confirmOrder.setText("deliver order");
        }
        else {
            confirmOrder.setEnabled(true);
            confirmOrder.setText("pay bill");
        }
        if(order.getDishes("prepared").size() == 0)
            cancelOrder.setEnabled(true);
        else
            cancelOrder.setEnabled(false);
        repaint();
    }

    public void viewDelivered(){
        delete.setEnabled(false);
        comment.setEnabled(true);
        comment.setText("comment");
        returnDish.setVisible(true);
        returnDish.setEnabled(true);
        deliver.setVisible(true);
        deliver.setEnabled(false);
        confirmOrder.setText("pay bill");
        cancelOrder.setEnabled(false);
        repaint();
    }

    public JList<String> getDishesName() {
        return dishesName;
    }

    public JButton getDelete() {
        return delete;
    }

    public JButton getComment() {
        return comment;
    }

    public JButton getReturnDish() {
        return returnDish;
    }

    public JButton getDeliver() {
        return deliver;
    }

    public JButton getConfirmOrder() {
        return confirmOrder;
    }

    public JButton getCancelOrder() {
        return cancelOrder;
    }

    public JButton getBack() {
        return back;
    }

    public DefaultListModel<String> getDish() {
        return dish;
    }

    public Order getOrder() {
        return order;
    }

    public Dish getSelectedDish() {
        return dishes.getDish(dishesName.getSelectedValue());
    }
}
