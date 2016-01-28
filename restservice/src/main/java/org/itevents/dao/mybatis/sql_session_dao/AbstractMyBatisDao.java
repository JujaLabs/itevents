package org.itevents.dao.mybatis.sql_session_dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by vaa25 on 28.01.2016.
 */
@Component
public class AbstractMyBatisDao extends SqlSessionDaoSupport {
    @Inject
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @PostConstruct
    public void init() throws Exception {
        setSqlSessionFactory(sqlSessionFactoryBean.getObject());
    }
}
