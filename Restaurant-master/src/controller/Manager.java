package controller;

import model.*;
import view.*;
import controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Manager class is a tool used by the manager to check and order ingredients in stock.
 */
public class Manager implements ActionListener{
     ManagerView managerView;
     ManagerPanel managerPanel;
     Dishes dishes;
     Orders orders;
     Employees employees;
     String name;

    /**
     * Instantiates a new Manager.
     *
     * @param managerView1 the manager view 1
     */
    public Manager(ManagerView managerView1, Dishes dishes1, Orders orders1, Employees employees1) {
        managerView = managerView1;
        dishes = dishes1;
        orders = orders1;
        employees = employees1;

        managerPanel = managerView.getManagerPanel();
        managerPanel.getChangeMenu().addActionListener(this);
        managerPanel.getConfirmViewOrder().addActionListener(this);
        managerPanel.getEmployee().addActionListener(this);
        managerPanel.getChangeTable().addActionListener(this);
        managerPanel.getChangeMenu().addActionListener(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Check ingredients. For manager to see the infomation for all ingredients.
     */
    public void checkIngredients(){
        managerView.giveManagerIngredients();
    }

    //for manager to add new tables to the system.
    public void addTables(int numTable, int size, Tables tables, ArrayList<TabletView> tabletViews){
        int startNum = tables.getTotal() + 1;
        //tables.addTable(numTable, size);
        for(TabletView tabletView: tabletViews){
            //tabletView.drawTable(startNum, numTable, size);
        }
    }

    public void addEmployee(Employees employees, String name, String type, String status, Double workingHours,
                            Double unpaidTips){
        employees.addNewEmployee(name, type, status, workingHours, unpaidTips);
    }

    public void viewKeywordEmployee(Employees employees, String keyword){
        ArrayList<String> keywordPerson = employees.getKeywordPeople(keyword);
        //managerView.giveManagerKeywordPeople(keywordPerson);
    }


    public void callItADay(String restaurantName, Dishes dishes, Ingredients ingredients, Tables tables,
                           Employees employees){
        dishes.updateRecord(restaurantName);
        ingredients.updateRecord(restaurantName);
        tables.updateRecord(restaurantName);
        employees.updateRecord(restaurantName);
    }

    public void checkDishespopularity(String type){
        Dish[] inorder = dishes.getTypePopularity(type);
        ArrayList<String[]> results = new ArrayList<String[]>();
        for(Dish dish: inorder){
            String[] result = new String[2];
            result[0] = dish.getDishName();
            result[1] = Integer.toString(dish.getNumOrdered());
            results.add(result);
        }
        //managerView.giveManagerPopularity(results);
    }

    public void setSpecial(Dish dish, double newPrice){
        dish.setSpecial(newPrice);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //getChangeMenu().addActionListener(this);
        //managerPanel.getConfirmViewOrder().addActionListener(this);
        //managerPanel.getEmployee().addActionListener(this);
        //managerPanel.getChangeTable().addActionListener(this);
        //managerPanel.getChangeMenu().addActionListener(this);
        if(e.getSource().equals(managerPanel.getChangeMenu())){

        }else if(e.getSource().equals(managerPanel.getChangeTable())){

        }else if(e.getSource().equals(managerPanel.getConfirmViewOrder())){

        }else if(e.getSource().equals(managerPanel.getEmployee())){

        }else if(e.getSource().equals(managerPanel.getChangeMenu())){

        }
    }


}
