package org.itevents.service.crawler;

import java.util.concurrent.ExecutionException;

/**
 * Created by vaa25 on 10.03.2016.
 */
public final class CrawlerService {
    public void startCrawling()
        throws InterruptedException, ExecutionException {
        new Engine().run();
    }
}
