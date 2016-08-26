package org.lra.test.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by laurenra on 8/23/16.
 */
public class oracleConnection implements DBConnection {

    private String testSqlStatement = "select sysdate from dual";
    private Connection connection = null;
    private long startTimeNano;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean openConnection(String dbUrl, String username, String password) {

//        BoneCP connectionPool = null;

        // Load the JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("ERROR: can't find driver " + e.getMessage());
            return false;
        }

        System.out.println("Oracle JDBC driver registered.");

        // Set up connection pool to database and get connection
        // using BoneCP 0.8 because it's fast (http://www.jolbox.com/)
//        BoneCPConfig config = new BoneCPConfig();
//        config.setJdbcUrl(dbUrl);
//        config.setUsername(username);
//        config.setPassword(password);
//        config.setMinConnectionsPerPartition(5);
//        config.setMaxConnectionsPerPartition(10);
//        config.setPartitionCount(1);

        try {
            startTimeNano = System.nanoTime();
            connection = DriverManager.getConnection(dbUrl, username, password);

//            connectionPool = new BoneCP(config);
//            connection = connectionPool.getConnection();
            System.out.println("getConnection - elapsed seconds: " + getElapsedSeconds(startTimeNano));
        }
        catch (SQLException e) {
            System.out.println("getConnection FAIL - elapsed seconds: " + getElapsedSeconds(startTimeNano));
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }

        if (connection != null) {
            System.out.println("Got the connection.  Congratulations!"); // testing only
        }
        else {
            System.out.println("Failed to make connection."); // testing only
            return false;
        }

        return true;
    }

    @Override
    public boolean isValidConnection() {
        try {
            Statement statement = connection.createStatement();
            startTimeNano = System.nanoTime();
            ResultSet resultSet = statement.executeQuery(testSqlStatement);
            System.out.println("executeQuery - elapsed seconds: " + getElapsedSeconds(startTimeNano));
            System.out.println("Test SQL statement: " + testSqlStatement);
            while(resultSet.next()) {
                System.out.println("Test SQL result: " + resultSet.getString("sysdate"));
            }
        }
        catch (SQLException e) {
            System.out.println("executeQuery FAIL - elapsed seconds: " + getElapsedSeconds(startTimeNano));
            System.out.println("ERROR: did not execute SQL statement.");
            System.out.println(e.getMessage());
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        else return false;
    }

    private static BigDecimal getElapsedSeconds(long startTime) {
        BigDecimal elapsedNanoSecs = new BigDecimal(System.nanoTime() - startTime);
        return elapsedNanoSecs.scaleByPowerOfTen(-9);
    }

}
