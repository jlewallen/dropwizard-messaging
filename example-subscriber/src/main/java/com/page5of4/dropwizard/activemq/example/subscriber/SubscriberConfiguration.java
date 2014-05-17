package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.dropwizard.ConfiguresCodon;
import com.page5of4.dropwizard.activemq.ConfiguresMessageQueuing;
import io.dropwizard.Configuration;

public class SubscriberConfiguration extends Configuration implements ConfiguresMessageQueuing, ConfiguresCodon {
}
