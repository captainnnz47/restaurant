package view;
import model.Ingredients;
import javax.swing.*;

public class ReceiverPanel extends JPanel{
    private DefaultListModel newSupply;
    private DefaultListModel allIngredients;
    private Ingredients ingredients;
    private JList supply;
    private JList ingredientList;
    private JButton confirmSupply;


    public ReceiverPanel() {
        newSupply = new DefaultListModel();

    }

    public void supplyAdded() {
        JScrollPane supplyScrollPane = new JScrollPane();
        supplyScrollPane.setBounds(0, 0, 400, 800);
        add(supplyScrollPane);
        for (String ingredient: ingredients.getNeedRefill()) {
            newSupply.addElement(ingredient);
        }
        supply = new JList(newSupply);
        supply.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        supply.setSelectedIndex(0);
        supply.setVisibleRowCount(10);
        supplyScrollPane = new JScrollPane(supply);

    }

    public void viewIngredients(Ingredients ingredients) {
        JScrollPane ingredientsScrollPane = new JScrollPane();
        ingredientsScrollPane.setBounds(400, 0, 400, 800);
        add(ingredientsScrollPane);
        for (String name: ingredients.getIngredients()) {
            allIngredients.addElement(name);
        }
        ingredientList = new JList(allIngredients);
        ingredientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ingredientList.setSelectedIndex(0);
        ingredientList.setVisibleRowCount(10);
        ingredientsScrollPane = new JScrollPane(ingredientList);

    }

    public void confirmSupply() {
        confirmSupply.setBounds(450, 0, 100, 50);
        add(confirmSupply);
        if (! newSupply.isEmpty()) {
            confirmSupply.setEnabled(true);
            confirmSupply.setVisible(true);
        } else {
            confirmSupply.setEnabled(false);
            confirmSupply.setVisible(false);
        }
    }


}