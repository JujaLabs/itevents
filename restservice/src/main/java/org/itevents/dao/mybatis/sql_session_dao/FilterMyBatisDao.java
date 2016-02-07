package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.FilterDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("filterDao")
public class FilterMyBatisDao extends AbstractMyBatisDao implements FilterDao {
    @Override
    public Filter getFilter(int id) {
        Filter filter = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.FilterMapper.getFilter", id);
        if (filter == null) {
            throw new EntityNotFoundDaoException("Filter with id = " + id + " not found");
        }
        return filter;
    }

    @Override
    public Filter getLastFilterByUser(User user) {
        Filter filter = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.FilterMapper.getLastFilterByUser", user);
        if (filter == null) {
            throw new EntityNotFoundDaoException("Filter of user " + user + " not found");
        }
        return filter;
    }

    @Override
    public List<Filter> getAllFilters() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.FilterMapper.getAllFilters");
    }

    @Override
    public void addFilter(Filter filter) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterMapper.addFilter", filter);
    }

    @Override
    public void addFilterTechnology(Filter filter) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterMapper.addFilterTechnology", filter);
    }

    @Override
    public void addUserFilter(User user, Filter filter) {
        class UserFilter {
            private User user;
            private Filter filter;

            public UserFilter(User user, Filter filter) {
                this.user = user;
                this.filter = filter;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public Filter getFilter() {
                return filter;
            }

            public void setFilter(Filter filter) {
                this.filter = filter;
            }
        }
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterMapper.addUserFilter",
                new UserFilter(user, filter));
    }
}
