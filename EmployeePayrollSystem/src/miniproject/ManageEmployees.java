package miniproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;

public class ManageEmployees extends JFrame {
    private JTable employeeTable;
    private JButton enableButton, disableButton, editButton;
    private DefaultTableModel tableModel;

    public ManageEmployees() {
        setTitle("Manage Employees");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        String[] columns = {"Employee ID", "Name", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(20, 20, 750, 400);
        add(scrollPane);

        enableButton = new JButton("Enable");
        enableButton.setBounds(200, 450, 100, 30);
        add(enableButton);

        disableButton = new JButton("Disable");
        disableButton.setBounds(320, 450, 100, 30);
        add(disableButton);

        editButton = new JButton("Edit");
        editButton.setBounds(440, 450, 100, 30);
        add(editButton);

        loadEmployees();

        enableButton.addActionListener(e -> updateEmployeeStatus("Enabled"));
        disableButton.addActionListener(e -> updateEmployeeStatus("Disabled"));
        editButton.addActionListener(e -> editEmployee());
    }

    private void loadEmployees() {
        tableModel.setRowCount(0); // Clear table
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, status FROM employees";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployeeStatus(String status) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee!");
            return;
        }

        String empId = (String) tableModel.getValueAt(selectedRow, 0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE employees SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, empId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee status updated to: " + status);
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee!");
            return;
        }

        String empId = (String) tableModel.getValueAt(selectedRow, 0);
        new EditEmployee(empId).setVisible(true);
    }
}
