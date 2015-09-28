package org.itevents;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

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

//        partialDatabaseExport();
//
//        fullDatabaseExport();

//        writeDtdFile();

        dependentTableExport("CityMapperTest", "city");

    }

    private static void writeDtdFile() throws IOException, DataSetException, SQLException {
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("test.dtd"));
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
        FlatXmlDataSet.write(depDataSet, new FileOutputStream(PATH + testName + "/" + methodName + "_initial.xml"));
    }

    private static void fullDatabaseExport() throws SQLException, IOException, DataSetException {
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
    }

    private static void partialDatabaseExport() throws IOException, DataSetException {
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
        partialDataSet.addTable("BAR");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));
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
    }
}