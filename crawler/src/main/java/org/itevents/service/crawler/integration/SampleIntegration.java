package org.itevents.service.crawler.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.itevents.service.crawler.Entity;
import org.itevents.service.crawler.interfaces.EngineObserver;
import org.itevents.service.crawler.interfaces.Integration;

/**
 * Created by vaa25 on 20.03.2016.
 */
public class SampleIntegration implements Integration {
    private static final String EXAMPLE_HTML_FILE = "example.html";
    private final int wiremockPort;
    private final WireMockServer wireMockServer;
    private final List<EngineObserver> observers;
    private List<Entity> result;

    public SampleIntegration() {
        this.wiremockPort = new IntegrationProperties("crawler-local.properties")
            .getInt("wiremock.port");
        this.observers = new ArrayList<>();
        this.wireMockServer = new WireMockServer(this.wiremockPort);
    }

    @Override
    public String getIntegrationName() {
        return "Sample Integration";
    }

    @Override
    public void run() {

        this.startWireMock();
        String html = new HttpFetcher().fetchAsString(
            String.format("http://localhost:%s/example", this.wiremockPort));
        System.out.println("--------------");
        //do something with fetched html
        Entity entity = new Parser(html).parse();
        this.result = new ArrayList<>();
        this.result.add(entity);
        this.notifyObservers();
        System.out.println(html);

        this.stopWireMock();
    }

    private void stopWireMock() {
        this.wireMockServer.stop();
    }

    private void startWireMock() {


        String html = new StringLoader().load(SampleIntegration.EXAMPLE_HTML_FILE);
//        System.out.println(html);

        stubFor(get(urlEqualTo("/example")).willReturn(
            aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "text/plain")
                .withBody(html)));
        this.wireMockServer.start();
    }

    @Override
    public void addObserver(final EngineObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (EngineObserver observer : this.observers) {
            observer.handleEvent(this.result);
        }
    }
}
