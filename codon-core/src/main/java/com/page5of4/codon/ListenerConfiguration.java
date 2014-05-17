package com.page5of4.codon;

public class ListenerConfiguration {
   private String id;
   private String listenAddress;
   private String poisonAddress;
   private Boolean transacted;
   private Integer concurrency;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getListenAddress() {
      return listenAddress;
   }

   public void setListenAddress(String listenAddress) {
      this.listenAddress = listenAddress;
   }

   public String getPoisonAddress() {
      return poisonAddress;
   }

   public void setPoisonAddress(String poisonAddress) {
      this.poisonAddress = poisonAddress;
   }

   public Boolean getTransacted() {
      return transacted;
   }

   public void setTransacted(Boolean transacted) {
      this.transacted = transacted;
   }

   public Integer getConcurrency() {
      return concurrency;
   }

   public void setConcurrency(Integer concurrency) {
      this.concurrency = concurrency;
   }
}
