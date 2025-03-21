package SystemWeek1.service;

import SystemWeek1.Object.Students;
import SystemWeek1.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentServices {
    // 添加学生信息
    public void addStudentInfo(Students student) throws SQLException {
        String sql = "INSERT INTO students (user_id, student_name, gender, class_info, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //设置学生信息
            pstmt.setInt(1, student.getUser_id());
            pstmt.setString(2, student.getStudent_name());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getClass_info());
            pstmt.setString(5, student.getPhone());
            //执行SQL 语句
            pstmt.executeUpdate();
        }
    }

    // 学生选课
    public void selectCourse(int studentId, int courseId) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM student_courses WHERE student_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, studentId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            // 从结果集中获取第一列（即 COUNT(*) 的结果）的值，该值表示学生已选课程的数量
            if (rs.getInt(1) >= 5) {
                throw new SQLException("最多选五门课");
            }

            //用于将学生选择的课程信息插入到 student_courses 表中
            String insertSql = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, courseId);
                insertStmt.executeUpdate();
            }
        }
    }

    // 查询学生已选课程
    public void listSelectedCourses(int studentId) throws SQLException {
        // 用于从 student_courses 表和 courses 表中联合查询指定学生已选的课程信息
        // 通过 JOIN 操作将 student_courses 表和 courses 表连接起来，连接条件是课程 ID 相等
        // 筛选条件为学生 ID 等于传入的 studentId
        String sql = "SELECT c.course_id, c.course_name " +
                "FROM student_courses sc " +
                "JOIN courses c ON sc.course_id = c.course_id " +
                "WHERE sc.student_id = ?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("课程ID: " + rs.getInt("course_id") + "，课程名: " + rs.getString("course_name"));
            }
        }
    }

    // 学生退选课程
    public void dropCourse(int studentId, int courseId) throws SQLException {
        //用于检查指定课程的开课状态
        String checkSql = "SELECT is_opened FROM courses WHERE course_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, courseId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getBoolean("is_opened")) {
                throw new SQLException("退选失败：该课程已开课，不能退选。");
            }
        }

        String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            // 执行删除操作，并返回受影响的行数
            int rowsAffected = pstmt.executeUpdate();
            // 如果受影响的行数为 0，说明未找到该学生的选课记录或课程 ID 无效
            if (rowsAffected == 0) {
                throw new SQLException("退选失败：未找到该学生的选课记录或课程ID无效");
            }
            // 若受影响的行数不为 0，说明退选操作成功，打印提示信息
            System.out.println("退选成功");
        }
    }

    // 修改学生手机号（带输入校验）
    public boolean updatePhone(int studentId, String newPhone) throws SQLException {
        // 对输入的手机号进行格式校验，确保手机号不为 null 且符合正则表达式规则
        // 校验手机号格式
        // 表达式 "^1[3-9]\\d{9}$" 表示手机号以 1 开头，第二位是 3 - 9 中的任意数字，后面跟着 9 位数字
        if (newPhone == null || !newPhone.matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式无效");
        }
        //将 students 表中指定学生 ID 的学生的手机号更新为新手机号
        String sql = "UPDATE students SET phone = ? WHERE student_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPhone);
            pstmt.setInt(2, studentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // 返回是否成功修改
        }
    }

    // 查看可选课程
    public void listAvailableCourses() throws SQLException {
        // 从 `courses` 表中筛选出 `is_opened` 字段值为 0 的课程记录
        String sql = "SELECT * FROM courses WHERE is_opened = 0";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // 将课程的 ID 和名称输出方便用户查看可选课程信息
                System.out.println("课程ID: " + rs.getInt("course_id") + "，课程名: " + rs.getString("course_name"));
            }
        }
    }

    // 查看自己的基本信息
    public Students viewStudentInfo(int studentId) throws SQLException {
        //从 students 表中查找指定学生 ID 的学生信息
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 创建一个新的 Students 对象，用于存储从数据库中查询到的学生信息
                Students student = new Students();
                //获取学生各项的值，并设置到 Students 对象中
                student.setStudent_id(rs.getInt("student_id"));
                student.setUser_id(rs.getInt("user_id"));
                student.setStudent_name(rs.getString("student_name"));
                student.setGender(rs.getString("gender"));
                student.setClass_info(rs.getString("class_info"));
                student.setPhone(rs.getString("phone"));
                // 返回包含学生信息的 Students 对象
                return student;
            }
        }
        return null;
    }
}