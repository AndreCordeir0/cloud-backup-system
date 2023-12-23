package com.cloud.backup.system.service;


import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.service.impl.UserService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import static jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;

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
}
