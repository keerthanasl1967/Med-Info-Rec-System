package com.medicinalplants.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(AppFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(232, 245, 233)); // Light Sage Green

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        JLabel titleLabel = new JLabel("Medicinal Plant Information System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(46, 125, 50));
        gbc.gridy = 0;
        add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Explore traditional remedies, get plant recommendations, and compare botanical properties.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(85, 85, 85));
        gbc.gridy = 1;
        add(subtitleLabel, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);

        JButton loginBtn = createStyledButton("Login", new Color(46, 125, 50));
        loginBtn.addActionListener(e -> frame.showPanel(AppFrame.LOGIN));
        btnPanel.add(loginBtn);

        JButton registerBtn = createStyledButton("Register", new Color(76, 175, 80));
        registerBtn.addActionListener(e -> frame.showPanel(AppFrame.REGISTER));
        btnPanel.add(registerBtn);

        gbc.gridy = 2;
        add(btnPanel, gbc);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }
}