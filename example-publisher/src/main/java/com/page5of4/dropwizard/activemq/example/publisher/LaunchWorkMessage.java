package com.page5of4.dropwizard.activemq.example.publisher;

import java.io.Serializable;
import java.util.UUID;

public class LaunchWorkMessage implements Serializable {
   private static final long serialVersionUID = 1L;
   private final UUID id;
   private final long index;
   private final Integer workSize;

   public UUID getId() {
      return id;
   }

   public long getIndex() {
      return index;
   }

   public Integer getWorkSize() {
      return workSize;
   }

   public LaunchWorkMessage(UUID id, long index, Integer workSize) {
      super();
      this.id = id;
      this.index = index;
      this.workSize = workSize;
   }

   @Override
   public String toString() {
      return "LaunchWorkMessage{" + "id=" + id + ", index=" + index + ", workSize=" + workSize + '}';
   }
}
