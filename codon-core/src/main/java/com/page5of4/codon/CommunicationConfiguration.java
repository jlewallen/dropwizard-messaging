package com.page5of4.codon;

public class CommunicationConfiguration {
   private String componentName;

   public String getComponentName() {
      return componentName;
   }

   public void setComponentName(String componentName) {
      this.componentName = componentName;
   }

   @Override
   public String toString() {
      return "[componentName=" + componentName + "]";
   }
}
