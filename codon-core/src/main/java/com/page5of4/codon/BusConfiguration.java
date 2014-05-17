package com.page5of4.codon;

public interface BusConfiguration {
    String getApplicationName();

    String getOwnerAddress(String messageType);

    String getLocalAddress(String messageType);

    CommunicationConfiguration findCommunicationConfiguration(String name);

    ListenerConfiguration findListenerConfiguration(String name);

    public static class CommunicationConfiguration {
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

    public static class ListenerConfiguration {
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
}
