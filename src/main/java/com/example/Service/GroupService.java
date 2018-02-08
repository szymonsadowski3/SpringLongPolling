package com.example.Service;

import com.example.dao.GroupDao;
import com.example.entity.Group;

public class GroupService {
    private GroupDao groupDao = new GroupDao();

    public void createGroup(Group group) {
        this.groupDao.createGroup(group.getGroupName());
    }

    public Group readGroup(int groupId) {
        return this.groupDao.readGroup(groupId);
    }

    public void deleteGroup(int groupId) {
        this.groupDao.deleteGroup(groupId);
    }
}
