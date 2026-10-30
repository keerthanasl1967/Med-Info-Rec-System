package com.medicinalplants.service;

import com.medicinalplants.UserSession;
import com.medicinalplants.dao.FavoriteDAO;
import com.medicinalplants.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class FavoriteService {
    private final FavoriteDAO favoriteDAO = new FavoriteDAO();

    public boolean toggleFavorite(int plantId) {
        if (!UserSession.isLoggedIn()) return false;
        int userId = UserSession.getCurrentUser().getId();
        if (favoriteDAO.isFavorite(userId, plantId)) {
            return favoriteDAO.removeFavorite(userId, plantId);
        } else {
            return favoriteDAO.addFavorite(userId, plantId);
        }
    }

    public boolean isFavorite(int plantId) {
        if (!UserSession.isLoggedIn()) return false;
        return favoriteDAO.isFavorite(UserSession.getCurrentUser().getId(), plantId);
    }

    public List<Plant> getUserFavorites() {
        if (!UserSession.isLoggedIn()) return new ArrayList<>();
        return favoriteDAO.getFavoritesForUser(UserSession.getCurrentUser().getId());
    }

    public int getUserFavoriteCount() {
        if (!UserSession.isLoggedIn()) return 0;
        return favoriteDAO.countForUser(UserSession.getCurrentUser().getId());
    }
}
