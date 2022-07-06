package view;
import model.Ingredients;

/**
 * ManagerView class; a class display the information includes the list of ingredients that needed to be
 * refilled, the information of all ingredients to restaurant manager.
 */
public class ManagerView {

    //ingredients.
    Ingredients ingredients;
    ManagerPanel managerPanel;

    /**
     * Constructor, Instantiates a new Manager view.
     *
     * @param ingredients the ingredients
     */
    public ManagerView(Ingredients ingredients) {
        this.ingredients = ingredients;
        managerPanel = new ManagerPanel();
    }

    public ManagerPanel getManagerPanel() {
        return managerPanel;
    }

    /**
     * Show manager the ingredients needed to be  refilled.
     */
    public void printManagerView() {
        System.out.println("Ingredients need to be refilled: ");
        System.out.println(ingredients.getNeedRefill());
    }

    /**
     * Notify the manager from receiver that new supply is added to inventory.
     */
    public void receiverNotifyManager() {
        System.out.println("New supply is added to inventory.");
    }

    /**
     * Display all ingredients' information including name, amount, and price.
     */
    public void giveManagerIngredients() {
        for (String ingredient: ingredients.getIngredients()) {
            int amount = ingredients.getQuantity(ingredient);
            double price = ingredients.getPrice(ingredient);
            System.out.println(ingredient + " " + amount + " " + price);
        }
    }
}
