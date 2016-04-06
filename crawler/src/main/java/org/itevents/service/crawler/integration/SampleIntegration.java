package org.itevents.service.crawler.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Created by vaa25 on 20.03.2016.
 */
public class SampleIntegration {
    private static final String EXAMPLE_HTML = "example.html";
    private static int wiremockPort;
    private static WireMockServer wireMockServer;

    public static void main(String[] args) throws Exception {
        SampleIntegration.wiremockPort = new IntegrationProperties("local.properties").getInt("wiremock.port");
        SampleIntegration.startWireMock();

        String html = new HttpFetcher().fetchAsString("http://localhost:" + SampleIntegration.wiremockPort + "/example");

        //do something with fetched html

        System.out.println(html);

        SampleIntegration.stopWireMock();
    }

    private static void stopWireMock() {
        SampleIntegration.wireMockServer.stop();
    }

    private static void startWireMock() {
        SampleIntegration.wireMockServer = new WireMockServer(SampleIntegration.wiremockPort);
        SampleIntegration.wireMockServer.start();

        String html = new StringLoader().load(SampleIntegration.EXAMPLE_HTML);

        stubFor(get(urlEqualTo("/example")).willReturn(
            aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody(html)));
    }
}
