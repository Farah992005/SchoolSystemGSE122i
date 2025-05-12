package schoolsystemgse122i;

import javax.swing.*;
import java.awt.*;

public class TeacherSectionGUI extends JFrame {
    private JTextField dayField, monthField, yearField, nameField, positionField, salaryField;
    private JButton saveButton, salaryButton, listButton, modifyButton, searchButton, deleteButton;

    public TeacherSectionGUI() {
        setTitle("Teacher Section");
        setSize(500, 500);
        setLayout(new GridLayout(11, 2, 10, 10));

        add(new JLabel("Day of Joining:"));
        dayField = new JTextField();
        add(dayField);

        add(new JLabel("Month of Joining:"));
        monthField = new JTextField();
        add(monthField);

        add(new JLabel("Year of Joining:"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Position:"));
        positionField = new JTextField();
        add(positionField);

        add(new JLabel("Salary:"));
        salaryField = new JTextField("0.0");
        add(salaryField);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveTeacher());
        add(saveButton);

        salaryButton = new JButton("View Salary");
        salaryButton.addActionListener(e -> viewSalary());
        add(salaryButton);

        listButton = new JButton("List Teachers");
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

    private void saveTeacher() {
        try {
            int day = Integer.parseInt(dayField.getText().trim());
            String month = monthField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());

            if (name.isEmpty() || position.isEmpty() || salary < 0) {
                throw new Exception("Invalid input: All fields must be valid.");
            }

            var dbHandler = new DatabaseHandler();
            dbHandler.saveTeacher(name, position, salary, date.toString());
            JOptionPane.showMessageDialog(this, "Teacher saved: " + date + " " + name);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void viewSalary() {
        try {
            int day = Integer.parseInt(dayField.getText().trim());
            String month = monthField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            Teacher teacher = new Teacher(name, position, salary, date);
            teacher.viewSalary();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void displayRecordList() {
        try {
            var dbHandler = new DatabaseHandler();
            var rs = dbHandler.getTeachers();
            StringBuilder display = new StringBuilder("Teacher Records:\n");
            boolean hasRecords = false;

            while (rs.next()) {
                hasRecords = true;
                String[] dateParts = rs.getString("date").split(" ");
                CustomDate date = new CustomDate(Integer.parseInt(dateParts[0]), dateParts[1], Integer.parseInt(dateParts[2]));
                display.append(date)
                       .append(", Name: ").append(rs.getString("name"))
                       .append(", Position: ").append(rs.getString("position"))
                       .append(", Salary: ").append(rs.getDouble("salary"))
                       .append("\n");
            }

            if (!hasRecords) {
                display.append("No records found.");
            }

            JOptionPane.showMessageDialog(this, display.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error displaying records: " + e.getMessage());
        }
    }

    private void modifyRecord() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter name to modify:");
            int day = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new day of joining:"));
            String month = JOptionPane.showInputDialog(this, "Enter new month of joining:");
            int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new year of joining:"));
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String position = JOptionPane.showInputDialog(this, "Enter new position:");
            double salary = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new salary:"));
            var dbHandler = new DatabaseHandler();
            dbHandler.updateTeacher(name, position, salary, date.toString());
            JOptionPane.showMessageDialog(this, "Record modified: " + date + " " + name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchRecord() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter name to search:");
            var dbHandler = new DatabaseHandler();
            var rs = dbHandler.searchTeacher(name);
            if (rs.next()) {
                String[] dateParts = rs.getString("date").split(" ");
                CustomDate date = new CustomDate(Integer.parseInt(dateParts[0]), dateParts[1], Integer.parseInt(dateParts[2]));
                JOptionPane.showMessageDialog(this, date + ", Name: " + rs.getString("name") + ", Position: " + rs.getString("position") + ", Salary: " + rs.getDouble("salary"));
            } else {
                JOptionPane.showMessageDialog(this, "No teacher found with Name: " + name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteRecord() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter name to delete:");
            var dbHandler = new DatabaseHandler();
            dbHandler.deleteTeacher(name);
            JOptionPane.showMessageDialog(this, "Record deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}