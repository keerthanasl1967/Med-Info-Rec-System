package com.medicinalplants.ui;

import com.medicinalplants.UserSession;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {
    public AdminDashboardPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(46, 125, 50));
        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);

        JLabel title = new JLabel("Administrator Control Panel");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        centerPanel.setOpaque(false);

        centerPanel.add(createAdminCard("Manage Plants", "Add, Edit, Delete Medicinal Plants", e -> frame.showPanel(AppFrame.MANAGE_PLANTS)));
        centerPanel.add(createAdminCard("Manage Categories", "Add, Edit, Delete Categories", e -> frame.showPanel(AppFrame.MANAGE_CATEGORIES)));
        centerPanel.add(createAdminCard("Manage Symptoms", "Add, Edit, Delete Symptoms", e -> frame.showPanel(AppFrame.MANAGE_SYMPTOMS)));
        centerPanel.add(createAdminCard("Plant-Symptom Mappings", "Link Plants to Symptoms", e -> frame.showPanel(AppFrame.MANAGE_MAPPINGS)));
        centerPanel.add(createAdminCard("Manage Users", "View Users & Roles", e -> frame.showPanel(AppFrame.MANAGE_USERS)));

        add(centerPanel, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (!UserSession.isAdmin()) {
                    JOptionPane.showMessageDialog(null, "Access Denied: Administrator privileges required.", "Error", JOptionPane.ERROR_MESSAGE);
                    frame.showPanel(AppFrame.DASHBOARD);
                }
            }
        });
    }

    private JButton createAdminCard(String title, String desc, java.awt.event.ActionListener listener) {
        JButton card = new JButton("<html><div style='text-align: center;'><b>" + title + "</b><br/><span style='font-size:10px; color:#555;'>" + desc + "</span></div></html>");
        card.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        card.setBackground(Color.WHITE);
        card.setForeground(new Color(46, 125, 50));
        card.setFocusPainted(false);
        card.setBorder(BorderFactory.createLineBorder(new Color(165, 214, 167), 2, true));
        card.addActionListener(listener);
        return card;
    }
}
