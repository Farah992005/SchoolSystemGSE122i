/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystemgse122i;

/**
 *
 * @author farah
 */
import javax.swing.*;
import java.awt.*;

public class SchoolGUI extends JFrame {
    public SchoolGUI() {
        displayMainMenu();
    }

    public void displayMainMenu() {
        setTitle("EduManager");
        setSize(400, 200);
        setLayout(new FlowLayout());

        JButton studentBtn = new JButton("Student Section");
        JButton teacherBtn = new JButton("Teacher Section");
        JButton exitBtn = new JButton("Exit");

        studentBtn.addActionListener(e -> new StudentSectionGUI().setVisible(true));
        teacherBtn.addActionListener(e -> new TeacherSectionGUI().setVisible(true));
        exitBtn.addActionListener(e -> System.exit(0));

        add(studentBtn);
        add(teacherBtn);
        add(exitBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}