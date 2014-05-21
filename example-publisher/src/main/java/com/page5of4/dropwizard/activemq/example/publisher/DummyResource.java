package com.page5of4.dropwizard.activemq.example.publisher;

import com.page5of4.codon.Bus;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceInstanceRecord;
import com.page5of4.dropwizard.discovery.zookeeper.ServiceRegistry;
import org.joda.time.DateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jlewallen on 5/17/2014.
 */
@Path("/dummy")
@Produces(MediaType.APPLICATION_JSON)
public class DummyResource {
   private Bus bus;

   public DummyResource(Bus bus) {
      this.bus = bus;
   }

   @GET
   public String success() {
      return "success";
   }

   @GET
   @Path("publish")
   public DateTime publish() {
      bus.publish(new LaunchWorkMessage(UUID.randomUUID(), new Random().nextInt()));
      return DateTime.now();
   }

   @GET
   @Path("services")
   public Collection<ServiceInstanceRecord> services() {
      return ServiceRegistry.get().getServices(ServiceInstanceRecord.class);
   }
}
