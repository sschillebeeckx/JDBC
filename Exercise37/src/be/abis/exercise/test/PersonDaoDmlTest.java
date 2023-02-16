package be.abis.exercise.test;

import be.abis.exercise.dao.JdbcPersonDao;
import be.abis.exercise.dao.PersonDao;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;

import java.sql.SQLException;

public class PersonDaoDmlTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        PersonDao pdao = new JdbcPersonDao(cf);
        try {

        System.out.println("----------------ADDING MYSELF---------------");
        Address a = new Address("Diestsevest","32 bus4b","3000","Leuven");
        Company c = new Company(200,"Abis","+3216562410","BE12345688",a);
        Person p1 = new Person(200,"Sandy","Schillebeeckx",42,"sschillebeeckx@abis.be","password","nl",c);
        pdao.addPerson(p1);

        System.out.println("----------------ADDING PERSON WITHOUT COMPANY---------------");
        Person p2 = new Person(210,"JEAN","DUPONT",53,"jdupont@gmail.com","password","fr");
        pdao.addPerson(p2);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
