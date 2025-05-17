/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystemgse122i;

/**
 *
 * @author farah
 */
public class CustomDate {

    private int day;
    private String month;
    private int year;

    public CustomDate(int day, String month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public boolean chkDate() {
        int numericMonth = getMonthNumber(month);

        if (numericMonth < 1 || numericMonth > 12) return false;

        int maxDays;

        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        switch (numericMonth) {
            case 2:
                maxDays = isLeapYear ? 29 : 28; 
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDays = 30;
                break;
            default:
                maxDays = 31;
                break;
        }

        return day > 0 && day <= maxDays;
    }

    private int getMonthNumber(String monthInput) {
        try {
            return Integer.parseInt(monthInput.trim());
        } catch (NumberFormatException e) {
            String monthLower = monthInput.trim().toLowerCase();
            if (monthLower.equals("january")) return 1;
            if (monthLower.equals("february")) return 2;
            if (monthLower.equals("march")) return 3;
            if (monthLower.equals("april")) return 4;
            if (monthLower.equals("may")) return 5;
            if (monthLower.equals("june")) return 6;
            if (monthLower.equals("july")) return 7;
            if (monthLower.equals("august")) return 8;
            if (monthLower.equals("september")) return 9;
            if (monthLower.equals("october")) return 10;
            if (monthLower.equals("november")) return 11;
            if (monthLower.equals("december")) return 12;
            return -1;
        }
    }

    @Override
    public String toString() {
        return day + " " + month + " " + year;
    }
}