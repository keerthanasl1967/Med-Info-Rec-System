package com.medicinalplants.ui;

import com.medicinalplants.model.Plant;
import com.medicinalplants.model.Symptom;
import com.medicinalplants.service.AdminService;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import java.awt.*;

public class ManagePlantSymptomsPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final AdminService adminService = new AdminService();
    private final JComboBox<Plant> plantBox;
    private final JComboBox<Symptom> symptomBox;

    public ManagePlantSymptomsPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Admin Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.ADMIN_DASHBOARD));
        topPanel.add(backBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        plantBox = new JComboBox<>();
        symptomBox = new JComboBox<>();

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Select Plant:"), gbc);
        gbc.gridx = 1; formPanel.add(plantBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Select Symptom:"), gbc);
        gbc.gridx = 1; formPanel.add(symptomBox, gbc);

        JButton mapBtn = new JButton("Link Plant to Symptom");
        mapBtn.setBackground(new Color(46, 125, 50));
        mapBtn.setForeground(Color.WHITE);
        mapBtn.addActionListener(e -> {
            Plant p = (Plant) plantBox.getSelectedItem();
            Symptom s = (Symptom) symptomBox.getSelectedItem();
            if (p != null && s != null) {
                adminService.addPlantSymptomMapping(p.getId(), s.getId());
                JOptionPane.showMessageDialog(this, "Successfully mapped " + p.getCommonName() + " to " + s.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(mapBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadDropdowns();
            }
        });
    }

    private void loadDropdowns() {
        plantBox.removeAllItems();
        for (Plant p : plantService.getAllPlants()) {
            plantBox.addItem(p);
        }
        symptomBox.removeAllItems();
        for (Symptom s : plantService.getAllSymptoms()) {
            symptomBox.addItem(s);
        }
    }
}