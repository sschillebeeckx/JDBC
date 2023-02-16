package be.abis.exercise.test;

import be.abis.exercise.dao.CourseDao;
import be.abis.exercise.dao.JdbcCourseDao;
import be.abis.exercise.exception.CourseAlreadyExistsException;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Course;

import java.sql.SQLException;

public class CourseSPTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.PRODUCTION);
        CourseDao cdao = new JdbcCourseDao(cf);
        Course c = new Course(9000,"JDBC","JDBC",1,625.0);

        try {

            System.out.println("----------------ADDING NEW COURSE-------------------");
            cdao.addCourse(c);

            System.out.println("----------------UPDATING COURSE-------------------");
            cdao.updateCourse(c.getCourseId(),2);

            System.out.println("----------------DELETING COURSE-------------------");
            cdao.deleteCourse(9000);


        } catch (SQLException | CourseAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

    }
}
