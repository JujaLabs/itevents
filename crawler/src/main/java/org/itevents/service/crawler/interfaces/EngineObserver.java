package org.itevents.service.crawler.interfaces;

import java.util.Collection;
import org.itevents.service.crawler.Entity;

/**
 * Created by vaa25 on 06.04.2016.
 */
public interface EngineObserver {

    void handleEvent(Collection<Entity> entities);
}
