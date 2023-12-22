package com.cloud.backup.system.service.impl;

import com.cloud.backup.system.dao.impl.UserDAO;
import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.model.impl.User;
import com.cloud.backup.system.model.impl.UserRoles;
import com.cloud.backup.system.record.UserRecord;
import com.cloud.backup.system.security.AuthRequest;
import com.cloud.backup.system.security.AuthResponse;
import com.cloud.backup.system.security.PBKDF2Encoder;
import com.cloud.backup.system.security.TokenUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    @Inject
    PBKDF2Encoder pbkdf2Encoder;

    @ConfigProperty(name = "com.cloud.backup.system.jwt.duration")
    Long tokenDuration;

    @Transactional(Transactional.TxType.REQUIRED)
    public AuthResponse create(AuthRequest authRequest) throws Exception {
        if (
                authRequest.getName() == null || authRequest.getEmail() == null || authRequest.getPassword() == null
        ) {
            throw new CloudBusinessException(
                    "Invalid user. Please provide values for name, email, and password.",
                    Response.Status.BAD_REQUEST
            );
        }
        var hash = pbkdf2Encoder.encode(authRequest.getPassword());
        User user = new User(
                authRequest.getEmail(), hash, authRequest.getName(), UserRoles.USER
        );
        userDAO.insert(user);

        var token = TokenUtils.generateToken(user.getName(),user.getUserRoles(), tokenDuration, null);
        return AuthResponse.token(token);
    }

    public void delete() {

    }

    public UserRecord update() {
        return null;
    }

    public UserRecord getById(Long id) {
        if (id == null) {
            throw new CloudBusinessException("The 'id' cannot be null.", Response.Status.BAD_REQUEST);
        }
        User user = userDAO.findById(id, User.class);
        if (user == null) {
            throw new CloudBusinessException("User not found. Please provide a valid user.", Response.Status.BAD_REQUEST);
        }
        return new UserRecord(user.getId(), user.getEmail(), user.getName(), user.getUserRoles(), user.getIsActive());
    }
}
