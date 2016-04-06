package org.itevents.service.crawler.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for HttpFetcher.
 */
public class HttpFetcherTest {
    private static final String TEST_STRING = "Test string";
    private final int wiremockPort;

    private WireMockServer wireMockServer;
    private HttpFetcher httpFetcher;

    public HttpFetcherTest() {
        this.wiremockPort = new IntegrationProperties("test-crawler-local.properties").getInt("wiremock.port");
    }

    @Before
    public void init() {
        WireMockConfiguration config = wireMockConfig().port(this.wiremockPort);
        this.wireMockServer = new WireMockServer(config);
        this.wireMockServer.start();
        WireMock.configureFor(this.wireMockServer.port());

        this.httpFetcher = new HttpFetcher();

        stubFor(get(urlEqualTo("/hoge.txt")).willReturn(
            aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody(HttpFetcherTest.TEST_STRING)));
    }

    @After
    public void tearDown() {
        this.wireMockServer.stop();
    }

    @Test
    public void ok() throws Exception {
        String actual = this.httpFetcher.fetchAsString("http://localhost:" + this.wiremockPort + "/hoge.txt");
        String expected = HttpFetcherTest.TEST_STRING;
        assertThat(actual, is(expected));
    }

    @Test(expected = RuntimeException.class)
    public void notFound() throws Exception {
        this.httpFetcher.fetchAsString("http://localhost:" + this.wiremockPort + "/NOT_FOUND");
    }

}
