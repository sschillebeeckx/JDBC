package be.abis.exercise.test;

import be.abis.exercise.dao.CourseDao;
import be.abis.exercise.dao.JdbcCourseDao;
import be.abis.exercise.exception.CourseAlreadyExistsException;
import be.abis.exercise.exception.CourseNotFoundException;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Course;

import java.sql.SQLException;

public class CourseExceptionTest {

    public static void main(String[] args) {
       // ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.TEST);
        CourseDao cdao = new JdbcCourseDao(cf);

        try {

            System.out.println("\n-------------COURSE BY ID--------------------");
            try {
                System.out.println(cdao.findCourseById(10000));
            } catch (CourseNotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\n-------------COURSE BY SHORT TITLE--------------------");
            try {
                System.out.println(cdao.findCourseByShortTitle("sas1"));
            } catch (CourseNotFoundException e) {
                System.out.println(e.getMessage());
            }

            Course c = new Course(7900,"JDBC","JDBC",1,625.0);

            System.out.println("----------------ADDING NEW COURSE-------------------");
            try {
                cdao.addCourse(c);
            } catch (CourseAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
