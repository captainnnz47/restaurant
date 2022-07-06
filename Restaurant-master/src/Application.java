import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Application class offer plentiful functions to manipulate a restaurant,
 * and help servers,cooks,managers,receivers to do their job.
 */
public class Application {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        File parentDir = new File(".").getParentFile();
        File newFile = new File(parentDir, "events.txt");
        newFile.createNewFile();
        newFile = new File(parentDir, "menu.txt");
        newFile.createNewFile();
        newFile = new File(parentDir, "ingredients.txt");
        newFile.createNewFile();
        newFile = new File(parentDir, "log.txt");
        newFile.createNewFile();

        Restaurant r = new Restaurant("Restaurant 207");

        BufferedReader in = new BufferedReader(new FileReader("events.txt"));
//        String restaurant = in.readLine();
//        Restaurant r = new Restaurant(restaurant, 5);
        r.newDay();
        r.enter();
        r.revalidate();
//        String line = in.readLine();
//        while (line != null) {
//            String[] info = line.split(",");
//            if (info[0].contains("Table")) {
//                String[] temp = info[0].split(" ");
//                int table = Integer.parseInt(temp[1]);
//                if (info[1].equals("order")) {
//                    if (r.getTables().getOrder(table).isEmpty()) {
//                        r.getOrderTablet().newCustomer(table);
//                    }
//                    int[] ingredients = new int[info.length - 3];
//                    for (int i = 3; i < info.length; i++) {
//                        ingredients[i - 3] = Integer.parseInt(info[i]);
//                    }
//                    r.getOrderTablet().addDish(table, r.getDishes(), info[2], ingredients);
//                    //System.out.println("customer added dish " + info[2]);
//                } else if (info[1].equals("ordered")) {
//                    r.getOrderTablet().finishOrdering(table);
//                } else if (info[1].equals("paid")) {
//                    r.getOrderTablet().payOrder(table);
//                } else if (info[1].equals("returned")) {
//                    r.getOrderTablet().returnDish(table, r.getDishes().getDish(info[2]), info[3], info[4]);
//                }
//            } else if (info[0].equals("Cook")) {
//                int table = Integer.parseInt(info[2]);
//                if (info[1].equals("confirmed")) {
//                    r.getCook().confirmOrder(r.getTables().getOrder(table));
//                } else {
//                    r.getCook().cookDish(r.getDishes().getDish(info[3]), r.getTables().getOrder(table));
//                }
//            } else if (info[0].equals("Server")) {
//                int table = Integer.parseInt(info[2]);
//                if (info[1].equals("delivered")) {
//                    if (info.length == 3)
//                        r.getOrderTablet().deliverOrder(table);
//                    else
//                        r.getOrderTablet().deliverDish(table, r.getDishes().getDish(info[3]));
//                }
//            } else {
//                if (info[1].equals("arrived")) {
//                    r.getReceiver().readSupplement();
//                    r.getReceiverView().supplyNotify();
//                } else {
//                    r.getReceiver().addIngredients();
//                    System.out.println("Ingredients successfully added to inventory.");
//                }
//            }
//            line = in.readLine();
//        }
//        System.out.println();
//        System.out.println("Current Ingredients:");
//        r.getManagerView().giveManagerIngredients();
    }
}
