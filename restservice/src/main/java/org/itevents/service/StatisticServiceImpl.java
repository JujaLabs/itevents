package org.itevents.service;

import org.itevents.mapper.StatisticMapper;

/**
 * @author Alexander Vlasov
 */
public class StatisticServiceImpl implements StatisticService {

    private StatisticMapper statisticMapper;

    @Override
    public void addClick(int event_id, int user_id) {
        statisticMapper.addClick(event_id, user_id);
    }
}
