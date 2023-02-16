package be.abis.exercise.dao;

import be.abis.exercise.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {

    public List<Course> findAllCourses() throws SQLException;
    public Course findCourseById(int id) throws SQLException;
    public Course findCourseByShortTitle(String shortTitle) throws SQLException;
    public void addCourse(Course c);
    public void updateCourse(Course c);
    public void deleteCourse(int id);

}
