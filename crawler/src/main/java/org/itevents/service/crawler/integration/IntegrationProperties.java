package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vaa25 on 24.03.2016.
 */
public final class IntegrationProperties {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Properties properties;

    public IntegrationProperties(final String propertiesFileName) {
        this.properties = new Properties();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(propertiesFileName);
        try (InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {
            this.properties.load(reader);
        } catch (IOException e) {
            String message = propertiesFileName + " not found";
            IntegrationProperties.LOGGER.error(message);
            throw new RuntimeException(message, e);
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(this.get(key));
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }

}
