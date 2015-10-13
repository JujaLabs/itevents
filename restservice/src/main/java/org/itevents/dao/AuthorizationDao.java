package org.itevents.dao;

import org.itevents.model.Authorization;

import java.util.List;

public interface AuthorizationDao {

    Authorization getAuthorization(int id);

    List<Authorization> getAllAuthorization();

    void addAuthorization(Authorization authorization);

    void removeAuthorization(Authorization authorization);
}