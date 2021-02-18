package io.mattnelson;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;


public class JettyContextPathConfiguration extends Configuration {
    @JsonProperty
    boolean contextPath = false;

    @JsonProperty
    boolean servletMapping = false;

    @JsonProperty
    boolean contextHandler = false;

    @JsonProperty
    boolean handlerWrapper = false;

    @JsonProperty
    boolean redirectFilter = false;

    @JsonProperty
    boolean redirectServlet = false;

    @JsonProperty
    boolean rewrite = false;
}
