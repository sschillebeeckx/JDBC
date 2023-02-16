package be.abis.exercise.dao;

import be.abis.exercise.exception.CourseAlreadyExistsException;
import be.abis.exercise.exception.CourseNotFoundException;
import be.abis.exercise.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {

    public List<Course> findAllCourses() throws SQLException;
    public Course findCourseById(int id) throws SQLException, CourseNotFoundException;
    public Course findCourseByShortTitle(String shortTitle) throws SQLException, CourseNotFoundException;
    public void addCourse(Course c) throws SQLException, CourseAlreadyExistsException;
    public void updateCourse(Course c) throws SQLException;
    public void updateCourse(int courseId,int newDuration) throws SQLException;
    public void deleteCourse(int id) throws SQLException;

}
