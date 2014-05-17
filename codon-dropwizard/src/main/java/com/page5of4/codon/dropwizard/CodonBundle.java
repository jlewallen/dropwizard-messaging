package com.page5of4.codon.dropwizard;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CodonBundle implements ConfiguredBundle<ConfiguresCodon> {
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(ConfiguresCodon configuration, Environment environment) throws Exception {
        environment.lifecycle().manage(new ManagedCodon());
    }

}
