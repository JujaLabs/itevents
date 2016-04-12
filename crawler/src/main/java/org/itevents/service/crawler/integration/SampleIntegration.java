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
public final class SampleIntegration implements Integration {
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
    public Void call() {
        this.startWireMock();
        final String html = new HttpFetcher().fetchAsString(
            String.format("http://localhost:%s/example", wiremockPort));
        result = new ArrayList<>(10);
        result.add(new Parser(html).parse());
        notifyObservers();
        stopWireMock();
        return null;
    }

    private void stopWireMock() {
        this.wireMockServer.stop();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void startWireMock() {
        wireMockServer.start();
        final String html = new StringFromFile(SampleIntegration.EXAMPLE_HTML_FILE).value();
        stubFor(get(urlEqualTo("/example")).willReturn(
            aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "text/plain")
                .withBody(html)));
    }

    @Override
    public void addObserver(final EngineObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (final EngineObserver observer : observers) {
            observer.handleEvent(result);
        }
    }
}
