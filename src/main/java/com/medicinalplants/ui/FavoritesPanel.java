package com.medicinalplants.ui;

import com.medicinalplants.model.Plant;
import com.medicinalplants.service.FavoriteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FavoritesPanel extends JPanel {
    private final FavoriteService favoriteService = new FavoriteService();
    private final JTable favoritesTable;
    private final DefaultTableModel tableModel;
    private final AppFrame parentFrame;

    public FavoritesPanel(AppFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);
        topPanel.add(new JLabel("Your Saved Favorite Plants:"));
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Common Name", "Scientific Name", "Category", "Uses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        favoritesTable = new JTable(tableModel);
        favoritesTable.setRowHeight(25);
        favoritesTable.getTableHeader().setBackground(new Color(165, 214, 167));

        favoritesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = favoritesTable.getSelectedRow();
                    if (row != -1) {
                        int plantId = (int) tableModel.getValueAt(row, 0);
                        parentFrame.showPlantDetails(plantId);
                    }
                }
            }
        });

        add(new JScrollPane(favoritesTable), BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadFavorites();
            }
        });
    }

    private void loadFavorites() {
        tableModel.setRowCount(0);
        List<Plant> favs = favoriteService.getUserFavorites();
        for (Plant p : favs) {
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