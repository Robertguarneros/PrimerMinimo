package edu.upc.dsa.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/juego", description = "Endpoint to Juego Service")
@Path("/juego")
public class JuegoService {

    // Define your JuegoManager instance to handle Juego operations

    public JuegoService() {
        // Initialize your JuegoManager
    }

    // Implement endpoints for Juego operations (GET, POST, PUT, DELETE) using JAX-RS annotations
    // Example methods: getJuegos, getJuego, deleteJuego, updateJuego, createJuego, etc.
}
