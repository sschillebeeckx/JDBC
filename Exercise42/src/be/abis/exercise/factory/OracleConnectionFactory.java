package be.abis.exercise.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionFactory extends ConnectionFactory {

    String url = "jdbc:oracle:thin:@//delphi:1521/TSTA";
    String userName="tu00030";
    String password="tu00030";
    //String password="wrong";

    private static OracleConnectionFactory ocf;

    private OracleConnectionFactory() {}

    public static OracleConnectionFactory getInstance() {
        if(ocf==null)ocf=new OracleConnectionFactory();
        return ocf;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }
}
