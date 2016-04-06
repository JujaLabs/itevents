package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.exception.IntegrationException;

/**
 * Created by vaa25 on 24.03.2016.
 */
public final class IntegrationProperties {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Properties properties;

    public IntegrationProperties(final String filename) {
        this.properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(
            ClassLoader.getSystemResourceAsStream(filename), "UTF-8")) {
            this.properties.load(reader);
        } catch (final IOException exception) {
            final String message = String.format("%s not found", filename);
            IntegrationProperties.LOGGER.error(message);
            throw new IntegrationException(message, exception);
        }
    }

    public int getInt(final String key) {
        return Integer.parseInt(this.get(key));
    }

    public String get(final String key) {
        return this.properties.getProperty(key);
    }

}
