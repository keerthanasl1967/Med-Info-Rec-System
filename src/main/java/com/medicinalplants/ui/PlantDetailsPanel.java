package com.medicinalplants.ui;

import com.medicinalplants.model.Plant;
import com.medicinalplants.model.Symptom;
import com.medicinalplants.service.FavoriteService;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlantDetailsPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final FavoriteService favoriteService = new FavoriteService();

    private final JLabel commonNameLabel;
    private final JLabel scientificNameLabel;
    private final JLabel categoryLabel;
    private final JLabel plantPartsLabel;
    private final JTextArea descArea;
    private final JTextArea usesArea;
    private final JTextArea prepArea;
    private final JTextArea precautionsArea;
    private final JTextArea symptomsArea;
    private final JButton favoriteBtn;
    private int currentPlantId;

    public PlantDetailsPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));

        JButton backBtn = new JButton("Back to Search");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.SEARCH));
        topPanel.add(backBtn);

        favoriteBtn = new JButton("Add to Favorites");
        favoriteBtn.setBackground(new Color(255, 193, 7));
        favoriteBtn.setFocusPainted(false);
        favoriteBtn.addActionListener(e -> {
            if (favoriteService.toggleFavorite(currentPlantId)) {
                updateFavoriteButtonState();
            }
        });
        topPanel.add(favoriteBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        detailsPanel.setBackground(Color.WHITE);

        commonNameLabel = new JLabel("Plant Common Name");
        commonNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        commonNameLabel.setForeground(new Color(46, 125, 50));
        detailsPanel.add(commonNameLabel);

        scientificNameLabel = new JLabel("Scientific Name");
        scientificNameLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        scientificNameLabel.setForeground(new Color(100, 100, 100));
        detailsPanel.add(scientificNameLabel);

        categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(categoryLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(createSectionLabel("Description"));
        descArea = createReadOnlyTextArea();
        detailsPanel.add(new JScrollPane(descArea));

        detailsPanel.add(createSectionLabel("Medicinal Uses"));
        usesArea = createReadOnlyTextArea();
        detailsPanel.add(new JScrollPane(usesArea));

        detailsPanel.add(createSectionLabel("Plant Parts Used & Preparation"));
        plantPartsLabel = new JLabel("Parts: ");
        detailsPanel.add(plantPartsLabel);
        prepArea = createReadOnlyTextArea();
        detailsPanel.add(new JScrollPane(prepArea));

        detailsPanel.add(createSectionLabel("Matching Symptoms"));
        symptomsArea = createReadOnlyTextArea();
        detailsPanel.add(new JScrollPane(symptomsArea));

        detailsPanel.add(createSectionLabel("Precautions & Side Effects"));
        precautionsArea = createReadOnlyTextArea();
        detailsPanel.add(new JScrollPane(precautionsArea));

        add(new JScrollPane(detailsPanel), BorderLayout.CENTER);
    }

    public void loadPlant(int plantId) {
        this.currentPlantId = plantId;
        Plant plant = plantService.getPlantById(plantId);
        if (plant != null) {
            commonNameLabel.setText(plant.getCommonName());
            scientificNameLabel.setText("Scientific Name: " + plant.getScientificName());
            categoryLabel.setText("Category: " + plant.getCategoryName());
            descArea.setText(plant.getDescription());
            usesArea.setText(plant.getUses());
            plantPartsLabel.setText("Plant Parts Used: " + plant.getPlantParts());
            prepArea.setText(plant.getPreparation());
            precautionsArea.setText(plant.getPrecautions());

            List<Symptom> symptoms = plantService.getSymptomsForPlant(plantId);
            StringBuilder sb = new StringBuilder();
            for (Symptom s : symptoms) {
                sb.append("• ").append(s.getName()).append("\n");
            }
            symptomsArea.setText(sb.toString().trim());
            updateFavoriteButtonState();
        }
    }

    private void updateFavoriteButtonState() {
        if (favoriteService.isFavorite(currentPlantId)) {
            favoriteBtn.setText("Remove from Favorites");
            favoriteBtn.setBackground(new Color(244, 67, 54));
            favoriteBtn.setForeground(Color.WHITE);
        } else {
            favoriteBtn.setText("Add to Favorites");
            favoriteBtn.setBackground(new Color(255, 193, 7));
            favoriteBtn.setForeground(Color.BLACK);
        }
    }

    private JLabel createSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(46, 125, 50));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        return lbl;
    }

    private JTextArea createReadOnlyTextArea() {
        JTextArea area = new JTextArea(3, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setBackground(new Color(248, 249, 250));
        return area;
    }
}