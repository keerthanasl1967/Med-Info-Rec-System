package com.medicinalplants.ui;

import com.medicinalplants.model.Symptom;
import com.medicinalplants.service.AdminService;
import com.medicinalplants.service.PlantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageSymptomsPanel extends JPanel {
    private final PlantService plantService = new PlantService();
    private final AdminService adminService = new AdminService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ManageSymptomsPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Admin Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.ADMIN_DASHBOARD));
        topPanel.add(backBtn);

        JButton addBtn = new JButton("Add Symptom");
        addBtn.addActionListener(e -> showDialog(null));
        topPanel.add(addBtn);

        JButton deleteBtn = new JButton("Delete Symptom");
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Delete symptom?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminService.deleteSymptom(id);
                    loadData();
                }
            }
        });
        topPanel.add(deleteBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Description"};
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
                        Symptom s = plantService.getAllSymptoms().stream().filter(sym -> sym.getId() == id).findFirst().orElse(null);
                        if (s != null) showDialog(s);
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
        for (Symptom s : plantService.getAllSymptoms()) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getDescription()});
        }
    }

    private void showDialog(Symptom symptom) {
        JDialog dialog = new JDialog((Frame) null, symptom == null ? "Add Symptom" : "Edit Symptom", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(symptom != null ? symptom.getName() : "", 15);
        JTextArea descArea = new JTextArea(symptom != null ? symptom.getDescription() : "", 3, 15);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(descArea), gbc);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            Symptom s = new Symptom(
                    symptom != null ? symptom.getId() : 0,
                    nameField.getText().trim(),
                    descArea.getText().trim()
            );
            adminService.saveSymptom(s);
            dialog.dispose();
            loadData();
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        dialog.add(saveBtn, gbc);

        dialog.setVisible(true);
    }
}
