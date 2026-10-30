package com.medicinalplants.ui;

import com.medicinalplants.UserSession;
import com.medicinalplants.service.FavoriteService;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final FavoriteService favoriteService = new FavoriteService();
    private final JLabel welcomeLabel;
    private final JLabel statsLabel;

    public DashboardPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        // Top Navigation Header
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(46, 125, 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        welcomeLabel = new JLabel("Welcome, User!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(211, 47, 47));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            UserSession.clearSession();
            frame.showPanel(AppFrame.WELCOME);
        });
        topBar.add(logoutBtn, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // Center Grid Buttons
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        centerPanel.setOpaque(false);

        centerPanel.add(createMenuCard("Plant Catalogue", "Browse and search medicinal plants", e -> frame.showPanel(AppFrame.SEARCH)));
        centerPanel.add(createMenuCard("Symptom Recommendations", "Get matching plant recommendations", e -> frame.showPanel(AppFrame.RECOMMENDATION)));
        centerPanel.add(createMenuCard("Plant Comparison", "Compare plants side by side", e -> frame.showPanel(AppFrame.COMPARISON)));
        centerPanel.add(createMenuCard("My Favorites", "View your saved favorite plants", e -> frame.showPanel(AppFrame.FAVORITES)));
        centerPanel.add(createMenuCard("User Profile", "Manage your account details", e -> frame.showPanel(AppFrame.PROFILE)));

        JButton adminBtn = createMenuCard("Admin Dashboard", "Manage database & records", e -> frame.showPanel(AppFrame.ADMIN_DASHBOARD));
        centerPanel.add(adminBtn);

        add(centerPanel, BorderLayout.CENTER);

        // Footer Statistics
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(220, 237, 200));
        statsLabel = new JLabel("Database Statistics...");
        statsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footer.add(statsLabel);
        add(footer, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (UserSession.getCurrentUser() != null) {
                    welcomeLabel.setText("Welcome, " + UserSession.getCurrentUser().getUsername() + "!");
                    adminBtn.setVisible(UserSession.isAdmin());
                }
                updateStats();
            }
        });
    }

    private JButton createMenuCard(String title, String desc, java.awt.event.ActionListener listener) {
        JButton card = new JButton("<html><div style='text-align: center;'><b>" + title + "</b><br/><span style='font-size:10px; color:#555;'>" + desc + "</span></div></html>");
        card.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        card.setBackground(Color.WHITE);
        card.setForeground(new Color(46, 125, 50));
        card.setFocusPainted(false);
        card.setBorder(BorderFactory.createLineBorder(new Color(165, 214, 167), 2, true));
        card.addActionListener(listener);
        return card;
    }

    private void updateStats() {
        int plants = plantService.getTotalPlantCount();
        int categories = plantService.getTotalCategoryCount();
        int symptoms = plantService.getTotalSymptomCount();
        int favs = favoriteService.getUserFavoriteCount();
        statsLabel.setText("Database Statistics — Plants: " + plants + " | Categories: " + categories + " | Symptoms: " + symptoms + " | Your Favorites: " + favs);
    }
}