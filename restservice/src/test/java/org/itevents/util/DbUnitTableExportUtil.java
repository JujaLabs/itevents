package org.itevents.util;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.itevents.dbunit.PostgisDataTypeFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by vaa25 on 28.09.2015.
 */
public class DbUnitTableExportUtil {

    private static final String PATH = "src/test/resources/dbunit/";
    private static IDatabaseConnection connection;

    public static void main(String[] args) throws Exception {

        databaseConnection();
//        writeDtdFile();
//        dependentTableExport("CityMapperTest", "city");
//        dependentTableExport("CurrencyMapperTest", "currency");
//        dependentTableExport("EventMapperTest", "event_technology");
//        dependentTableExport("TechnologyMapperTest", "technology");
//        dependentTableExport("UserMapperTest", "user_profile");
        dependentTableExport("VisitLogMapperTest", "visit_log");

    }

    private static void writeDtdFile() throws IOException, DataSetException, SQLException {
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream(PATH + "test.dtd"));
    }

    private static void dependentTableExport(String testName, String tableName) throws Exception {
        dependentTableExport(testName, testName, tableName);
    }

    private static void dependentTableExport(String testName, String methodName, String tableName) throws Exception {
        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
        String[] depTableNames = TablesDependencyHelper.getDependentTables(connection, tableName);
        IDataSet depDataSet = connection.createDataSet(depTableNames);
        File directory = new File(PATH + testName);
        directory.mkdirs();
        FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream(PATH + testName + "/" + methodName + "_initial.xml"));
        datasetWriter.setDocType(PATH + "test.dtd");
        datasetWriter.write(depDataSet);
    }

    private static void databaseConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException {
        Properties testProps = new Properties();
        testProps.load(new FileInputStream("src/main/resources/local.properties"));
        testProps.getProperty("database.driver");
        Class.forName(testProps.getProperty("database.driver"));
        Connection jdbcConnection = DriverManager.getConnection(
                testProps.getProperty("database.url"),
                testProps.getProperty("database.username"),
                testProps.getProperty("database.password"));
        connection = new DatabaseConnection(jdbcConnection);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgisDataTypeFactory());
    }
}