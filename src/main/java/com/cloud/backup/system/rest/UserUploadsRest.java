package com.cloud.backup.system.rest;

import com.cloud.backup.system.model.impl.FormData;
import com.cloud.backup.system.security.ClaimEnum;
import com.cloud.backup.system.service.impl.UserUploadsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/upload")
public class UserUploadsRest {

    @Inject
    UserUploadsService userUploadsService;

    @Inject
    JsonWebToken ctx;

    @POST
    @Path("/create-folder")
    @RolesAllowed({"USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFolder(String folderName) {
        return Response.ok(
                userUploadsService.createFolder(folderName, ctx.getClaim(ClaimEnum.ID.getClaim()))
        ).build();
    }

    @POST
    @Path("/stream")
    @RolesAllowed({"USER", "ADMIN"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveStream(@MultipartForm FormData formData) {
        return Response.ok(userUploadsService.saveStream(formData, ctx.getClaim(ClaimEnum.ID.getClaim()))).build();
    }
}
