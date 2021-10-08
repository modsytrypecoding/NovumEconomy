package de.Initium.Eco.Main;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnector {
    public static Connection connection;
    public static String host = "localhost", database = "Economy", username = "Economy", password = "2Q-kV6Rx3yr]c2aI";
    public static int port = 3306;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(host);
        dataSource.setPort(port);
        dataSource.setDatabaseName(database);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        connection = dataSource.getConnection();

    }
}
