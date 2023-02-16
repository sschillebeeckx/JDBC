package be.abis.exercise.test;

import be.abis.exercise.dao.CourseDao;
import be.abis.exercise.dao.JdbcCourseDao;
import be.abis.exercise.dao.JdbcPersonDao;
import be.abis.exercise.dao.PersonDao;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;

import java.sql.SQLException;
import java.util.List;

public class PersonTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        PersonDao pdao = new JdbcPersonDao(cf);

        try {
            System.out.println("\n-------------ALL PERSONS--------------------");
            List<Person> allPersons = pdao.findAllPersons();
            System.out.println("nr of persons in table: " + allPersons.size());
            allPersons.forEach(System.out::println);

            System.out.println("\n-------------PERSON BY ID--------------------");
            System.out.println(pdao.findPersonById(3));
            System.out.println(pdao.findPersonById(135));

            System.out.println("\n-------------PERSONS BY LAST NAME STARTING WITH--------------------");
            pdao.findPersonsByLastName("S").forEach(System.out::println);

            System.out.println("\n-------------NUMBER OF PERSONS FOR COMPANY--------------------");
            System.out.println("Number of persons for ABIS = " + pdao.countPersonsForCompany("abis") + ".");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
