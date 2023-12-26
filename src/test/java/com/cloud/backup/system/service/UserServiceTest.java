package com.cloud.backup.system.service;


import com.cloud.backup.system.dao.impl.UserDAO;
import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.model.impl.User;
import com.cloud.backup.system.security.AuthRequest;
import com.cloud.backup.system.security.AuthResponse;
import com.cloud.backup.system.service.impl.UserService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static jakarta.ws.rs.core.Response.Status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusComponentTest
public class UserServiceTest {
    @InjectMock
    UserDAO userDAO;

    @Inject
    UserService userService;

    AuthRequest authRequest;
    @BeforeEach
    public void init() {
        authRequest = mockAuthResquest();
    }


    @Test
    public void naoDeveRecuperarUsuario_IdNull() {
        try {
            userService.getById(null);
            fail("erro");
        } catch (CloudBusinessException e) {
            assertEquals("The 'id' cannot be null.", e.getMessage());
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void naoDeveRecuperarUsuario_NotNull() {
        Long id = 1L;
        try {
            userService.getById(id);
            fail("erro");
        } catch (CloudBusinessException e) {
            assertEquals("User not found. Please provide a valid user.", e.getMessage());
            assertEquals(Status.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    public void deveCriarUsuario() throws Exception {
        AuthResponse auth = userService.create(authRequest);
        Mockito.verify(userDAO, Mockito.times(1)).insert(Mockito.any(User.class));
    }

    private AuthRequest mockAuthResquest() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("teste@gmail.com");
        authRequest.setName("Maria");
        authRequest.setPassword("1234Abc");
        return authRequest;
    }
}
