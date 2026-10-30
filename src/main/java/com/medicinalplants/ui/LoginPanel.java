package com.medicinalplants.ui;

import com.medicinalplants.ValidationUtil;
import com.medicinalplants.service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final AuthenticationService authService = new AuthenticationService();
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel(AppFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(241, 248, 233));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("User Login", JLabel.CENTER);
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
        add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setOpaque(false);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(46, 125, 50));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (ValidationUtil.isNullOrEmpty(username) || ValidationUtil.isNullOrEmpty(password)) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (authService.login(username, password)) {
                usernameField.setText("");
                passwordField.setText("");
                frame.showPanel(AppFrame.DASHBOARD);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.WELCOME));
        btnPanel.add(backBtn);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnPanel, gbc);
    }
}