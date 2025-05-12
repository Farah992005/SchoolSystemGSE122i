package schoolsystemgse122i;

import javax.swing.*;
import java.awt.*;

public class StudentSectionGUI extends JFrame {
    private JTextField dayField, monthField, yearField, nameField, classField, rollField, feeField;
    private JButton saveButton, feeButton, listButton, modifyButton, searchButton, deleteButton;

    public StudentSectionGUI() {
        setTitle("Student Section");
        setSize(500, 500);
        setLayout(new GridLayout(12, 2, 10, 10));

        add(new JLabel("Day of Admission:"));
        dayField = new JTextField();
        add(dayField);

        add(new JLabel("Month of Admission:"));
        monthField = new JTextField();
        add(monthField);

        add(new JLabel("Year of Admission:"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Class:"));
        classField = new JTextField();
        add(classField);

        add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        add(rollField);

        add(new JLabel("Fee Paid:"));
        feeField = new JTextField("0.0");
        add(feeField);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveStudent());
        add(saveButton);

        feeButton = new JButton("View Fee");
        feeButton.addActionListener(e -> viewFee());
        add(feeButton);

        listButton = new JButton("List Students");
        listButton.addActionListener(e -> displayRecordList());
        add(listButton);

        modifyButton = new JButton("Modify");
        modifyButton.addActionListener(e -> modifyRecord());
        add(modifyButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchRecord());
        add(searchButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteRecord());
        add(deleteButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void saveStudent() {
        try {
            int day = Integer.parseInt(dayField.getText().trim());
            String month = monthField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String name = nameField.getText().trim();
            String studentClass = classField.getText().trim();
            int rollNo = Integer.parseInt(rollField.getText().trim());
            double feePaid = Double.parseDouble(feeField.getText().trim());

            if (name.isEmpty() || studentClass.isEmpty() || rollNo <= 0 || feePaid < 0) {
                throw new Exception("Invalid input: All fields must be valid.");
            }

            var dbHandler = new DatabaseHandler();
            dbHandler.saveStudent(name, studentClass, rollNo, feePaid, date.toString());
            JOptionPane.showMessageDialog(this, "Student saved: " + date + " " + name);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void viewFee() {
        try {
            int rollNo = Integer.parseInt(rollField.getText().trim());
            double feePaid = Double.parseDouble(feeField.getText().trim());
            int day = Integer.parseInt(dayField.getText().trim());
            String month = monthField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            Student student = new Student("", "", rollNo, feePaid, date);
            student.viewFee();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void displayRecordList() {
        try {
            var dbHandler = new DatabaseHandler();
            var rs = dbHandler.getStudents();
            StringBuilder display = new StringBuilder("Student Records:\n");
            boolean hasRecords = false;

            while (rs.next()) {
                hasRecords = true;
                String[] dateParts = rs.getString("date").split(" ");
                CustomDate date = new CustomDate(Integer.parseInt(dateParts[0]), dateParts[1], Integer.parseInt(dateParts[2]));
                display.append(date)
                       .append(", Name: ").append(rs.getString("name"))
                       .append(", Class: ").append(rs.getString("studentClass"))
                       .append(", Roll No: ").append(rs.getInt("rollNo"))
                       .append(", Fee Paid: ").append(rs.getDouble("feePaid"))
                       .append("\n");
            }

            if (!hasRecords) display.append("No records found.");
            JOptionPane.showMessageDialog(this, display.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void modifyRecord() {
        try {
            int rollNo = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter roll number to modify:"));
            int day = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new day of admission:"));
            String month = JOptionPane.showInputDialog(this, "Enter new month of admission:");
            int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new year of admission:"));
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String name = JOptionPane.showInputDialog(this, "Enter new name:");
            String studentClass = JOptionPane.showInputDialog(this, "Enter new class:");
            double feePaid = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new fee paid:"));
            var dbHandler = new DatabaseHandler();
            dbHandler.updateStudent(rollNo, name, studentClass, feePaid, date.toString());
            JOptionPane.showMessageDialog(this, "Record modified: " + date + " " + name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchRecord() {
        try {
            int rollNo = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter roll number to search:"));
            var dbHandler = new DatabaseHandler();
            var rs = dbHandler.searchStudent(rollNo);
            if (rs.next()) {
                String[] dateParts = rs.getString("date").split(" ");
                CustomDate date = new CustomDate(Integer.parseInt(dateParts[0]), dateParts[1], Integer.parseInt(dateParts[2]));
                JOptionPane.showMessageDialog(this, date + ", Name: " + rs.getString("name") + ", Class: " + rs.getString("studentClass") + ", Roll No: " + rs.getInt("rollNo") + ", Fee Paid: " + rs.getDouble("feePaid"));
            } else {
                JOptionPane.showMessageDialog(this, "No student found with Roll No: " + rollNo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteRecord() {
        try {
            int rollNo = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter roll number to delete:"));
            var dbHandler = new DatabaseHandler();
            dbHandler.deleteStudent(rollNo);
            JOptionPane.showMessageDialog(this, "Record deleted.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}