package org.itevents.test_utils.dbunit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.util.search.SearchException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class helps to create dbunit xml data for test annotations @DatabaseSetup, @ExpectedDatabase, @DatabaseTearDown
 * To rewrite dtd file uncomment "writeDtdFile();". Rewriting dtd is need when changes schema of database. When xml file
 * consumed by @ExpectedDatabase with operation NON_STRICT_UNORDERED or NON_STRICT you will need to delete dtd reference
 * from xml file.
 *
 * Created by vaa25 on 28.09.2015.
 */

public class DbUnitTableExportUtil {

    private static final String PATH = "src/test/resources/dbunit/";
    private static IDatabaseConnection connection;

    public static void main(String[] args) {

        databaseConnection();
        dependentTableExport("FilterMapperTest", "FilterMapperTest", "user_filter", "filter_technology");
    }

    /**
     * Writes dbunit xml data file filled with data from specified tables and all tables that Primary Keys are in the
     * specified tables as Foreign Keys. Path of the file is "src/test/resources/dbunit/${testName}/". Name of the file
     * is "${methodName}_initial.xml". Use it if file will be consumed by one method
     * @param testName - name of test class that consumes this file
     * @param methodName - name of test method that consumes this file
     * @param tableName - specified tables
     */
    private static void dependentTableExport(String testName, String methodName, String... tableName) {
        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
        try {
            String[] depTableNames = TablesDependencyHelper.getDependentTables(connection, tableName);
            IDataSet depDataSet = connection.createDataSet(depTableNames);
            File directory = new File(PATH + testName);
            directory.mkdirs();
            FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream(PATH + testName + "/" + methodName + "_initial.xml"));
            datasetWriter.setDocType(PATH + "test.dtd");
            datasetWriter.write(depDataSet);
        } catch (SearchException | DataSetException | SQLException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void databaseConnection() {
        Properties testProps = new Properties();
        try{
        testProps.load(new FileInputStream("src/main/resources/local.properties"));
        Class.forName(testProps.getProperty("database.driver"));
        Connection jdbcConnection = DriverManager.getConnection(
                testProps.getProperty("database.url"),
                testProps.getProperty("database.username"),
                testProps.getProperty("database.password"));
        connection = new DatabaseConnection(jdbcConnection);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgisDataTypeFactory());
        }catch (IOException | ClassNotFoundException | SQLException | DatabaseUnitException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}