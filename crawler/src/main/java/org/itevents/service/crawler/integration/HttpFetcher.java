package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.exception.IntegrationException;

/**
 * Created by roma on 20.03.16.
 */
public final class HttpFetcher {
    private static final Logger LOGGER = LogManager.getLogger();

    public String fetchAsString(final String url) {
        final HttpClient client = HttpClients.createDefault();
        try {
            return getStringFromWeb(url, client);
        } catch (final IOException exception) {
            final String message =
                String.format("Can't download url '%s' because of IOException %s",
                    url, exception.getMessage());
            LOGGER.error(message);
            throw new IntegrationException(message, exception);
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
    }

    private String getStringFromWeb(final String url, final HttpClient client) throws IOException {
        final ResponseWrapper response = new ResponseWrapper(
            client.execute(new HttpGet(url)));
        if (!response.isOk()) {
            final String message = String.format(
                "Can't download url '%s' because of status %s", url,
                response.getStatusString());
            LOGGER.error(message);
            throw new IntegrationException(message, null);
        }
        return EntityUtils.toString(response.getEntity(),
            Charset.defaultCharset());
    }

}
