package view;
import model.Ingredients;

/**
 * ReceiverView class; a class used for receiver, show receiver the ingredients needed to be refilled.
 */
public class ReceiverView {

    //ingredients.
    Ingredients ingredients;
    ReceiverPanel receiverPanel;

    /**
     * constructor, Instantiates a new Receiver view.
     *
     * @param ingredients the ingredients
     */
    public ReceiverView(Ingredients ingredients) {
        this.ingredients = ingredients;
        receiverPanel = new ReceiverPanel();
    }

    public ReceiverPanel getReceiverPanel() {
        return receiverPanel;
    }

    /**
     * Notify the receiver that the supply has arrived.
     */
    public void supplyNotify() {
        System.out.println("New supply waiting to be handled.");
    }

    /**
     * Print the list of ingredients that needed to be refilled.
     */
    public void printReceiverView() {
        System.out.println("Ingredients needed to be refilled: ");
        for (String refill: ingredients.getNeedRefill()) {
            System.out.println(refill);
        }
    }
}
