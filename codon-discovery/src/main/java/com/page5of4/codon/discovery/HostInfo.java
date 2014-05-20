package com.page5of4.codon.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostInfo {
   private final String address;
   private final String name;

   public String getAddress() {
      return address;
   }

   public String getName() {
      return name;
   }

   public HostInfo(String address, String name) {
      this.address = address;
      this.name = name;
   }

   public static HostInfo create() {
      try {
         String address = InetAddress.getLocalHost().getHostAddress();
         String name = InetAddress.getLocalHost().getHostName();
         return new HostInfo(address, name);
      }
      catch(UnknownHostException e) {
         throw new RuntimeException(e);
      }
   }
}
