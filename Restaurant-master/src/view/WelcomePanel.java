package view;
import javax.swing.*;
import java.awt.*;

/**
 * The type Welcome panel.
 */
public class WelcomePanel extends JPanel {

    private JLabel sentence;
    private JButton login;
    private String user;

    /**
     * Instantiates a new Welcome panel.
     */
    public WelcomePanel() {
        user = JOptionPane.showInputDialog(this.getParent(), "Welcome! Please type your name to login.");
        login = new JButton();
        login.setBounds(400, 600, 200, 50);
        sentence = new JLabel();
    }

    /**
     * get user.
     */
    public String getUser() {
        return user;
    }

    public void setUser() {
        user = JOptionPane.showInputDialog(this.getParent(), "Welcome! Please type your name to login.");
    }

    /**
     * input recheck.
     */
    public void inputTypo() {
        JOptionPane.showMessageDialog(this,
                "Sorry, please check you enter the right name.",
                "Inane warning",
                JOptionPane.WARNING_MESSAGE);
    }
}
