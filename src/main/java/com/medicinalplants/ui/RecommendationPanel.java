package com.medicinalplants.ui;

import com.medicinalplants.model.Recommendation;
import com.medicinalplants.model.Symptom;
import com.medicinalplants.service.PlantService;
import com.medicinalplants.service.RecommendationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final RecommendationService recommendationService = new RecommendationService();
    private final JPanel checkboxPanel;
    private final List<JCheckBox> symptomCheckBoxes = new ArrayList<>();
    private final JTable resultTable;
    private final DefaultTableModel tableModel;

    public RecommendationPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));

        JButton backBtn = new JButton("Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.DASHBOARD));
        topPanel.add(backBtn);
        topPanel.add(new JLabel("Select your symptoms / health concerns below:"));
        add(topPanel, BorderLayout.NORTH);

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        checkboxPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(280, 0));
        add(scrollPane, BorderLayout.WEST);

        String[] columns = {"Plant Name", "Scientific Name", "Match Score (%)", "Matched Symptoms"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(30);
        resultTable.getTableHeader().setBackground(new Color(165, 214, 167));

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        JButton recommendBtn = new JButton("Get Plant Recommendations");
        recommendBtn.setBackground(new Color(46, 125, 50));
        recommendBtn.setForeground(Color.WHITE);
        recommendBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        recommendBtn.addActionListener(e -> calculateRecommendations());
        centerContainer.add(recommendBtn, BorderLayout.SOUTH);

        add(centerContainer, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadSymptoms();
            }
        });
    }

    private void loadSymptoms() {
        checkboxPanel.removeAll();
        symptomCheckBoxes.clear();
        for (Symptom s : plantService.getAllSymptoms()) {
            JCheckBox cb = new JCheckBox(s.getName());
            cb.putClientProperty("id", s.getId());
            cb.setBackground(Color.WHITE);
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            symptomCheckBoxes.add(cb);
            checkboxPanel.add(cb);
        }
        checkboxPanel.revalidate();
        checkboxPanel.repaint();
        tableModel.setRowCount(0);
    }

    private void calculateRecommendations() {
        List<Integer> selectedIds = new ArrayList<>();
        for (JCheckBox cb : symptomCheckBoxes) {
            if (cb.isSelected()) {
                selectedIds.add((Integer) cb.getClientProperty("id"));
            }
        }

        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one symptom.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Recommendation> recs = recommendationService.getRecommendations(selectedIds);
        tableModel.setRowCount(0);
        for (Recommendation r : recs) {
            tableModel.addRow(new Object[]{
                    r.getPlant().getCommonName(),
                    r.getPlant().getScientificName(),
                    String.format("%.1f%%", r.getMatchScore()),
                    String.join(", ", r.getMatchedSymptoms())
            });
        }
    }
}
