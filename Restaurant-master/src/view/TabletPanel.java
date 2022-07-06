package view;
import model.Tables;
import view.TabletView;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImagingOpException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The type Tablet panel.
 */
public class TabletPanel extends JPanel {

    private ArrayList<JButton> buttonList;

    private JButton newOrder;

    /**
     * Instantiates a new Tablet panel.
     */
    public TabletPanel() {
        setLayout(null);
        newOrder = new JButton("start new order");
        newOrder.setBounds(800, 700, 150, 50);
        buttonList = new ArrayList<JButton>();
        add(newOrder);
        revalidate();
    }

    /**
     * Gets button list.
     */
    public void updateButtonList(Tables tables) {
        buttonList = new ArrayList<JButton>();
        JButton tableButton;
        String status;
        for(int i = 1; i < tables.getTotal()+1; i++) {
            tableButton = new JButton();
            tableButton.setText(Integer.toString(i) + " for " + Integer.toString(tables.getSize(i)));
            tableButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
            buttonList.add(tableButton);
            if(tables.getOrder(i).isEmpty())
                status = "Empty";
            else if(tables.getOrder(i).getStatus().equals("Ordering"))
                status = "Ordering";
            else if(tables.getOrder(i).getStatus().equals("Paid"))
                status = "Paid";
            else
                status = "Ordered";
            changeTableColor(i, status);
        }
        drawTable();
    }

    /**
     * Draw table.
     *
     *
     */
    public void drawTable() {
        int count = 0;
        for (int i = 0; i < (buttonList.size()+3)/4; i++) {
            for (int j = 0; j < 4; j++) {
                if (count < buttonList.size()) {
                    buttonList.get(count).setBounds(50+ j * 250, 50+ i * 100, 150, 50);
                    add(buttonList.get(count));
                    count++;
                }
            }
        }
    }

    /**
     * Change table color.
     *
     * @param tableNum the table num
     * @param status   the status
     */
    public void changeTableColor(int tableNum, String status) {
                if (status.equals("Ordering")) {
                    buttonList.get(tableNum-1).setBackground(new Color(255,255,30,85));
                    buttonList.get(tableNum-1).setEnabled(true);
                }
                else if (status.equals("Ordered")) {
                    buttonList.get(tableNum-1).setBackground(new Color(40,150,255,85));
                    buttonList.get(tableNum-1).setEnabled(true);
                }
                else if (status.equals("Paid")) {
                    buttonList.get(tableNum-1).setBackground(Color.WHITE);
                    buttonList.get(tableNum-1).setEnabled(true);
                }
                else if (status.equals("Empty")) {
                    buttonList.get(tableNum-1).setBackground(Color.LIGHT_GRAY);
                    buttonList.get(tableNum-1).setEnabled(false);
                }
                repaint();
    }



    public ArrayList<JButton> getButtons() {
        return buttonList;
    }

    public JButton getNewOrder() {
        return newOrder;
    }
}
