package be.abis.exercise.test;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {

        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.TEST);
        //ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);

        try {
            Connection connection = cf.createConnection();
            System.out.println("connection succeeded via " + connection.getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }


    }
}
