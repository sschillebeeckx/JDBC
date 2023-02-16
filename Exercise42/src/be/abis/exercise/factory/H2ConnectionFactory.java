package be.abis.exercise.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionFactory extends ConnectionFactory {

    String url = "jdbc:h2:file:C:/temp/h2jdbc/h2db;AUTO_SERVER=true";
    String userName="h2user";
    String password="h2pass";
    //String password="wrongpass";

    private static H2ConnectionFactory h2cf;

    private H2ConnectionFactory() {}

    public static H2ConnectionFactory getInstance() {
        if(h2cf==null)h2cf=new H2ConnectionFactory();
        return h2cf;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }
}
