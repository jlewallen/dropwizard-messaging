package com.page5of4.dropwizard.activemq.example.publisher;

import java.io.Serializable;
import java.util.UUID;

public class LaunchWorkMessage implements Serializable {
   private static final long serialVersionUID = 1L;
   private final UUID id;
   private final long index;

   public UUID getId() {
      return id;
   }

   public long getIndex() {
      return index;
   }

   public LaunchWorkMessage(UUID id, long index) {
      super();
      this.id = id;
      this.index = index;
   }

   @Override
   public String toString() {
      return "LaunchWorkMessage [id=" + id + ", index=" + index + "]";
   }
}
