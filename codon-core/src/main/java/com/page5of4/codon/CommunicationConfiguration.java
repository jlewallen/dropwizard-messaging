package com.page5of4.codon;

public class CommunicationConfiguration {
   private String componentName;
   private String url;

   public String getComponentName() {
      return componentName;
   }

   public void setComponentName(String componentName) {
      this.componentName = componentName;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public String toString() {
      return "[componentName=" + componentName + ", url=" + url + "]";
   }
}
