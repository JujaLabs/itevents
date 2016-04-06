package org.itevents.service.crawler.integration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vaa25 on 20.03.2016.
 */
public class StringLoader {
    private static final Logger LOGGER = LogManager.getLogger();

    public String load(String relativePath) {
        Path path = this.getPath(relativePath);
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            String message = "Can't load file " + relativePath;
            StringLoader.LOGGER.error(message);
            throw new RuntimeException(message, e);
        }
    }

    private Path getPath(String relativePath) {
        URL resource = ClassLoader.getSystemResource(relativePath);
        try {
            return Paths.get(resource.toURI()).toFile().toPath();
        } catch (URISyntaxException e) {
            String message = "Wrong filename " + relativePath;
            StringLoader.LOGGER.error(message);
            throw new RuntimeException(message, e);
        }
    }
}
