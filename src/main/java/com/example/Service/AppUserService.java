package com.example.Service;

import com.example.dao.AppUserDao;
import com.example.entity.AppUser;
import com.example.entity.Group;

public class AppUserService {
    private AppUserDao appUserDao = new AppUserDao();

    public void createAppUser(AppUser appUser) {
        appUserDao.createAppUser(appUser.getUsername());
    }

    public AppUser readAppUser(int userId) {
        return appUserDao.readAppUser(userId);
    }

    public void deleteUser(int userId) {
        this.appUserDao.deleteUser(userId);
    }
}
