package be.abis.exercise.test;

import be.abis.exercise.dao.JdbcPersonDao;
import be.abis.exercise.dao.PersonDao;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;

import java.sql.SQLException;

public class PersonSPTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        PersonDao pdao = new JdbcPersonDao(cf);

        try {

            System.out.println("\n-------------NUMBER OF PERSONS FOR COMPANY--------------------");
            System.out.println("Number of persons for ABIS = " + pdao.countPersonsForCompany("abis") + ".");

            System.out.println("\n-------------PERSONS BY LAST NAME STARTING WITH--------------------");
            pdao.findPersonsByLastName("S").forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
