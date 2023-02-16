package be.abis.exercise.dao;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Course;

import java.util.List;

public class JdbcCourseDao implements CourseDao{

    private ConnectionFactory connectionFactory;

    public JdbcCourseDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Course> findAllCourses() {
        return null;
    }

    @Override
    public Course findCourseById(int id) {
        return null;
    }

    @Override
    public Course findCourseByShortTitle(String shortTitle) {
        return null;
    }

    @Override
    public void addCourse(Course c) {

    }

    @Override
    public void updateCourse(Course c) {

    }

    @Override
    public void deleteCourse(int id) {

    }
}
