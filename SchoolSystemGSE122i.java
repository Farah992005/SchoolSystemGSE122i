/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolsystemgse122i;

/**
 *
 * @author farah
 */
import javax.swing.*;
import java.util.Scanner;

public class SchoolSystemGSE122i {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner s = new Scanner(System.in);
        int day;
        String month;
        int year;

        while (true) {
            System.out.print("Enter day: ");
            day = s.nextInt();
            System.out.print("Enter month: ");
            month = s.next();
            System.out.print("Enter year: ");
            year = s.nextInt();

            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) {
                System.out.println("Invalid date. Try again.");
                continue;
            }
            break;
        }

        System.out.println("Date validated: " + day + "/" + month + "/" + year);
        SwingUtilities.invokeLater(() -> new SchoolGUI());
    }
}