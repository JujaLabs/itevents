package org.itevents.util.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@PropertySource("local.property")
public class BuilderUrl {

    @Value("${serverName}")
    private String serverName;
    @Value("${httpPort}")
    private String httpPort;

    public BuilderUrl() {
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    public void buildUrl() throws IOException {
        String url = String.valueOf(new StringBuilder("http://" + serverName + ":" + httpPort));
    }
}