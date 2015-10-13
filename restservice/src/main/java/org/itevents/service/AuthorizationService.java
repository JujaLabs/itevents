package org.itevents.service;

import org.itevents.model.Authorization;

import java.util.List;

public interface AuthorizationService {

    void addAuthorization(Authorization authorization);

    Authorization getAuthorization(int id);

    List<Authorization> getAllAuthorization();

    Authorization removeAuthorization(Authorization authorization);
}