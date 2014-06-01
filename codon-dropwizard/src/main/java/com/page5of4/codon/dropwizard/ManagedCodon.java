package com.page5of4.codon.dropwizard;

import com.page5of4.codon.BusModule;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ManagedCodon implements Managed {
   private static final Logger logger = LoggerFactory.getLogger(ManagedCodon.class);
   private ApplicationContext applicationContext;
   private BusModule busModule;

   public ManagedCodon(ApplicationContext applicationContext, BusModule busModule) {
      this.applicationContext = applicationContext;
      this.busModule = busModule;
   }

   @Override
   public void start() throws Exception {
      busModule.start();
   }

   @Override
   public void stop() throws Exception {
      busModule.stop();
   }
}
