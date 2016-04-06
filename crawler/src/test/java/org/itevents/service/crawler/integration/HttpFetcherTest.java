package org.itevents.service.crawler.integration;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.http.HttpStatus;
import org.itevents.service.exception.IntegrationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for HttpFetcher.
 */
public final class HttpFetcherTest {
    private static final String TEST_STRING = "Test string";
    private int wiremockPort;

    private WireMockServer server;
    private HttpFetcher fetcher;

    @Before
    public void init() {
        this.wiremockPort =
            new IntegrationProperties("test-crawler-local.properties")
                .getInt("wiremock.port");
        final WireMockConfiguration config = WireMockConfiguration
            .wireMockConfig()
            .port(this.wiremockPort);
        this.server = new WireMockServer(config);
        this.server.start();
        WireMock.configureFor(this.server.port());

        this.fetcher = new HttpFetcher();

        stubFor(get(urlEqualTo("/hoge.txt")).willReturn(
            aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "text/plain")
                .withBody(HttpFetcherTest.TEST_STRING)));
    }

    @After
    public void tearDown() {
        this.server.stop();
    }

    @Test
    public void shouldFetchUrl() {
        final String expected = HttpFetcherTest.TEST_STRING;
        final String actual = this.fetcher.fetchAsString(
            String.format("http://localhost:%d/hoge.txt", this.wiremockPort));
        Assert.assertEquals("Can't fetch url", expected, actual);
    }

    @Test(expected = IntegrationException.class)
    public void shouldThrowIntegrationException() {
        this.fetcher.fetchAsString(
            String.format("http://localhost:%d/NOT_FOUND", this.wiremockPort));
    }

}
