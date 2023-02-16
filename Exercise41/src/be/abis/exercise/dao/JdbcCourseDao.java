package be.abis.exercise.dao;

import be.abis.exercise.exception.CourseAlreadyExistsException;
import be.abis.exercise.exception.CourseNotFoundException;
import be.abis.exercise.factory.ConnectionFactory;
import be.abis.exercise.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCourseDao implements CourseDao {

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
    public Course findCourseById(int id) throws SQLException, CourseNotFoundException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abiscourses where cid=?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        Course foundCourse = this.mapOneCourse(rs);
        conn.close();
        if(foundCourse==null){
            throw new CourseNotFoundException("Course with id " +id + " not found.");
        }
        return foundCourse;
    }

    @Override
    public Course findCourseByShortTitle(String shortTitle) throws SQLException, CourseNotFoundException {
        Connection conn = connectionFactory.createConnection();
        PreparedStatement ps = conn.prepareStatement("select * from abiscourses where cstitle=?");
        ps.setString(1,shortTitle.toUpperCase());
        ResultSet rs = ps.executeQuery();
        Course foundCourse = this.mapOneCourse(rs);
        connectionFactory.closeConnection(conn);
        if(foundCourse==null){
            throw new CourseNotFoundException("Course with title " +shortTitle + " not found.");
        }
        return foundCourse;
    }

    @Override
    public void addCourse(Course c) throws SQLException, CourseAlreadyExistsException {
        Connection conn = connectionFactory.createConnection();
        String sql = "insert into abiscourses(cid,cstitle,cltitle,cdur,caprice) values(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,c.getCourseId());
        ps.setString(2,c.getShortTitle());
        ps.setString(3,c.getLongTitle());
        ps.setInt(4,c.getNumberOfDays());
        ps.setDouble(5,c.getPricePerDay());
        try {
            int i = ps.executeUpdate();
            System.out.println(i + " course was added.");
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            throw new CourseAlreadyExistsException("Course with id "+ c.getCourseId() + " already exists.");
        } finally {
            conn.close();
        }

    }

    @Override
    public void updateCourse(Course c) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String sql = "update abiscourses set cstitle=? , cltitle=?, cdur=?, caprice=? where cid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,c.getShortTitle());
        ps.setString(2,c.getLongTitle());
        ps.setInt(3,c.getNumberOfDays());
        ps.setDouble(4,c.getPricePerDay());
        ps.setInt(5,c.getCourseId());
        int i = ps.executeUpdate();
        System.out.println(i + " course was updated.");
        conn.close();
    }

    @Override
    public void updateCourse(int courseId,int newDuration) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String sql = "{call update_course_duration(?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1,courseId);
        cs.setInt(2,newDuration);
        int i = cs.executeUpdate();
        System.out.println("there was "+ i + " row updated");
        conn.close();

    }

    @Override
    public void deleteCourse(int id) throws SQLException {
        Connection conn = connectionFactory.createConnection();
        String sql = "delete from abiscourses where cid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        int i = ps.executeUpdate();
        System.out.println(i + " course was deleted.");
        conn.close();
    }

    private Course mapOneCourse(ResultSet rs) throws SQLException {
        Course course = null;
        if (rs.next()){
            course = new Course();
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
