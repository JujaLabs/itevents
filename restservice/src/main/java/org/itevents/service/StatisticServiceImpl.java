package org.itevents.service;

import org.itevents.mapper.StatisticMapper;

/**
 * @author Alexander Vlasov
 */
public class StatisticServiceImpl implements StatisticService {

    private StatisticMapper statisticMapper;

    public StatisticMapper getStatisticMapper() {
        return statisticMapper;
    }

    public void setStatisticMapper(StatisticMapper statisticMapper) {
        this.statisticMapper = statisticMapper;
    }

    @Override
    public void addClick(int eventId, int userId) {
        statisticMapper.addClick(eventId, userId);
    }
}
