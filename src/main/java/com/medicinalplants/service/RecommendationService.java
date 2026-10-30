package com.medicinalplants.service;

import com.medicinalplants.DBConnection;
import com.medicinalplants.dao.PlantDAO;
import com.medicinalplants.model.Plant;
import com.medicinalplants.model.Recommendation;

import java.sql.*;
import java.util.*;

public class RecommendationService {
    private final PlantDAO plantDAO = new PlantDAO();

    public List<Recommendation> getRecommendations(List<Integer> selectedSymptomIds) {
        if (selectedSymptomIds == null || selectedSymptomIds.isEmpty()) return new ArrayList<>();

        Map<Integer, List<String>> plantMatchedSymptomsMap = new HashMap<>();
        Map<Integer, Integer> plantMatchCountMap = new HashMap<>();

        String placeholders = String.join(",", Collections.nCopies(selectedSymptomIds.size(), "?"));
        String query = "SELECT ps.plant_id, s.name AS symptom_name, s.id AS symptom_id " +
                       "FROM plant_symptoms ps JOIN symptoms s ON ps.symptom_id = s.id " +
                       "WHERE s.id IN (" + placeholders + ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            for (int i = 0; i < selectedSymptomIds.size(); i++) {
                stmt.setInt(i + 1, selectedSymptomIds.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int plantId = rs.getInt("plant_id");
                    String symptomName = rs.getString("symptom_name");

                    plantMatchCountMap.put(plantId, plantMatchCountMap.getOrDefault(plantId, 0) + 1);
                    plantMatchedSymptomsMap.computeIfAbsent(plantId, k -> new ArrayList<>()).add(symptomName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Recommendation> recommendations = new ArrayList<>();
        int totalSelected = selectedSymptomIds.size();

        for (Map.Entry<Integer, Integer> entry : plantMatchCountMap.entrySet()) {
            int plantId = entry.getKey();
            int matchedCount = entry.getValue();
            double matchScore = ((double) matchedCount / totalSelected) * 100.0;

            Plant plant = plantDAO.findById(plantId);
            if (plant != null) {
                List<String> matchedSymptoms = plantMatchedSymptomsMap.get(plantId);
                recommendations.add(new Recommendation(plant, matchScore, matchedSymptoms));
            }
        }

        recommendations.sort((r1, r2) -> Double.compare(r2.getMatchScore(), r1.getMatchScore()));
        return recommendations;
    }
}
