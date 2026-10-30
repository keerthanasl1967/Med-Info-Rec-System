package com.medicinalplants.ui;

import com.medicinalplants.model.Category;
import com.medicinalplants.model.Plant;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlantSearchPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final JTextField searchField;
    private final JComboBox<Category> categoryComboBox;
    private final JTable plantTable;
    private final DefaultTableModel tableModel;
    private final AppFrame parentFrame;

    public PlantSearchPanel(AppFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        topPanel.setBackground(new Color(220, 237, 200));

        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(46, 125, 50));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.addActionListener(e -> performSearch());
        topPanel.add(searchBtn);

        topPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem(new Category(0, "All Categories", ""));
        loadCategories();
        categoryComboBox.addActionListener(e -> {
            Category selected = (Category) categoryComboBox.getSelectedItem();
            if (selected != null) {
                if (selected.getId() == 0) {
                    loadTableData(plantService.getAllPlants());
                } else {
                    loadTableData(plantService.getPlantsByCategory(selected.getId()));
                }
            }
        });
        topPanel.add(categoryComboBox);

        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Common Name", "Scientific Name", "Category", "Primary Uses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        plantTable = new JTable(tableModel);
        plantTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        plantTable.setRowHeight(25);
        plantTable.getTableHeader().setBackground(new Color(165, 214, 167));

        plantTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = plantTable.getSelectedRow();
                    if (row != -1) {
                        int plantId = (int) tableModel.getValueAt(row, 0);
                        parentFrame.showPlantDetails(plantId);
                    }
                }
            }
        });

        add(new JScrollPane(plantTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(220, 237, 200));
        bottomPanel.add(new JLabel("Double-click any plant row to view full details."));
        add(bottomPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadCategories();
                loadTableData(plantService.getAllPlants());
            }
        });
    }

    private void loadCategories() {
        Object selected = categoryComboBox.getSelectedItem();
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem(new Category(0, "All Categories", ""));
        for (Category c : plantService.getAllCategories()) {
            categoryComboBox.addItem(c);
        }
        if (selected != null) categoryComboBox.setSelectedItem(selected);
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        loadTableData(plantService.searchPlants(keyword));
    }

    private void loadTableData(List<Plant> plants) {
        tableModel.setRowCount(0);
        for (Plant p : plants) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getCommonName(),
                    p.getScientificName(),
                    p.getCategoryName(),
                    p.getUses()
            });
        }
    }
}