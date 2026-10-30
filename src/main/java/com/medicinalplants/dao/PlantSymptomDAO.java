package com.medicinalplants.dao;

import com.medicinalplants.DBConnection;
import com.medicinalplants.model.Symptom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantSymptomDAO {

    public boolean addMapping(int plantId, int symptomId) {
        String sql = "INSERT IGNORE INTO plant_symptoms (plant_id, symptom_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plantId);
            stmt.setInt(2, symptomId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeMapping(int plantId, int symptomId) {
        String sql = "DELETE FROM plant_symptoms WHERE plant_id = ? AND symptom_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plantId);
            stmt.setInt(2, symptomId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Symptom> getSymptomsForPlant(int plantId) {
        List<Symptom> symptoms = new ArrayList<>();
        String sql = "SELECT s.* FROM symptoms s JOIN plant_symptoms ps ON s.id = ps.symptom_id WHERE ps.plant_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    symptoms.add(new Symptom(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return symptoms;
    }
}
