package de.Initium.Eco.Main;


import com.mysql.cj.jdbc.MysqlDataSource;
import de.Initium.Eco.Dispatcher.MainDis;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnector {
    public static Connection connection;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(MainDis.Host);
        dataSource.setPort(MainDis.Port);
        dataSource.setDatabaseName(MainDis.DataBase);
        dataSource.setUser(MainDis.UserName);
        dataSource.setPassword(MainDis.Password);

        connection = dataSource.getConnection();

    }
}
