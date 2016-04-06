package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
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
            final HttpResponse execute = client.execute(new HttpGet(url));
            final StatusLine status = execute.getStatusLine();
            final int code = status.getStatusCode();
            if (code != HttpStatus.SC_OK) {
                final String message = String.format(
                    "Can't download url '%s' because of status %s", url,
                    status.toString());
                HttpFetcher.LOGGER.error(message);
                throw new IntegrationException(message, null);
            }
            return EntityUtils.toString(execute.getEntity(),
                Charset.defaultCharset());
        } catch (final IOException exception) {
            final String message =
                String.format("Can't download url '%s' because of IOException",
                    url);
            HttpFetcher.LOGGER.error(message);
            throw new IntegrationException(message, exception);
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
    }

}
