package be.abis.exercise.dao;

import be.abis.exercise.model.Course;

import java.util.List;

public interface CourseDao {

    public List<Course> findAllCourses();
    public Course findCourseById(int id);
    public Course findCourseByShortTitle(String shortTitle);
    public void addCourse(Course c);
    public void updateCourse(Course c);
    public void deleteCourse(int id);

}
