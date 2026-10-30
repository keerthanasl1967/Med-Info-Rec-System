package com.medicinalplants.ui;

import com.medicinalplants.UserSession;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainContainer;

    // Navigation Constants
    public static final String WELCOME = "WELCOME";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String SEARCH = "SEARCH";
    public static final String DETAILS = "DETAILS";
    public static final String RECOMMENDATION = "RECOMMENDATION";
    public static final String COMPARISON = "COMPARISON";
    public static final String FAVORITES = "FAVORITES";
    public static final String PROFILE = "PROFILE";
    public static final String ADMIN_DASHBOARD = "ADMIN_DASHBOARD";
    public static final String MANAGE_PLANTS = "MANAGE_PLANTS";
    public static final String MANAGE_CATEGORIES = "MANAGE_CATEGORIES";
    public static final String MANAGE_SYMPTOMS = "MANAGE_SYMPTOMS";
    public static final String MANAGE_MAPPINGS = "MANAGE_MAPPINGS";
    public static final String MANAGE_USERS = "MANAGE_USERS";

    private final PlantDetailsPanel plantDetailsPanel;

    public AppFrame() {
        setTitle("Medicinal Plant Information & Recommendation System");
        setSize(1080, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Initialize UI Panels
        mainContainer.add(new WelcomePanel(this), WELCOME);
        mainContainer.add(new LoginPanel(this), LOGIN);
        mainContainer.add(new RegisterPanel(this), REGISTER);
        mainContainer.add(new DashboardPanel(this), DASHBOARD);
        mainContainer.add(new PlantSearchPanel(this), SEARCH);

        plantDetailsPanel = new PlantDetailsPanel(this);
        mainContainer.add(plantDetailsPanel, DETAILS);

        mainContainer.add(new RecommendationPanel(this), RECOMMENDATION);
        mainContainer.add(new ComparisonPanel(this), COMPARISON);
        mainContainer.add(new FavoritesPanel(this), FAVORITES);
        mainContainer.add(new ProfilePanel(this), PROFILE);
        mainContainer.add(new AdminDashboardPanel(this), ADMIN_DASHBOARD);
        mainContainer.add(new ManagePlantsPanel(this), MANAGE_PLANTS);
        mainContainer.add(new ManageCategoriesPanel(this), MANAGE_CATEGORIES);
        mainContainer.add(new ManageSymptomsPanel(this), MANAGE_SYMPTOMS);
        mainContainer.add(new ManagePlantSymptomsPanel(this), MANAGE_MAPPINGS);
        mainContainer.add(new ManageUsersPanel(this), MANAGE_USERS);

        add(mainContainer);
        cardLayout.show(mainContainer, WELCOME);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainContainer, panelName);
    }

    public void showPlantDetails(int plantId) {
        plantDetailsPanel.loadPlant(plantId);
        cardLayout.show(mainContainer, DETAILS);
    }
}