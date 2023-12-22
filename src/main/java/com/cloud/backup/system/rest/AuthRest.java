package com.cloud.backup.system.rest;


import com.cloud.backup.system.security.AuthRequest;
import com.cloud.backup.system.service.impl.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/auth")
public class AuthRest {

    @Inject
    UserService userService;

    @Context
    SecurityContext ctx;
    @POST
    @Path("/create")
    @PermitAll
    public Response createUser(AuthRequest authRequest) throws Exception {
        System.out.println(ctx);
        return Response.ok(userService.create(authRequest)).build();
    }

    @POST
    @Path("/login")
    public Response login(AuthRequest authRequest) throws Exception {
        return Response.ok(userService.login(authRequest)).build();
    }
}
