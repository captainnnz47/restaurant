package view;

import model.Dish;
import model.Dishes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MenuPanel extends JPanel{

    private JTabbedPane tabbedMenu;
    private ArrayList<JPanel> categories;

    private ArrayList<JButton> dishNames;
    private Dishes dishes;
    private JPanel addDish;
    private JPanel managerControl;

    private JButton add;
    private JButton subtract;
    private JButton confirm;
    private JButton reset;
    private JButton requirement;
    private JButton viewOrder;
    private JLabel tableNum;
    private JComboBox<String> customer;
    private int table, peopleNum;

    private JButton back;
    private JList<String> ingredientsName;
    private JList<String> ingredientsNum;
    private DefaultListModel<String> ingredients;

    private DefaultListModel<String> amounts;

    private Dish selectedDish;

    public MenuPanel(Dishes dishes) {
        this.dishes = dishes;
        setLayout(null);
        JPanel panel;
        dishNames = new ArrayList<JButton>();
        JButton button;
        tabbedMenu = new JTabbedPane();
        categories = new ArrayList<JPanel>();
        for(String type: dishes.getTypes()){
            panel = new JPanel();
            panel.setLayout(null);
            int index = 0;
            for(Dish dishName: dishes.getDishOfType(type)) {
                button = new JButton(dishName.getDishName());
                dishNames.add(button);
                button.setFont(new Font("Times New Roman",Font.PLAIN, 15));
                button.setBounds(30+230*(index%2),30+80*(index/2),200,50);
                panel.add(button);
                index++;
            }
            categories.add(panel);
            tabbedMenu.addTab(type.toUpperCase(), panel);
        }
        tabbedMenu.setBounds(0,0,500,800);
        tabbedMenu.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(tabbedMenu);

        addDish = new JPanel();
        addDish.setLayout(null);

        ingredients = new DefaultListModel<String>();
        amounts = new DefaultListModel<String>();

        ingredientsName = new JList<String>(ingredients);
        ingredientsName.setBounds(50,50,200,200);
        ingredientsName.setSelectedIndex(0);
        addDish.add(ingredientsName);

        ingredientsNum = new JList<String>(amounts);
        ingredientsNum.setBounds(275,50,75,200);
        ingredientsNum.setEnabled(false);
        addDish.add(ingredientsNum);

        add = new JButton("+");
        add.setBounds(250,300,50,50);
        add.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add.setEnabled(false);
        addDish.add(add);

        subtract = new JButton("-");
        subtract.setBounds(100, 300, 50,50);
        subtract.setFont(new Font("Times New Roman", Font.BOLD, 24));
        subtract.setEnabled(false);
        addDish.add(subtract);

        reset = new JButton("reset ingredients amount");
        reset.setBounds(100,375,200,50);
        reset.setFont(new Font("Times New Roman", Font.BOLD, 15));
        reset.setEnabled(false);
        addDish.add(reset);

        requirement = new JButton("additional requirement");
        requirement.setBounds(100,450,200,50);
        requirement.setFont(new Font("Times New Roman", Font.BOLD, 15));
        requirement.setEnabled(false);
        addDish.add(requirement);

        confirm = new JButton("add dish to order");
        confirm.setBounds(100,525,200,50);
        confirm.setFont(new Font("Times New Roman", Font.BOLD, 15));
        confirm.setEnabled(false);
        addDish.add(confirm);

        viewOrder = new JButton("view current order");
        viewOrder.setBounds(100,600,200,50);
        viewOrder.setFont(new Font("Times New Roman", Font.BOLD, 15));
        viewOrder.setEnabled(false);
        addDish.add(viewOrder);

        back = new JButton("back");
        back.setBounds(350,700,75,50);
        back.setFont(new Font("Times New Roman", Font.BOLD, 15));
        addDish.add(back);

        tableNum = new JLabel();
        tableNum.setBounds(0,0,220,50);
        tableNum.setFont(new Font("Times New Roman", Font.BOLD, 15));
        addDish.add(tableNum);

        customer = new JComboBox<String>();
        customer.setBounds(220,15,50,20);
        customer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        addDish.add(customer);

        addDish.setBounds(550,0,500,800);
        add(addDish);

        revalidate();

        managerControl = new JPanel();
        managerControl.setLayout(null);
        managerControl.setBounds(550,0,500,800);
    }

    public void editDish(String dishName) {
        selectedDish = dishes.getDish(dishName);
        ingredients.clear();
        amounts.clear();
        //ingredients = new DefaultListModel<String>();
        //amounts = new DefaultListModel<String>();
        int index = 0;
        for(String ingredient: selectedDish.getIngredients()) {
            if(selectedDish.getIngredientQuantityRange().get(index)[1] != selectedDish.getIngredientQuantityRange().get(index)[2]) {
                ingredients.addElement(ingredient);
                amounts.addElement(Integer.toString(selectedDish.getIngredientQuantityRange().get(index)[0]));
            }
            index++;
        }
        //ingredientsName.setListData(ingredients);
        ingredientsName.setSelectedIndex(0);
        //ingredientsNum.setListData(amounts.toArray(new String[0]));
        ingredientsNum.setSelectedIndex(0);
        if(amounts.size() > 0) {
            add.setEnabled(true);
            subtract.setEnabled(true);
            confirm.setEnabled(true);
            reset.setEnabled(true);
            requirement.setEnabled(true);
        }
        else{
            add.setEnabled(false);
            subtract.setEnabled(false);
            confirm.setEnabled(true);
            reset.setEnabled(false);
            requirement.setEnabled(false);
        }
        repaint();
    }

    public void startOrder(int num, int numPeople){
        table = num;
        peopleNum = numPeople;
        tableNum.setText("Serving Table # " + num + ", Customer # ");
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        for(int i = 1; i <= numPeople; i++){
            model.addElement(Integer.toString(i));
        }
        customer.setModel(model);
        repaint();
    }

    public int getTable() {
        return table;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public Dishes getDishes() {
        return dishes;
    }

    public JComboBox<String> getCustomer() {
        return customer;
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getSubtract() {
        return subtract;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getRequirement() {
        return requirement;
    }

    public JButton getViewOrder() {
        return viewOrder;
    }

    public JButton getBack() {
        return back;
    }

    public JList<String> getIngredientsName() {
        return ingredientsName;
    }

    public JList<String> getIngredientsNum() {
        return ingredientsNum;
    }

    public ArrayList<JButton> getDishNames() {
        return dishNames;
    }

    public Dish getSelectedDish() {
        return selectedDish;
    }

    public DefaultListModel<String> getIngredients() {
        return ingredients;
    }

    public DefaultListModel<String> getAmounts() {
        return amounts;
    }
}
