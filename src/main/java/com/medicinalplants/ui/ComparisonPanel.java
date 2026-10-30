package com.medicinalplants.ui;

import com.medicinalplants.model.Plant;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ComparisonPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final JComboBox<Plant> plant1Box;
    private final JComboBox<Plant> plant2Box;
    private final JTable comparisonTable;
    private final DefaultTableModel tableModel;

    public ComparisonPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        topPanel.setBackground(new Color(220, 237, 200));

        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);

        topPanel.add(new JLabel("Plant 1:"));
        plant1Box = new JComboBox<>();
        topPanel.add(plant1Box);

        topPanel.add(new JLabel("Plant 2:"));
        plant2Box = new JComboBox<>();
        topPanel.add(plant2Box);

        JButton compareBtn = new JButton("Compare");
        compareBtn.setBackground(new Color(46, 125, 50));
        compareBtn.setForeground(Color.WHITE);
        compareBtn.addActionListener(e -> performComparison());
        topPanel.add(compareBtn);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Attribute", "Plant 1", "Plant 2"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        comparisonTable = new JTable(tableModel);
        comparisonTable.setRowHeight(40);
        comparisonTable.getTableHeader().setBackground(new Color(165, 214, 167));
        add(new JScrollPane(comparisonTable), BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadPlants();
            }
        });
    }

    private void loadPlants() {
        plant1Box.removeAllItems();
        plant2Box.removeAllItems();
        List<Plant> plants = plantService.getAllPlants();
        for (Plant p : plants) {
            plant1Box.addItem(p);
            plant2Box.addItem(p);
        }
        if (plants.size() >= 2) {
            plant2Box.setSelectedIndex(1);
        }
    }

    private void performComparison() {
        Plant p1 = (Plant) plant1Box.getSelectedItem();
        Plant p2 = (Plant) plant2Box.getSelectedItem();

        if (p1 == null || p2 == null) return;

        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"Common Name", p1.getCommonName(), p2.getCommonName()});
        tableModel.addRow(new Object[]{"Scientific Name", p1.getScientificName(), p2.getScientificName()});
        tableModel.addRow(new Object[]{"Category", p1.getCategoryName(), p2.getCategoryName()});
        tableModel.addRow(new Object[]{"Description", p1.getDescription(), p2.getDescription()});
        tableModel.addRow(new Object[]{"Medicinal Uses", p1.getUses(), p2.getUses()});
        tableModel.addRow(new Object[]{"Plant Parts Used", p1.getPlantParts(), p2.getPlantParts()});
        tableModel.addRow(new Object[]{"Preparation", p1.getPreparation(), p2.getPreparation()});
        tableModel.addRow(new Object[]{"Precautions", p1.getPrecautions(), p2.getPrecautions()});
    }
}