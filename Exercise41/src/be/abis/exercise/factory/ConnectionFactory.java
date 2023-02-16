package be.abis.exercise.factory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    public static ConnectionFactory createFactory(ConnectionType ct){
        switch(ct){
            case TEST: return H2ConnectionFactory.getInstance();
            case PRODUCTION: return OracleConnectionFactory.getInstance();
        }
        return null;
    }

    public abstract Connection createConnection() throws SQLException;

    public void closeConnection(Connection c) throws SQLException {
        if(c!=null)c.close();
    }

}
