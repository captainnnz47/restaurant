package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The type Employees.
 */
// for infos, String[type, status]
// for wageInfos, int[number of hours, unpaid tips]
public class Employees {
    private ArrayList<String> names;
    private ArrayList<String> occupations;
    private ArrayList<String> status;
    private ArrayList<double[]> wageInfos;

    /**
     * Instantiates a new Employees.
     */
    public Employees() {
        names = new ArrayList<String>();
        occupations = new ArrayList<String>();
        status = new ArrayList<String>();
        wageInfos = new ArrayList<double[]>();
    }

    public boolean isEmpty(){
        return names.isEmpty();
    }

    /**
     * Add new employee.
     *
     * @param name         the name
     * @param type         the type
     * @param statu        the statu
     * @param workingHours the working hours
     * @param unpaidTips   the unpaid tips
     */
    public void addNewEmployee(String name, String type, String statu, Double workingHours, Double unpaidTips) {
        names.add(name);
        occupations.add(type);
        status.add(statu);
        double[] wageInfo = new double[2];
        wageInfo[0] = workingHours;
        wageInfo[1] = unpaidTips;
        wageInfos.add(wageInfo);
    }

    /**
     * Change status.
     *
     * @param name      the name
     * @param newStatus the new status
     */
    public void changeStatus(String name, String newStatus) {
        status.set(names.indexOf(name), newStatus);
    }

    /**
     * Change working time.
     *
     * @param name        the name
     * @param workingTime the working time
     */
    public void changeWorkingTime(String name, double workingTime) {
        if(names.contains(name)) {
            wageInfos.get(names.indexOf(name))[0] += workingTime;
        }
    }

    /**
     * Change total time.
     *
     * @param name the name
     * @param tips the tips
     */
    public void changeTotalTime(String name, double tips) {
        if(names.contains(name)) {
            wageInfos.get(names.indexOf(name))[1] += tips;
        }
    }

    /**
     * Gets names.
     *
     * @return the names
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Gets occupations.
     *
     * @return the occupations
     */
    public ArrayList<String> getOccupations() {
        return occupations;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public ArrayList<String> getStatus() {
        return status;
    }

    /**
     * Gets wage infos.
     *
     * @return the wage infos
     */
    public ArrayList<double[]> getWageInfos() {
        return wageInfos;
    }

    /**
     * Gets keyword people.
     *
     * @param keyword the keyword
     * @return the keyword people
     */
    public ArrayList<String> getKeywordPeople(String keyword) {
        ArrayList<String> result = new ArrayList<String>();
        if (isType(keyword)) {
            for (int i = 0; i < names.size(); i++) {
                if (occupations.get(i).equals(keyword)) {
                    result.add(names.get(i));
                }
            }
        } else if (isStatus(keyword)) {
            for (int j = 0; j < names.size(); j++) {
                if (status.get(j).equals(keyword)) {
                    result.add(names.get(j));
                }
            }
        }
        return result;
    }

    /**
     * Get person info string [ ].
     *
     * @param name the name
     * @return the string [ ]
     */
    public String[] getPersonInfo(String name) {
        if(names.contains(name)) {
            String[] result = new String[5];
            result[0] = name;
            result[1] = occupations.get(names.indexOf(name));
            result[2] = status.get(names.indexOf(name));
            double[] wagesinfo = wageInfos.get(names.indexOf(name));
            result[3] = Double.toString(wagesinfo[0]);
            result[4] = Double.toString(wagesinfo[1]);
            return result;
        }else{
            return null;
        }
    }

    public String getPersonType(String name){
        if(names.contains(name)) {
            return occupations.get(names.indexOf(name));
        }else{
            return null;
        }
    }

    /**
     * Is type boolean.
     *
     * @param keyword the keyword
     * @return the boolean
     */
    public boolean isType(String keyword) {
        if (occupations.contains(keyword)) {
            return true;
        }
        return false;
    }

    /**
     * Is status boolean.
     *
     * @param keyword the keyword
     * @return the boolean
     */
    public boolean isStatus(String keyword) {
        if (status.contains(keyword)) {
            return true;
        }
        return false;
    }

    /**
     * Update record.
     *
     * @param restaurantName the restaurant name
     */
    public void updateRecord(String restaurantName) {
        try {
            FileWriter fileWriter = new FileWriter("menu.txt");
            PrintWriter menu = new PrintWriter(fileWriter);
            menu.print("employees in " + restaurantName);
            menu.println("Name/Occupation/Status/Working hour/Unpaid tips");
            for (String name : names) {
                String[] info = getPersonInfo(name);
                menu.println(info[0]);
                menu.println(info[1]);
                menu.println(info[2]);
                menu.println(info[3]);
                menu.println(info[4]);
            }
            menu.close();
        } catch (IOException e) {
        }
    }
}
