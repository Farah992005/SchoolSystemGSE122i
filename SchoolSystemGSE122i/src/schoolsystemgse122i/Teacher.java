package schoolsystemgse122i;

import javax.swing.JOptionPane;

public class Teacher extends Person {
    protected String position;
    protected double salary;
    protected CustomDate dateOfJoining;
    private static DatabaseHandler dbHandler = new DatabaseHandler();

    public Teacher(String name, String position, double salary, CustomDate dateOfJoining) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.dateOfJoining = dateOfJoining;
    }

    @Override
    public void addRecord() {
        String dayStr = JOptionPane.showInputDialog("Enter day of joining:");
        String month = JOptionPane.showInputDialog("Enter month of joining:");
        String yearStr = JOptionPane.showInputDialog("Enter year of joining:");
        String name = JOptionPane.showInputDialog("Enter teacher name:");
        String position = JOptionPane.showInputDialog("Enter position:");
        String salaryStr = JOptionPane.showInputDialog("Enter salary:");
        try {
            int day = Integer.parseInt(dayStr);
            int year = Integer.parseInt(yearStr);
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            double salary = Double.parseDouble(salaryStr);
            dbHandler.saveTeacher(name, position, salary, date.toString());
            JOptionPane.showMessageDialog(null, "Teacher added: " + date + " " + name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding teacher: " + e.getMessage());
        }
    }

    public void viewSalary() {
        JOptionPane.showMessageDialog(null, dateOfJoining + "\nPosition: " + position + "\nSalary: " + salary);
    }

    @Override
    public void modifyRecord() {
        String name = JOptionPane.showInputDialog("Enter name to modify:");
        try {
            String dayStr = JOptionPane.showInputDialog("Enter new day of joining:");
            String month = JOptionPane.showInputDialog("Enter new month of joining:");
            String yearStr = JOptionPane.showInputDialog("Enter new year of joining:");
            int day = Integer.parseInt(dayStr);
            int year = Integer.parseInt(yearStr);
            CustomDate date = new CustomDate(day, month, year);
            if (!date.chkDate()) throw new Exception("Invalid date!");

            String position = JOptionPane.showInputDialog("Enter new position:");
            String salaryStr = JOptionPane.showInputDialog("Enter new salary:");
            double salary = Double.parseDouble(salaryStr);
            dbHandler.updateTeacher(name, position, salary, date.toString());
            JOptionPane.showMessageDialog(null, "Teacher modified: " + date + " " + name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error modifying teacher: " + e.getMessage());
        }
    }

    @Override
    public void searchRecord() {
        String name = JOptionPane.showInputDialog("Enter name to search:");
        try {
            var rs = dbHandler.searchTeacher(name);
            if (rs.next()) {
                String[] dateParts = rs.getString("date").split(" ");
                CustomDate date = new CustomDate(Integer.parseInt(dateParts[0]), dateParts[1], Integer.parseInt(dateParts[2]));
                JOptionPane.showMessageDialog(null, date + ", Name: " + rs.getString("name") + ", Position: " + rs.getString("position") + ", Salary: " + rs.getDouble("salary"));
            } else {
                JOptionPane.showMessageDialog(null, "No teacher found with Name: " + name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error searching teacher: " + e.getMessage());
        }
    }

    @Override
    public void deleteRecord() {
        String name = JOptionPane.showInputDialog("Enter name to delete:");
        try {
            dbHandler.deleteTeacher(name);
            JOptionPane.showMessageDialog(null, "Teacher deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting teacher: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return dateOfJoining + ", Name: " + name + ", Position: " + position + ", Salary: " + salary;
    }
}