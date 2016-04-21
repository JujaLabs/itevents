package org.itevents.service.crawler.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.http.HttpStatus;
import org.itevents.service.exception.IntegrationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for HttpFetcher.
 */
@SuppressWarnings("PMD.LawOfDemeter")
public final class HttpFetcherTest {
    private static final String TEST_STRING = "Test string";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private int wiremockPort;
    private WireMockServer server;
    private HttpFetcher fetcher;

    @Before
    public void init() {
        wiremockPort =
            new IntegrationProperties("test-crawler-local.properties")
                .getInt("wiremock.port");
        final WireMockConfiguration config = WireMockConfiguration
            .wireMockConfig()
            .port(wiremockPort);
        server = new WireMockServer(config);
        server.start();
        WireMock.configureFor(server.port());

        fetcher = new HttpFetcher();

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/hoge.txt")).willReturn(
            WireMock.aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "text/plain")
                .withBody(HttpFetcherTest.TEST_STRING)));
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void shouldFetchUrl() {
        final String expected = HttpFetcherTest.TEST_STRING;
        final String query = String.format("http://localhost:%d/hoge.txt",
            wiremockPort);
        final String actual = fetcher.fetchAsString(query);
        Assert.assertEquals("Can't fetch url", expected, actual);
    }

    @Test
    public void shouldThrowIntegrationExceptionWhenTryToConnectToFaultLink() {
        final String query = String.format("http://localhost:%d/NOT_FOUND",
            wiremockPort);
        final String message = String.format("Can't download url " +
            "'%s' because of status HTTP/1.1 404 Not Found", query);
        thrown.expectMessage(message);
        thrown.expect(IntegrationException.class);
        fetcher.fetchAsString(query);
    }

    @Test
    public void shouldThrowIntegrationIoExceptionWhenSomethingWrong() {
        server.stop();
        final String query = String.format("http://localhost:%d/hoge.txt",
            wiremockPort);
        final String message = String.format("Can't download url " +
            "'%s' because of IOException", query);
        thrown.expectMessage(message);
        thrown.expect(IntegrationException.class);
        fetcher.fetchAsString(query);
    }

}
