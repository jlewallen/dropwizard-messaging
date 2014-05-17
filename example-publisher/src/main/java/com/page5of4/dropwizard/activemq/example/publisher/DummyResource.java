package com.page5of4.dropwizard.activemq.example.publisher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
* Created by jlewallen on 5/17/2014.
*/
@Path("/dummy")
@Produces(MediaType.APPLICATION_JSON)
public class DummyResource {
    @GET
    public String success() {
        return "success";
    }
}
