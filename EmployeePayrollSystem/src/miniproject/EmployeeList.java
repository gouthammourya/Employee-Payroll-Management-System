package miniproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class EmployeeList extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> departmentDropdown;

    public EmployeeList() {
        setTitle("Employee List - Department Wise");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Top Panel for Department Filter
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel departmentLabel = new JLabel("Select Department:");
        departmentDropdown = new JComboBox<>();
        departmentDropdown.addItem("All"); // Default option to show all employees
        loadDepartments(); // Populate departments from the database
        JButton filterButton = new JButton("Filter");

        topPanel.add(departmentLabel);
        topPanel.add(departmentDropdown);
        topPanel.add(filterButton);

        add(topPanel, BorderLayout.NORTH);

        // Table Model with column names
        String[] columnNames = {
            "Employee ID", "Name", "Address", "Email", "Phone",
            "Account No.","Qualification", "Department", "Designation", "Base Salary", "Status"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Event Listeners
        filterButton.addActionListener(this::filterEmployees);

        // Load all employee data by default
        loadEmployeeData("All");
    }

    private void loadDepartments() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT DISTINCT department FROM employees";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departmentDropdown.addItem(rs.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading departments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployeeData(String department) {
        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement ps;

            if (department.equals("All")) {
                query = "SELECT * FROM employees";
                ps = conn.prepareStatement(query);
            } else {
                query = "SELECT * FROM employees WHERE department = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, department);
            }

            ResultSet rs = ps.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String account = rs.getString("account");
                String qualification = rs.getString("Qualification");
                String dept = rs.getString("department");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("salary");
                String status = rs.getString("status");

                Object[] row = {id, name, address, email, phone, account, qualification, dept, designation, salary, status};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterEmployees(ActionEvent event) {
        String selectedDepartment = (String) departmentDropdown.getSelectedItem();
        loadEmployeeData(selectedDepartment);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeList().setVisible(true));
    }
}
