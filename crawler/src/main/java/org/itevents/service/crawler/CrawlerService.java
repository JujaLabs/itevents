package org.itevents.service.crawler;

/**
 * Created by vaa25 on 10.03.2016.
 */
public class CrawlerService {
    public void startCrawling() throws InterruptedException {
        new Engine().run();
    }
}
