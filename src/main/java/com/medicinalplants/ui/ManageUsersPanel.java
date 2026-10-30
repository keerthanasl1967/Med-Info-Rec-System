package com.medicinalplants.ui;

import com.medicinalplants.model.User;
import com.medicinalplants.service.AdminService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageUsersPanel extends JPanel {
    private final AdminService adminService = new AdminService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ManageUsersPanel(AppFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 248, 233));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 237, 200));
        JButton backBtn = new JButton("Admin Dashboard");
        backBtn.addActionListener(e -> frame.showPanel(AppFrame.ADMIN_DASHBOARD));
        topPanel.add(backBtn);

        JButton toggleRoleBtn = new JButton("Toggle Role (USER/ADMIN)");
        toggleRoleBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                String currentRole = (String) tableModel.getValueAt(row, 3);
                String newRole = "ADMIN".equals(currentRole) ? "USER" : "ADMIN";
                adminService.updateUserRole(id, newRole);
                loadData();
            }
        });
        topPanel.add(toggleRoleBtn);

        JButton deleteBtn = new JButton("Delete User");
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Delete user?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    adminService.deleteUser(id);
                    loadData();
                }
            }
        });
        topPanel.add(deleteBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Username", "Email", "Role", "Created At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadData();
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (User u : adminService.getAllUsers()) {
            tableModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail(), u.getRole(), u.getCreatedAt()});
        }
    }
}