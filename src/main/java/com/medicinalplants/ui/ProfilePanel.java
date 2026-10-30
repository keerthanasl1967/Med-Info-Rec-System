package com.medicinalplants.ui;

import com.medicinalplants.UserSession;
import com.medicinalplants.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private final JLabel usernameLabel;
    private final JLabel emailLabel;
    private final JLabel roleLabel;
    private final JLabel dateLabel;

    public ProfilePanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        usernameLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        emailLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);
        roleLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(roleLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Member Since:"), gbc);
        dateLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(dateLabel, gbc);

        add(panel, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                User u = UserSession.getCurrentUser();
                if (u != null) {
                    usernameLabel.setText(u.getUsername());
                    emailLabel.setText(u.getEmail());
                    roleLabel.setText(u.getRole());
                    dateLabel.setText(u.getCreatedAt() != null ? u.getCreatedAt().toString() : "N/A");
                }
            }
        });
    }
}