package be.abis.exercise.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionTest {

    public static void main(String[] args) {

        String url = "jdbc:h2:file:C:/temp/hjdbc/h2db;AUTO_SERVER=TRUE";
        try (Connection connection = DriverManager.getConnection(url,"h2user","h2pass")){
            System.out.println("connection succeeded via " + connection.getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
