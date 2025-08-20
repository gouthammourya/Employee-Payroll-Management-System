package miniproject;

import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton addEmployeeBtn = new JButton("Add Employee");
        addEmployeeBtn.setBounds(50, 50, 150, 30);
        add(addEmployeeBtn);

        JButton manageEmployeeBtn = new JButton("Manage Employees");
        manageEmployeeBtn.setBounds(250, 50, 150, 30);
        add(manageEmployeeBtn);

        JButton employeeListBtn = new JButton("Employee List");
        employeeListBtn.setBounds(50, 150, 150, 30);
        add(employeeListBtn);

        JButton paymentMgmtBtn = new JButton("Payment Management");
        paymentMgmtBtn.setBounds(250, 150, 150, 30);
        add(paymentMgmtBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(150, 250, 150, 30);
        add(exitBtn);

        // Button Actions
        addEmployeeBtn.addActionListener(e -> new AddEmployee().setVisible(true));
        manageEmployeeBtn.addActionListener(e -> new ManageEmployees().setVisible(true));
        employeeListBtn.addActionListener(e -> new EmployeeList().setVisible(true));
        paymentMgmtBtn.addActionListener(e -> new PaymentManagement().setVisible(true));
        exitBtn.addActionListener(e -> System.exit(0));
    }
}
