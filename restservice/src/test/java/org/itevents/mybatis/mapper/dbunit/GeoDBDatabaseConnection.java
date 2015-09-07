package org.itevents.mybatis.mapper.dbunit;


import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

public class GeoDBDatabaseConnection implements FactoryBean<IDatabaseConnection> {

    private DataSource dataSource;

    private IDatabaseConnection databaseConnection;

    public GeoDBDatabaseConnection() {
    }

    public GeoDBDatabaseConnection(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
            this.databaseConnection = new DatabaseConnection(dataSource.getConnection());
            this.databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }
    }

}