package be.abis.exercise.test;

import be.abis.exercise.dao.JdbcPersonDao;
import be.abis.exercise.dao.PersonDao;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

import java.sql.SQLException;

public class PersonDaoTransactionTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        PersonDao pdao = new JdbcPersonDao(cf);
        try {

            System.out.println("----------------ADDING new person new company ---------------");
            Address a1 = new Address("Oude Lindestraat", "70", "6411 EJ", "Heerlen");
            Company c1 = new Company("APG", "+3145123456", "NL12345688", a1);
            Person p1 = new Person("Jan", "Janssen", 36, "jan.janssen@apg.nl", "password1", "nl", c1);
            pdao.addPerson(p1);

            System.out.println("----------------ADDING MYSELF, Existing company ---------------");
            Address a2 = new Address("Diestsevest", "32 bus4b", "3000", "Leuven");
            Company c2 = new Company("ABIS N.V.", "+3216562410", "BE12345688", a2);
            Person p2 = new Person("Sandy", "Schillebeeckx", 42, "sschillebeeckx@abis.be", "password", "nl", c2);
            pdao.addPerson(p2);

            System.out.println("----------------ADDING PERSON WITHOUT COMPANY---------------");
            Person p3 = new Person("JEAN", "DUPONT", 53, "jdupont@gmail.com", "password", "fr");
            pdao.addPerson(p3);

            System.out.println("----------------ADDING WRONG PERSON WITH NEW COMPANY---------------");
            Address a4 = new Address("Haarlerbergweg", "13", "1101 CH", "Amsterdam");
            Company c4 = new Company("ING", "", "NL123456889", a4);
            Person p4 = new Person("Leo", "", 50, "leo@ing.nl", "password2", "nl", c4);
            pdao.addPerson(p4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
