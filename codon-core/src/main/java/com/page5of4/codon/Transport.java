package com.page5of4.codon;

public interface Transport {
    void send(EndpointAddress address, Object message);

    void listen(EndpointAddress address);

    void unlisten(EndpointAddress address);
}
