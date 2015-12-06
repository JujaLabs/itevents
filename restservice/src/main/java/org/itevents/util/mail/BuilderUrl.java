package org.itevents.util.mail;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class BuilderUrl {

    private String url;

    public BuilderUrl() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void buildUrl() throws IOException {
        Properties prop = new Properties();

        prop.load(getClass().getClassLoader().getResourceAsStream("local.properties"));

        String serverName = prop.getProperty("serverName");
        String httpPort = prop.getProperty("httpPort");

        url = String.valueOf(new StringBuilder("http://" + serverName + ":" + httpPort));
    }
}