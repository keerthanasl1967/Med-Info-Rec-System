package com.medicinalplants.service;

import com.medicinalplants.dao.CategoryDAO;
import com.medicinalplants.dao.PlantDAO;
import com.medicinalplants.dao.PlantSymptomDAO;
import com.medicinalplants.dao.SymptomDAO;
import com.medicinalplants.model.Category;
import com.medicinalplants.model.Plant;
import com.medicinalplants.model.Symptom;

import java.util.List;

public class PlantService {
    private final PlantDAO plantDAO = new PlantDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final SymptomDAO symptomDAO = new SymptomDAO();
    private final PlantSymptomDAO plantSymptomDAO = new PlantSymptomDAO();

    public List<Plant> getAllPlants() { return plantDAO.findAll(); }
    public List<Plant> searchPlants(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllPlants();
        return plantDAO.search(keyword.trim());
    }
    public List<Plant> getPlantsByCategory(int categoryId) { return plantDAO.findByCategory(categoryId); }
    public Plant getPlantById(int id) { return plantDAO.findById(id); }
    public List<Category> getAllCategories() { return categoryDAO.findAll(); }
    public List<Symptom> getAllSymptoms() { return symptomDAO.findAll(); }
    public List<Symptom> getSymptomsForPlant(int plantId) { return plantSymptomDAO.getSymptomsForPlant(plantId); }
    public int getTotalPlantCount() { return plantDAO.count(); }
    public int getTotalCategoryCount() { return categoryDAO.count(); }
    public int getTotalSymptomCount() { return symptomDAO.count(); }
}
