package org.itevents.util.mail;

import org.apache.xalan.extensions.XSLProcessorContext;
import org.apache.xalan.templates.ElemExtensionCall;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("local.property")
public class BuilderUrl {

    @Value("${serverName}")
    private static String serverName;
    @Value("${httpPort}")
    private static String httpPort;

    private static String url;

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

    public static String buildUrl(XSLProcessorContext context,
                           ElemExtensionCall elem) {

        return url = String.valueOf(new StringBuilder
                ("http://" + serverName + ":" + httpPort + "/users/activate/68eeea0d-f89d-4634-aa52-67da452eadb0"));
    }
}