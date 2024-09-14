package com.bloger.web.books_market.services;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Data
@Service
public class RegistrationService {

    private List<JsonObject> credentialsList = new ArrayList<>();

    public void addCredential(String login, String password) {
        JsonObject credentials = new JsonObject();
        credentials.addProperty("login", login);
        credentials.addProperty("password", password);
        credentials.addProperty("isAuthentication", false);

        credentialsList.add(credentials);
        System.out.println(getAllCredentials());
    }

    public List<JsonObject> getAllCredentials() {
        return credentialsList;
    }

    private void setUnActiveFlag() {
        for (var obj : credentialsList) {
            obj.addProperty("isAuthentication", false);
        }
    }

    public boolean isAuthorized(String login, String password) {
        boolean userExists = false;

        for (var obj : credentialsList) {
            var objLogin = obj.get("login").getAsString();
            var objPassword = obj.get("password").getAsString();

            if (objLogin.equals(login)) {
                userExists = true;

                if (objPassword.equals(password)) {
                    setUnActiveFlag();
                    obj.addProperty("isAuthentication", true);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isAdministrator(String login, String password) {
        if (login.equals("admin") && password.equals("admin")) {
            JsonObject adminCredentials = new JsonObject();
            adminCredentials.addProperty("login", login);
            adminCredentials.addProperty("password", password);
            adminCredentials.addProperty("isAuthentication", true);
            credentialsList.add(adminCredentials);
            return true;
        }
        return false;
    }

    public void logOut(String login, String password) {
        if (login.equals("admin") && password.equals("admin")) {
            setUnActiveFlag();
        }
    }
}