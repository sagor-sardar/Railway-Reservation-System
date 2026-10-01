package gui;

import fileio.FileManager;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    public LoginGUI() {
        setTitle("Railway Reservation System");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setIconImage(new ImageIcon("images/logo.png").getImage());

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel("Bangladesh Railway");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(46, 98, 190));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(welcomeLabel);

        rightPanel.add(Box.createVerticalStrut(20));

        JLabel signInLabel = new JLabel("Sign in to your account");
        signInLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        signInLabel.setForeground(new Color(100, 100, 100));
        signInLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(signInLabel);

        rightPanel.add(Box.createVerticalStrut(40));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(usernameLabel);

        usernameField = new JTextField();
        styleTextField(usernameField);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(usernameField);

        rightPanel.add(Box.createVerticalStrut(25));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(passwordField);

        rightPanel.add(Box.createVerticalStrut(40));

        loginBtn = new JButton("Sign In");
        styleButton(loginBtn, new Color(46, 204, 113));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(loginBtn);

        rightPanel.add(Box.createVerticalStrut(30));

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        registerPanel.setBackground(Color.WHITE);

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerPanel.add(noAccountLabel);

        registerBtn = new JButton("Create New Account");
        styleLinkButton(registerBtn);
        registerPanel.add(registerBtn);

        registerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(registerPanel);

        add(rightPanel);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> openRegistration());

        KeyAdapter enterKey = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        };
        usernameField.addKeyListener(enterKey);
        passwordField.addKeyListener(enterKey);

        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(44, 62, 80));
        field.setCaretColor(new Color(44, 62, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void styleLinkButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(new Color(46, 204, 113));
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(46, 204, 113).darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(46, 204, 113));
            }
        });
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showStyledMessage("Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (FileManager.authenticate(username, password)) {
            showStyledMessage("Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new ReservationSystemGUI(username);
        } else {
            showStyledMessage("Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void openRegistration() {
        new RegisterGUI();
    }

    private void showStyledMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.buttonForeground", Color.BLACK);
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
