package com.medicinalplants.dao;

import com.medicinalplants.DBConnection;
import com.medicinalplants.model.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantDAO {

    public boolean insert(Plant plant) {
        String sql = "INSERT INTO plants (common_name, scientific_name, category_id, description, uses, plant_parts, preparation, precautions, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setPlantParams(stmt, plant);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Plant findById(int id) {
        String sql = "SELECT p.*, c.name AS category_name FROM plants p LEFT JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlant(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Plant> findAll() {
        List<Plant> plants = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM plants p LEFT JOIN categories c ON p.category_id = c.id ORDER BY p.common_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plants.add(mapResultSetToPlant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public List<Plant> search(String keyword) {
        List<Plant> plants = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM plants p LEFT JOIN categories c ON p.category_id = c.id " +
                     "WHERE p.common_name LIKE ? OR p.scientific_name LIKE ? OR p.uses LIKE ? ORDER BY p.common_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plants.add(mapResultSetToPlant(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public List<Plant> findByCategory(int categoryId) {
        List<Plant> plants = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM plants p LEFT JOIN categories c ON p.category_id = c.id WHERE p.category_id = ? ORDER BY p.common_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plants.add(mapResultSetToPlant(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public boolean update(Plant plant) {
        String sql = "UPDATE plants SET common_name = ?, scientific_name = ?, category_id = ?, description = ?, uses = ?, plant_parts = ?, preparation = ?, precautions = ?, image_path = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setPlantParams(stmt, plant);
            stmt.setInt(10, plant.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM plants WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM plants";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void setPlantParams(PreparedStatement stmt, Plant plant) throws SQLException {
        stmt.setString(1, plant.getCommonName());
        stmt.setString(2, plant.getScientificName());
        stmt.setInt(3, plant.getCategoryId());
        stmt.setString(4, plant.getDescription());
        stmt.setString(5, plant.getUses());
        stmt.setString(6, plant.getPlantParts());
        stmt.setString(7, plant.getPreparation());
        stmt.setString(8, plant.getPrecautions());
        stmt.setString(9, plant.getImagePath());
    }

    private Plant mapResultSetToPlant(ResultSet rs) throws SQLException {
        return new Plant(
                rs.getInt("id"),
                rs.getString("common_name"),
                rs.getString("scientific_name"),
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("description"),
                rs.getString("uses"),
                rs.getString("plant_parts"),
                rs.getString("preparation"),
                rs.getString("precautions"),
                rs.getString("image_path")
        );
    }
}
