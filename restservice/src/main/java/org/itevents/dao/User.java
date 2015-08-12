package org.itevents.dao;

import java.util.List;

/**
 * Created by Well on 29.07.2015.
 */
public interface User {

    Long create(User user);
    User read(Long id);
    boolean update(User user);
    boolean delete(User user);
    List findAll();

}
