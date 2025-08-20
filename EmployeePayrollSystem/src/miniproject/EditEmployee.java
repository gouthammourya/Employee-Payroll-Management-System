package miniproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class EditEmployee extends JFrame {
    private JTextField nameField, addressField, emailField, phoneField, accountField, salaryField;
    private JComboBox<String> qualificationBox, departmentBox, designationBox;
    private String empId;

    public EditEmployee(String empId) {
        this.empId = empId;
        setTitle("Edit Employee");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 25);
        add(nameField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 100, 100, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 100, 200, 25);
        add(addressField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 150, 200, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 200, 100, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 200, 200, 25);
        add(phoneField);

        JLabel accountLabel = new JLabel("Account No:");
        accountLabel.setBounds(50, 250, 100, 25);
        add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(150, 250, 200, 25);
        add(accountField);

        JLabel qualificationLabel = new JLabel("Qualification:");
        qualificationLabel.setBounds(50, 300, 100, 25);
        add(qualificationLabel);

        String[] qualifications = {"Bachelor's", "Master's", "PhD"};
        qualificationBox = new JComboBox<>(qualifications);
        qualificationBox.setBounds(150, 300, 200, 25);
        add(qualificationBox);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 350, 100, 25);
        add(departmentLabel);

        String[] departments = {"HR", "Finance", "IT", "Admin"};
        departmentBox = new JComboBox<>(departments);
        departmentBox.setBounds(150, 350, 200, 25);
        add(departmentBox);

        JLabel designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(50, 400, 100, 25);
        add(designationLabel);

        String[] designations = {"Manager", "Engineer", "Analyst", "Clerk"};
        designationBox = new JComboBox<>(designations);
        designationBox.setBounds(150, 400, 200, 25);
        add(designationBox);

        JLabel salaryLabel = new JLabel("Basic Salary:");
        salaryLabel.setBounds(50, 450, 100, 25);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(150, 450, 200, 25);
        add(salaryField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 500, 80, 30);
        add(saveButton);

        saveButton.addActionListener(e -> saveChanges());
        loadEmployeeDetails();
    }

    private void loadEmployeeDetails() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                addressField.setText(rs.getString("address"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
                accountField.setText(rs.getString("account"));
                salaryField.setText(String.valueOf(rs.getDouble("salary")));
                qualificationBox.setSelectedItem(rs.getString("qualification"));
                departmentBox.setSelectedItem(rs.getString("department"));
                designationBox.setSelectedItem(rs.getString("designation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveChanges() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE employees SET name=?, address=?, email=?, phone=?, account=?, qualification=?, department=?, designation=?, salary=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, addressField.getText());
            ps.setString(3, emailField.getText());
            ps.setString(4, phoneField.getText());
            ps.setString(5, accountField.getText());
            ps.setString(6, (String) qualificationBox.getSelectedItem());
            ps.setString(7, (String) departmentBox.getSelectedItem());
            ps.setString(8, (String) designationBox.getSelectedItem());
            ps.setDouble(9, Double.parseDouble(salaryField.getText()));
            ps.setString(10, empId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee details updated successfully!");
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
