package org.itevents.util.mail;

import org.apache.xalan.extensions.XSLProcessorContext;
import org.apache.xalan.templates.ElemExtensionCall;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("local.property")
public class BuilderUrl {

    @Value("${serverName}")
    private String serverName;
    @Value("${httpPort}")
    private String httpPort;

    String url;

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

    public String buildUrl(XSLProcessorContext context,
                           ElemExtensionCall elem) {
        //String server = elem.getAttribute("serverName");
        //String port = elem.getAttribute("httpPort");

        return url = String.valueOf(new StringBuilder("http://localhost:8080/users/activate/"));
        //return url = String.valueOf(new StringBuilder("http://" + server + ":" + port + "/users/activate/"));
    }
}