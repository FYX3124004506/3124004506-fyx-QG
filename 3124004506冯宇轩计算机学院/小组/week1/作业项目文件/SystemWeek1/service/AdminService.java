package SystemWeek1.service;

import SystemWeek1.Object.Course;
import SystemWeek1.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    //管理员的功能实现
    // 1. 查询所有学生
    public void listAllStudents() {
        //执行的sql语句，查询表中的所有学生信息
        String sql = "SELECT s.student_id, u.username, s.student_name, s.class_info, s.phone " +
                "FROM students s " +
                "JOIN users u ON s.user_id = u.user_id";
        //从 students 表（使用别名 s）和 users 表（使用别名 u）中查询数据，
        // 并且通过 user_id 字段将这两个表连接起来。
        // 实现获取学生对应的用户名信息。
        try (Connection conn = Util.getConnection();//执行查询并获取结果集

             PreparedStatement pstmt = conn.prepareStatement(sql);//预编译防止sql注入
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("学生ID：" + rs.getInt("student_id") +
                        "，姓名：" + rs.getString("student_name") +
                        "，班级：" + rs.getString("class_info") +
                        "，手机号：" + rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. 修改学生手机号
    public boolean updateStudentPhone(int studentId, String newPhone) {
        //students 表中指定 student_id 的学生的 phone 字段更新为新的手机号码
        String sql = "UPDATE students SET phone = ? WHERE student_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPhone);
            pstmt.setInt(2, studentId);
            // 执行更新操作，并返回受影响的行数
            int rows = pstmt.executeUpdate();
            // 如果受影响的行数大于 0，说明成功修改了学生的手机号码，返回 true；否则返回 false
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. 查询所有课程
    public void listAllCourses() {
        String sql = "SELECT * FROM courses";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             // 执行查询操作，并将查询结果存储在 ResultSet 对象中
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("课程ID：" + rs.getInt("course_id") +
                        "，课程名：" + rs.getString("course_name") +
                        "，学分：" + rs.getInt("credit") +
                        "，是否开课：" + (rs.getBoolean("is_opened") ? "是" : "否") +
                        "，上课时间地点：" + rs.getString("timeplace") +
                        "，容量：" + rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. 添加课程
    public boolean addCourse(Course course) {
        // 插入的字段包括课程名称、学分、是否开课、上课时间地点和课程容量
        String sql = "INSERT INTO courses (course_name, credit, is_opened, timeplace, capacity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourse_name());
            pstmt.setInt(2, course.getCredit());
            pstmt.setBoolean(3, course.isIs_opened());
            pstmt.setString(4, course.getTimeplace());
            pstmt.setInt(5, course.getCapacity());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. 删除课程
    public boolean deleteCourse(int courseId) {
        //从 courses 表中删除指定课程 ID 的课程记录
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            //返回被影响的行数
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. 查询某课程的学生名单
    public void getCourseStudents(int courseId) {
        //多表连接查询选修指定课程的学生信息
        String sql = "SELECT s.student_id, s.student_name " +
                "FROM students s " +
                "JOIN student_courses sc ON s.student_id = sc.student_id " +
                "JOIN courses c ON sc.course_id = c.course_id " +
                "WHERE c.course_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("学生ID：" + rs.getInt("student_id") +
                        "，姓名：" + rs.getString("student_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 7. 查询某个学生的选课信息
    public void viewStudentEnrollment(int studentId) {
        //多表连接查询指定学生的选课信息
        String sql = "SELECT c.course_id, c.course_name " +
                "FROM courses c " +
                "JOIN student_courses sc ON c.course_id = sc.course_id " +
                "JOIN students s ON sc.student_id = s.student_id " +
                "WHERE s.student_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //将课程的 ID 和名称输出
                System.out.println("课程ID：" + rs.getInt("course_id") +
                        "，课程名：" + rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}