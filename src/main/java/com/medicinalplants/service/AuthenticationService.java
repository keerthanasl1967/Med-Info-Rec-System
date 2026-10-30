package com.medicinalplants.service;

import com.medicinalplants.PasswordUtil;
import com.medicinalplants.UserSession;
import com.medicinalplants.dao.UserDAO;
import com.medicinalplants.model.User;

public class AuthenticationService {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(String username, String email, String password) {
        if (userDAO.findByUsername(username) != null || userDAO.findByEmail(email) != null) {
            return false;
        }
        String passwordHash = PasswordUtil.hashPassword(password);
        User user = new User(0, username, email, passwordHash, "USER", null);
        return userDAO.insert(user);
    }

    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPasswordHash())) {
            UserSession.setCurrentUser(user);
            return true;
        }
        return false;
    }

    public void logout() {
        UserSession.clearSession();
    }
}
