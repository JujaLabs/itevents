package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.exception.IntegrationException;

/**
 * Created by vaa25 on 20.03.2016.
 */
public final class StringFromFile {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String value;

    public StringFromFile(final String relative) {
        value = load(relative);
    }

    private String load(final String relative) {
        try {
            return new String(
                Files.readAllBytes(getPath(relative)), "UTF-8");
        } catch (final IOException exception) {
            final String message = String.format("Can't load file %s", relative);
            LOGGER.error(message);
            throw new IntegrationException(message, exception);
        }
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Path getPath(final String relative) {
        try {
            return Paths
                .get(ClassLoader.getSystemResource(relative).toURI())
                .toFile()
                .toPath();
        } catch (final URISyntaxException exception) {
            final String message = String.format("Wrong filename %s", relative);
            LOGGER.error(message);
            throw new IntegrationException(message, exception);
        }
    }

    public String getValue() {
        return value;
    }
}
