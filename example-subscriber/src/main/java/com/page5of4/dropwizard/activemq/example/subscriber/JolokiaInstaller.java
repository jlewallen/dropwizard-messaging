package com.page5of4.dropwizard.activemq.example.subscriber;

import io.dropwizard.setup.Environment;

public class JolokiaInstaller {
   public void install(Environment environment) {
      environment.servlets().addServlet("jolokia", new org.jolokia.http.AgentServlet()).addMapping("/jolokia/*");
   }
}
