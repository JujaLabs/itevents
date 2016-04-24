package org.itevents.utils.dbunit;


import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.beans.factory.FactoryBean;

/**
 * dbUnit/postgis bridge.
 */
@SuppressWarnings("PMD")
public class DbUnitDatabaseConnection
    implements FactoryBean<IDatabaseConnection> {

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

    private void initDatabaseConnection() {
        try {
            databaseConnection = new DatabaseDataSourceConnection(dataSource);
            databaseConnection.getConfig().setProperty(
                DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new PostgisDataTypeFactory());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IDatabaseConnection getObject() throws Exception {
        return databaseConnection;
    }

    @Override
    public Class<IDatabaseConnection> getObjectType() {
        return IDatabaseConnection.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
