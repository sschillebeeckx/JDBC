package be.abis.exercise.test;

import be.abis.exercise.dao.CourseDao;
import be.abis.exercise.dao.JdbcCourseDao;
import be.abis.exercise.exception.CourseNotFoundException;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.factory.ConnectionType;
import be.abis.exercise.model.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseTest {

    public static void main(String[] args) {
        ConnectionFactory cf = ConnectionFactory.createFactory(ConnectionType.TEST);
        CourseDao cdao = new JdbcCourseDao(cf);

        try {
            System.out.println("-------------ALL COURSES--------------------");
            List<Course> allCourses = cdao.findAllCourses();
            System.out.println("nr of courses in table: " + allCourses.size());
            allCourses.forEach(System.out::println);

            System.out.println("\n-------------COURSE BY ID--------------------");
            System.out.println(cdao.findCourseById(7900));

            System.out.println("\n-------------COURSE BY SHORT TITLE--------------------");
            System.out.println(cdao.findCourseByShortTitle("sas1"));


        } catch (SQLException | CourseNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
