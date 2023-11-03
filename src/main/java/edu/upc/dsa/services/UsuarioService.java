package edu.upc.dsa.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UsuarioService {

    // Define your UserManager instance to handle User operations

    public UsuarioService() {
        // Initialize your UserManager
    }

    // Implement endpoints for User operations (GET, POST, PUT, DELETE) using JAX-RS annotations
    // Example methods: getUsers, getUser, deleteUser, updateUser, createUser, etc.
}
