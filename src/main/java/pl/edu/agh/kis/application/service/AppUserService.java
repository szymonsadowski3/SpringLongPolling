package pl.edu.agh.kis.application.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.kis.application.dao.AppUserDao;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.external.BCrypt;

/**
 * "Middleware" which operates on AppUser Data Access Object in order to perform certain operations
 */
@Service
public class AppUserService {
    private AppUserDao appUserDao = new AppUserDao();

    /**
     * @param username User's name
     * @param password Password for user
     * @return true if operation of creation succeeded, false otherwise
     */
    public boolean createAppUser(String username, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        return appUserDao.createAppUser(username, hashed);
    }

    /**
     * @param username user's name
     * @param password password typed in by user
     * @return result of verification
     */
    public boolean verifyAppUser(String username, String password) {
        return appUserDao.verifyAppUser(username, password);
    }

    /**
     * @param userId unique identificator of user
     * @return Instance of AppUser class
     */
    public AppUser readAppUser(int userId) {
        return appUserDao.readAppUser(userId);
    }

    /**
     * @param userId Id of user which is going to be deleted
     */
    public void deleteUser(int userId) {
        this.appUserDao.deleteUser(userId);
    }
}
