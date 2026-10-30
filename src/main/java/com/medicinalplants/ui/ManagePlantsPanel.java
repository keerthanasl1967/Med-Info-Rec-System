package com.medicinalplants.ui;

import com.medicinalplants.model.Category;
import com.medicinalplants.model.Plant;
import com.medicinalplants.service.AdminService;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagePlantsPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final AdminService adminService = new AdminService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ManagePlantsPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Admin Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.ADMIN_DASHBOARD));
        topPanel.add(backBtn);

        JButton addBtn = new JButton("Add Plant");
        addBtn.addActionListener(e -> showPlantDialog(null));
        topPanel.add(addBtn);

        JButton deleteBtn = new JButton("Delete Plant");
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Delete this plant?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminService.deletePlant(id);
                    loadData();
                }
            }
        });
        topPanel.add(deleteBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Common Name", "Scientific Name", "Category", "Uses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        Plant p = plantService.getPlantById(id);
                        if (p != null) showPlantDialog(p);
                    }
                }
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadData();
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (Plant p : plantService.getAllPlants()) {
            tableModel.addRow(new Object[]{p.getId(), p.getCommonName(), p.getScientificName(), p.getCategoryName(), p.getUses()});
        }
    }

    private void showPlantDialog(Plant plant) {
        JDialog dialog = new JDialog((Frame) null, plant == null ? "Add Plant" : "Edit Plant", true);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField commonField = new JTextField(plant != null ? plant.getCommonName() : "", 20);
        JTextField sciField = new JTextField(plant != null ? plant.getScientificName() : "", 20);
        JComboBox<Category> catBox = new JComboBox<>();
        for (Category c : plantService.getAllCategories()) {
            catBox.addItem(c);
            if (plant != null && c.getId() == plant.getCategoryId()) {
                catBox.setSelectedItem(c);
            }
        }
        JTextArea descArea = new JTextArea(plant != null ? plant.getDescription() : "", 3, 20);
        JTextArea usesArea = new JTextArea(plant != null ? plant.getUses() : "", 3, 20);
        JTextField partsField = new JTextField(plant != null ? plant.getPlantParts() : "", 20);
        JTextArea prepArea = new JTextArea(plant != null ? plant.getPreparation() : "", 3, 20);
        JTextArea precArea = new JTextArea(plant != null ? plant.getPrecautions() : "", 3, 20);

        int gridy = 0;
        gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Common Name:"), gbc);
        gbc.gridx = 1; dialog.add(commonField, gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Scientific Name:"), gbc);
        gbc.gridx = 1; dialog.add(sciField, gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; dialog.add(catBox, gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(descArea), gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Uses:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(usesArea), gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Parts Used:"), gbc);
        gbc.gridx = 1; dialog.add(partsField, gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Preparation:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(prepArea), gbc);

        gridy++; gbc.gridx = 0; gbc.gridy = gridy; dialog.add(new JLabel("Precautions:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(precArea), gbc);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            Category selectedCat = (Category) catBox.getSelectedItem();
            Plant p = new Plant(
                    plant != null ? plant.getId() : 0,
                    commonField.getText().trim(),
                    sciField.getText().trim(),
                    selectedCat != null ? selectedCat.getId() : 1,
                    selectedCat != null ? selectedCat.getName() : "",
                    descArea.getText().trim(),
                    usesArea.getText().trim(),
                    partsField.getText().trim(),
                    prepArea.getText().trim(),
                    precArea.getText().trim(),
                    ""
            );
            adminService.savePlant(p);
            dialog.dispose();
            loadData();
        });
        gridy++; gbc.gridx = 0; gbc.gridy = gridy; gbc.gridwidth = 2;
        dialog.add(saveBtn, gbc);

        dialog.setVisible(true);
    }
}
