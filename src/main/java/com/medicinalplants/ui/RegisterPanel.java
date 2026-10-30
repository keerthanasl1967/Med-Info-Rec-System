package com.medicinalplants.ui;

import com.medicinalplants.ValidationUtil;
import com.medicinalplants.service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private final AuthenticationService authService = new AuthenticationService();
    private final JTextField usernameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public RegisterPanel(AppFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(241, 248, 233));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(46, 125, 50));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setOpaque(false);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(46, 125, 50));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (ValidationUtil.isNullOrEmpty(username) || ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ValidationUtil.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email address format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ValidationUtil.isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (authService.register(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                frame.showPanel(AppFrame.LOGIN);
            } else {
                JOptionPane.showMessageDialog(this, "Username or Email already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.WELCOME));
        btnPanel.add(backBtn);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnPanel, gbc);
    }
}
