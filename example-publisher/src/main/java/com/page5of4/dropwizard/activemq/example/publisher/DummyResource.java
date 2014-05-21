package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.dropwizard.discovery.zookeeper.ServiceInstanceRecord;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.joda.time.DateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

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

   @GET
   @Path("publish")
   public DateTime publish() {
      return DateTime.now();
   }

   @GET
   @Path("services")
   public Collection<ServiceInstanceRecord> services() {
      return ServiceRegistry.get().getServices(ServiceInstanceRecord.class);
   }
}
