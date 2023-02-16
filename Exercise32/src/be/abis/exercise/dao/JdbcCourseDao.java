package be.abis.exercise.dao;

import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCourseDao implements CourseDao{

    private ConnectionFactory connectionFactory;

    public JdbcCourseDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Course> findAllCourses() throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abiscourses");
        ResultSet rs = ps.executeQuery();
        List<Course> foundCourses =  this.mapCourses(rs);
        conn.close();
        return foundCourses;
    }

    @Override
    public Course findCourseById(int id) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abiscourses where cid=?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        Course foundCourse = this.mapOneCourse(rs);
        conn.close();
        return foundCourse;
    }

    @Override
    public Course findCourseByShortTitle(String shortTitle) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abiscourses where cstitle=?");
        ps.setString(1,shortTitle.toUpperCase());
        ResultSet rs = ps.executeQuery();
        Course foundCourse = this.mapOneCourse(rs);
        connectionFactory.closeConnection(conn);
        return foundCourse;
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

    private Course mapOneCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        if (rs.next()){
            course.setCourseId(rs.getInt("cid"));
            course.setShortTitle(rs.getString(2).trim());
            course.setLongTitle(rs.getString("cltitle"));
            course.setNumberOfDays(rs.getInt(4));
            course.setPricePerDay(rs.getDouble("caprice"));
        }
        return course;
    }

    private List<Course> mapCourses(ResultSet rs) throws SQLException {
        List<Course> courses = new ArrayList<Course>();
        while (rs.next()) {
            Course course = new Course();
            course.setCourseId(rs.getInt("cid"));
            course.setShortTitle(rs.getString(2).trim());
            course.setLongTitle(rs.getString("cltitle"));
            course.setNumberOfDays(rs.getInt(4));
            course.setPricePerDay(rs.getDouble("caprice"));
            courses.add(course);
        }
        return courses;
    }
}
