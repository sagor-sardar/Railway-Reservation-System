package gui;

import fileio.FileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterGUI() {
        setTitle("Register Account");
        setSize(350, 230);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setIconImage(new ImageIcon("images/logo.png").getImage());

        JLabel heading = new JLabel("Create New Account", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        JLabel userLbl = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        panel.add(userLbl);
        panel.add(usernameField);
        panel.add(passLbl);
        panel.add(passwordField);
        add(panel, BorderLayout.CENTER);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(0, 123, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        add(registerBtn, BorderLayout.SOUTH);

        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields required!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (FileManager.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}