/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystemgse122i;

/**
 *
 * @author farah
 */
import javax.swing.JOptionPane;

public class Student {
    private String name;
    private String studentClass;
    private int rollNo;
    private double feePaid;
    private double totalFee = 1000.0;
    private CustomDate dateOfAdmission;
    private static DatabaseHandler dbHandler = new DatabaseHandler();

    public Student(String name, String studentClass, int rollNo, double feePaid, CustomDate dateOfAdmission) {
        this.name = name;
        this.studentClass = studentClass;
        this.rollNo = rollNo;
        this.feePaid = feePaid;
        this.dateOfAdmission = dateOfAdmission;
    }

    public void viewFee() {
        double due = totalFee - feePaid;
        double fine = due > 0 ? due * 0.05 : 0;
        double advance = feePaid > totalFee ? feePaid - totalFee : 0;
        JOptionPane.showMessageDialog(null, dateOfAdmission + "\nTotal Fee: " + totalFee + "\nFee Paid: " + feePaid + "\nDue: " + (due > 0 ? due : 0) + "\nFine: " + fine + "\nAdvance: " + advance);
    }

    public String getName() {
        return name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public int getRollNo() {
        return rollNo;
    }

    public double getFeePaid() {
        return feePaid;
    }

    public CustomDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    @Override
    public String toString() {
        return dateOfAdmission + ", Name: " + name + ", Class: " + studentClass + ", Roll No: " + rollNo + ", Fee Paid: " + feePaid;
    }
}