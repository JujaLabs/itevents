package org.itevents;

import org.itevents.mapper.StatisticMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ramax on 7/15/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class StatisticEventMaperTest {

    @Autowired
    StatisticMapper statisticMapper;

    @Test
    public void testStaticMapper() {
       statisticMapper.addClick(1,1);
       System.out.println(statisticMapper.selectStatistic(1));
       //statisticMapper.deleteClick(1);
    }

}
