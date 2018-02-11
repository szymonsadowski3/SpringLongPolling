package pl.edu.agh.kis.application.service;

import pl.edu.agh.kis.application.dao.AppUserDao;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.external.BCrypt;

public class AppUserService {
    private AppUserDao appUserDao = new AppUserDao();

    public boolean createAppUser(String username, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        return appUserDao.createAppUser(username, hashed);
    }

    public boolean verifyAppUser(String username, String password) {
        return appUserDao.verifyAppUser(username, password);
    }

    public AppUser readAppUser(int userId) {
        return appUserDao.readAppUser(userId);
    }

    public void deleteUser(int userId) {
        this.appUserDao.deleteUser(userId);
    }
}
