package edu.upc.dsa.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/partida", description = "Endpoint to Partida Service")
@Path("/partida")
public class PartidaService {

    // Define your PartidaManager instance to handle Partida operations

    public PartidaService() {
        // Initialize your PartidaManager
    }

    // Implement endpoints for Partida operations (GET, POST, PUT, DELETE) using JAX-RS annotations
    // Example methods: getPartidas, getPartida, deletePartida, updatePartida, createPartida, etc.
}
