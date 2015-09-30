package org.itevents.dbunit;


import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DbUnitDatabaseConnection implements FactoryBean<IDatabaseConnection> {

    @Inject
    private DataSource dataSource;

    private IDatabaseConnection databaseConnection;

    @PostConstruct
    public void init() {
        if (dataSource == null) {
            throw new IllegalArgumentException("The datasource is required");
        }
        initDatabaseConnection();
    }

    @Override
    public IDatabaseConnection getObject() throws Exception {
        return getDatabaseConnection();
    }

    @Override
    public Class<IDatabaseConnection> getObjectType() {
        return IDatabaseConnection.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public IDatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    private void initDatabaseConnection() {
        try {
            databaseConnection = new DatabaseDataSourceConnection(dataSource);
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}