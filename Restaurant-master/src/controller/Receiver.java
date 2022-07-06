package controller;
import model.Ingredients;

import java.util.ArrayList;
import model.Ingredients;
import java.io.*;
import view.ManagerView;

/**
 * Receiver class is the tool used by receiver to scan and refill ingredients when shipment come.
 * supplementList: to store the info of the coming inventory.
 * ingredients: to access the main record of ingredients.
 */
public class Receiver {
    private ArrayList<String[]> supplementList;
    private Ingredients ingredients;
    private ManagerView managerView;
    private String name;

    /**
     * Instantiates a new Receiver.
     *
     * @param ingredients1 the ingredients 1
     * @param managerView1 the manager view 1
     */
    public Receiver(Ingredients ingredients1, ManagerView managerView1){
        ingredients = ingredients1;
        supplementList = new ArrayList<String[]>();
        managerView = managerView1;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void leave(){
        name = null;
    }

    /**
     * Read supplement. To read from the supply.txt file and add the info in it to <supplementList>,
     *                  which can be directly handled.
     */
    public void readSupplement(){
        try {
            File currDir = new File(".");
            File parentDir = currDir.getParentFile();
            File supply = new File(parentDir,"supply.txt");
            if (!supply.createNewFile()) {
                BufferedReader in = new BufferedReader(new FileReader(supply));
                String line = in.readLine();
                while (line != null) {
                    System.out.println(line);
                    String[] info = line.split(" ");
                    supplementList.add(info);
                    line = in.readLine();
                }
                PrintWriter out = new PrintWriter(supply);
                out.print("");
                out.close();
            }
        }
        catch (IOException e) {}
    }


    /**
     * Add ingredients. Add arrived ingredients to the ingredients record.
     */
    public void addIngredients(){
        for(int i = 0; i < supplementList.size(); i++){
            addIngredient(supplementList.get(i)[0], supplementList.get(i)[1]);
        }
        supplementList = new ArrayList<String[]>();
        //managerView.giveManagerIngredients();
        ingredients.checkAllIngredients();
    }

    /**
     * Add ingredient. A helper method to add amount for one ingredient and print this event.
     *                 (Since in phase1 every event should be recorded)
     *
     * @param ingredientName the ingredient name
     * @param amount         the amount
     */
    public void addIngredient(String ingredientName, String amount){
        int ingredientsIndex = ingredients.getIngredients().indexOf(ingredientName);
        ArrayList<String[]> ingredientsInfo = ingredients.getIngredientsInfo();
        int addQuantity = Integer.parseInt(amount);
        int newQuantity = Integer.parseInt(ingredientsInfo.get(ingredientsIndex)[0]) + addQuantity;
        ingredientsInfo.get(ingredientsIndex)[0] = Integer.toString(newQuantity);
    }

}
