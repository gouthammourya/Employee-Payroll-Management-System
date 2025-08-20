package miniproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.sql.*;

public class PaymentManagement extends JFrame {
    private JComboBox<String> monthDropdown;
    private JTextField empIdField, leaveDaysField;
    private JTable paymentSlipTable;
    private DefaultTableModel tableModel;
    private JButton calculateButton, sendEmailButton, printButton;

    private String employeeName; // To store employee name for the slip

    public PaymentManagement() {
        setTitle("Payment Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setBounds(50, 30, 100, 25);
        add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setBounds(150, 30, 200, 25);
        add(empIdField);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, 70, 100, 25);
        add(monthLabel);

        monthDropdown = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        monthDropdown.setBounds(150, 70, 200, 25);
        add(monthDropdown);

        JLabel leaveDaysLabel = new JLabel("Leave Days:");
        leaveDaysLabel.setBounds(50, 110, 100, 25);
        add(leaveDaysLabel);

        leaveDaysField = new JTextField();
        leaveDaysField.setBounds(150, 110, 200, 25);
        add(leaveDaysField);

        calculateButton = new JButton("Calculate & Generate");
        calculateButton.setBounds(50, 150, 200, 30);
        add(calculateButton);

        sendEmailButton = new JButton("Send Payment Slip");
        sendEmailButton.setBounds(300, 150, 200, 30);
        add(sendEmailButton);

        printButton = new JButton("Print Payment Slip");
        printButton.setBounds(550, 150, 200, 30);
        add(printButton);

        // Payment Slip Table
        String[] columns = {"Description", "Details"};
        tableModel = new DefaultTableModel(columns, 0);
        paymentSlipTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(paymentSlipTable);
        tableScrollPane.setBounds(50, 200, 700, 300);
        add(tableScrollPane);

        calculateButton.addActionListener(this::generatePaymentSlip);
        sendEmailButton.addActionListener(this::sendPaymentSlipEmail);
        printButton.addActionListener(this::printPaymentSlip);
    }

    private void generatePaymentSlip(ActionEvent event) {
        String empId = empIdField.getText();
        String month = (String) monthDropdown.getSelectedItem();
        int leaveDays = Integer.parseInt(leaveDaysField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, email, salary FROM employees WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employeeName = rs.getString("name");
                String email = rs.getString("email");
                double basicSalary = rs.getDouble("salary");

                double hra = basicSalary * 0.2;
                double da = basicSalary * 0.1;
                double ta = basicSalary * 0.05;
                double esi = basicSalary * 0.075;
                double pf = basicSalary * 0.08;
                double leaveDeduction = leaveDays * (basicSalary / 30);
                double totalDeductions = esi + pf + leaveDeduction;
                double netSalary = basicSalary + hra + da + ta - totalDeductions;

                // Clear and populate table
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"Employee ID", empId});
                tableModel.addRow(new Object[]{"Employee Name", employeeName});
                tableModel.addRow(new Object[]{"Month", month});
                tableModel.addRow(new Object[]{"Basic Salary", basicSalary});
                tableModel.addRow(new Object[]{"HRA", hra});
                tableModel.addRow(new Object[]{"DA", da});
                tableModel.addRow(new Object[]{"TA", ta});
                tableModel.addRow(new Object[]{"ESI", esi});
                tableModel.addRow(new Object[]{"PF", pf});
                tableModel.addRow(new Object[]{"Leave Deduction", leaveDeduction});
                tableModel.addRow(new Object[]{"Net Salary", netSalary});

                JOptionPane.showMessageDialog(this, "Payment Slip generated for " + employeeName);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating payment slip.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendPaymentSlipEmail(ActionEvent event) {
        String empId = empIdField.getText();
        String month = (String) monthDropdown.getSelectedItem();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT email FROM employees WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");

                // Build email message
                StringBuilder emailBody = new StringBuilder();
                emailBody.append("Payment Slip for ").append(month).append("\n\n");
                emailBody.append("Employee ID: ").append(empId).append("\n");
                emailBody.append("Employee Name: ").append(employeeName).append("\n\n");

                for (int i = 3; i < tableModel.getRowCount(); i++) { // Skip Employee ID, Name, and Month rows
                    emailBody.append(tableModel.getValueAt(i, 0))
                             .append(": ")
                             .append(tableModel.getValueAt(i, 1))
                             .append("\n");
                }

                EmailSender.sendEmail(email, "Payment Slip for " + month, emailBody.toString());
                JOptionPane.showMessageDialog(this, "Payment Slip sent to " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending email.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printPaymentSlip(ActionEvent event) {
        try {
            boolean printed = paymentSlipTable.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Payment Slip printed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Printing canceled.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while printing.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentManagement().setVisible(true));
    }
}