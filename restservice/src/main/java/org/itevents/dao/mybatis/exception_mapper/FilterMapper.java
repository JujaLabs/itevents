package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.FilterDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class FilterMapper extends SqlSessionDaoSupport implements FilterDao {
    @Override
    public Filter getFilter(int id) {
        Filter filter = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.FilterSqlMapper.getFilter", id);
        if (filter == null) {
            throw new EntityNotFoundDaoException("Filter with id = " + id + " not found");
        }
        return filter;
    }

    @Override
    public Filter getLastFilterByUser(User user) {
        Filter filter = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.FilterSqlMapper.getLastFilterByUser", user);
        if (filter == null) {
            throw new EntityNotFoundDaoException("Filter of user " + user + " not found");
        }
        return filter;
    }

    @Override
    public List<Filter> getAllFilters() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.FilterSqlMapper.getAllFilters");
    }

    @Override
    public void addFilter(Filter filter) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterSqlMapper.addFilter", filter);
    }

    @Override
    public void addFilterTechnology(Filter filter) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterSqlMapper.addFilterTechnology", filter);
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
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.FilterSqlMapper.addUserFilter",
                new UserFilter(user, filter));
    }
}
