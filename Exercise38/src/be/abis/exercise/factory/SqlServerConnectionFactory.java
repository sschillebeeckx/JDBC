package be.abis.exercise.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnectionFactory extends ConnectionFactory {

    String url = "jdbc:sqlserver://tosfeb3fa\\SQLEXPRESS;database=training;encrypt=true;trustServerCertificate=true";
    String userName="tu00040";
    String password="tu00040";

    private static SqlServerConnectionFactory ocf;

    private SqlServerConnectionFactory() {}

    public static SqlServerConnectionFactory getInstance() {
        if(ocf==null)ocf=new SqlServerConnectionFactory();
        return ocf;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }
}
