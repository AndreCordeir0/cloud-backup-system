package com.cloud.backup.system.service.impl;

import com.cloud.backup.system.dao.impl.UserDAO;
import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.model.impl.User;
import com.cloud.backup.system.record.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;


@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    public Long create(){
        return null;
    }

    public void delete(){

    }

    public UserRecord update() {
        return null;
    }

    public UserRecord getById(Long id){
        if (id == null){
            throw new CloudBusinessException("The 'id' cannot be null.", Response.Status.BAD_REQUEST);
        }
        User user = userDAO.findById(id);
        if (user == null) {
            throw new CloudBusinessException("User not found. Please provide a valid user.", Response.Status.BAD_REQUEST);
        }
        return new UserRecord(user.getId(), user.getEmail(), user.getName(), user.getUserRoles(), user.getIsActive());
    }
}
