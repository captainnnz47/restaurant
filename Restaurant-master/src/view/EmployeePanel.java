package view;
import model.Employees;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The type Employee panel.
 */
public class EmployeePanel {

    private String[] columnNames = {"Full Name", "Occupation", "Status", "Wages Information"};
    private Object[][] data;
    private JTable table;
    private ArrayList<String> names;
    private ArrayList<String> occupations;
    private ArrayList<String> status;
    private ArrayList<double[]> wagesInfo;
    private JPanel mainPanel;

    /**
     * Instantiates a new Employee panel.
     */
    public EmployeePanel() {
        table.setBounds(0, 0, 1000, 800);
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1000, 800);
    }

    /**
     * Gets employee data.
     *
     * @param employees the employees
     */
    public void getEmployeeData(Employees employees) {
        names = employees.getNames();
        occupations = employees.getOccupations();
        status = employees.getStatus();
        wagesInfo = employees.getWageInfos();
        data = new Object[names.size()][4];

        for (int i = 0; i < names.size(); i++) {
            data[i][0] = names.get(i);
        }
        for (int j = 0; j < names.size(); j++) {
            data[j][1] = occupations.get(j);
        }
        for (int k = 0; k < status.size(); k++) {
            data[k][2] = status.get(k);
        }
        for (int l = 0; l < wagesInfo.size(); l++) {
            data[l][3] = wagesInfo.get(l);
        }
        table = new JTable(data, columnNames);
        mainPanel.add(table);
    }

}
