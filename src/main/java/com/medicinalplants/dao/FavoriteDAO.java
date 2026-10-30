package com.medicinalplants.dao;

import com.medicinalplants.DBConnection;
import com.medicinalplants.model.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    public boolean addFavorite(int userId, int plantId) {
        String sql = "INSERT IGNORE INTO favorites (user_id, plant_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, plantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(int userId, int plantId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND plant_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, plantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFavorite(int userId, int plantId) {
        String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND plant_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, plantId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Plant> getFavoritesForUser(int userId) {
        List<Plant> plants = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM plants p " +
                     "JOIN favorites f ON p.id = f.plant_id " +
                     "LEFT JOIN categories c ON p.category_id = c.id " +
                     "WHERE f.user_id = ? ORDER BY p.common_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plants.add(new Plant(
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
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public int countForUser(int userId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
