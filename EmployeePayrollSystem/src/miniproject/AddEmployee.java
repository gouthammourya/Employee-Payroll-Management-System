package miniproject;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.UUID;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class AddEmployee extends JFrame {
    private JTextField nameField, addressField, emailField, phoneField, accountField, salaryField;
    private JComboBox<String> qualificationBox, departmentBox, designationBox;

    public AddEmployee() {
        setTitle("Add Employee");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setBounds(50, 50, 150, 25);
        add(idLabel);

        String empId = UUID.randomUUID().toString().substring(0, 8);
        JTextField idField = new JTextField(empId);
        idField.setBounds(200, 50, 200, 25);
        idField.setEditable(false);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 100, 200, 25);
        add(nameField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 150, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(200, 150, 200, 25);
        add(addressField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 200, 150, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(200, 200, 200, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 250, 150, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(200, 250, 200, 25);
        add(phoneField);

        JLabel accountLabel = new JLabel("Account No:");
        accountLabel.setBounds(50, 300, 150, 25);
        add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(200, 300, 200, 25);
        add(accountField);

        JLabel qualificationLabel = new JLabel("Qualification:");
        qualificationLabel.setBounds(50, 350, 150, 25);
        add(qualificationLabel);

        String[] qualifications = {"Bachelor's", "Master's", "PhD"};
        qualificationBox = new JComboBox<>(qualifications);
        qualificationBox.setBounds(200, 350, 200, 25);
        add(qualificationBox);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 400, 150, 25);
        add(departmentLabel);

        String[] departments = {"HR", "Finance", "IT", "Admin"};
        departmentBox = new JComboBox<>(departments);
        departmentBox.setBounds(200, 400, 200, 25);
        add(departmentBox);

        JLabel designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(50, 450, 150, 25);
        add(designationLabel);

        String[] designations = {"Manager", "Engineer", "Analyst", "Clerk"};
        designationBox = new JComboBox<>(designations);
        designationBox.setBounds(200, 450, 200, 25);
        add(designationBox);

        JLabel salaryLabel = new JLabel("Basic Salary:");
        salaryLabel.setBounds(50, 500, 150, 25);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(200, 500, 200, 25);
        add(salaryField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(200, 550, 80, 30);
        add(saveBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(300, 550, 80, 30);
        add(resetBtn);

        saveBtn.addActionListener(e -> saveEmployee(empId));
        resetBtn.addActionListener(e -> resetFields());
    }

    private void saveEmployee(String empId) {
        String name = nameField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String account = accountField.getText();
        String qualification = (String) qualificationBox.getSelectedItem();
        String department = (String) departmentBox.getSelectedItem();
        String designation = (String) designationBox.getSelectedItem();
        String salary = salaryField.getText();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || account.isEmpty() || salary.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO employees (id, name, address, email, phone, account, qualification, department, designation, salary) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, account);
            ps.setString(7, qualification);
            ps.setString(8, department);
            ps.setString(9, designation);
            ps.setDouble(10, Double.parseDouble(salary));
            ps.executeUpdate();

            sendEmail(email, empId, name);
            JOptionPane.showMessageDialog(this, "Employee saved successfully!");
            resetFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String toEmail, String empId, String name) {
        String fromEmail = "hamujegotechnologies@gmail.com";  // Update with your email
        String password = "lpzd jdug ydyg aedv";   // Update with your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Welcome to the Company");
            message.setText("Dear " + name + ",\n\nYour Employee ID is: " + empId + "\nWelcome to the team!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
        phoneField.setText("");
        accountField.setText("");
        salaryField.setText("");
        qualificationBox.setSelectedIndex(0);
        departmentBox.setSelectedIndex(0);
        designationBox.setSelectedIndex(0);
    }
}
