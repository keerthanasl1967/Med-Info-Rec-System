package com.medicinalplants.service;

import com.medicinalplants.dao.*;
import com.medicinalplants.model.*;

import java.util.List;

public class AdminService {
    private final PlantDAO plantDAO = new PlantDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final SymptomDAO symptomDAO = new SymptomDAO();
    private final PlantSymptomDAO plantSymptomDAO = new PlantSymptomDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean savePlant(Plant plant) {
        return plant.getId() == 0 ? plantDAO.insert(plant) : plantDAO.update(plant);
    }
    public boolean deletePlant(int id) { return plantDAO.delete(id); }

    public boolean saveCategory(Category category) {
        return category.getId() == 0 ? categoryDAO.insert(category) : categoryDAO.update(category);
    }
    public boolean deleteCategory(int id) { return categoryDAO.delete(id); }

    public boolean saveSymptom(Symptom symptom) {
        return symptom.getId() == 0 ? symptomDAO.insert(symptom) : symptomDAO.update(symptom);
    }
    public boolean deleteSymptom(int id) { return symptomDAO.delete(id); }

    public boolean addPlantSymptomMapping(int plantId, int symptomId) {
        return plantSymptomDAO.addMapping(plantId, symptomId);
    }
    public boolean removePlantSymptomMapping(int plantId, int symptomId) {
        return plantSymptomDAO.removeMapping(plantId, symptomId);
    }

    public List<User> getAllUsers() { return userDAO.findAll(); }
    public boolean updateUserRole(int userId, String role) {
        User user = userDAO.findAll().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
        if (user != null) {
            user.setRole(role);
            return userDAO.update(user);
        }
        return false;
    }
    public boolean deleteUser(int userId) { return userDAO.delete(userId); }
}

