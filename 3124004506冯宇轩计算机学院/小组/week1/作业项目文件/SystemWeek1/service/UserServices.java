package SystemWeek1.service;

import SystemWeek1.Object.User;
import SystemWeek1.util.Util;

import java.sql.*;

public class UserServices {
    // 用户注册
    public int register(User user) throws SQLException {
        // 检查用户名是否已存在
        if (isUsernameExists(user.getUsername())) {
            throw new SQLException("用户名已存在，请重新输入");
        }

        // 定义插入用户信息的 SQL 语句
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             // 准备 SQL 语句，并设置返回自动生成的键
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // 设置 SQL 语句的参数
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getRole());
            // 执行插入操作
            pstmt.executeUpdate();

            // 获取自动生成的 user_id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
    // 检查邮箱是否已存在
    public static boolean isEmailExists(String email) {
        // 通过 Util 工具类获取数据库连接
        try (Connection connection = Util.getConnection();
             // 准备一个预编译的 SQL 语句，用于查询 users 表中指定邮箱的记录数量
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email =?")) {
            // 为预编译 SQL 语句的第一个占位符（?）设置具体的邮箱值
            statement.setString(1, email);
            // 执行查询操作，并将查询结果存储在 ResultSet 对象中
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //如果记录数量大于0说明邮箱存在，返回true
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            // 捕获并处理可能出现的异常，打印异常的堆栈信息
            e.printStackTrace();
        }
        //未查询到，则返回false
        return false;
    }

    // 检查用户名是否已存在
    //与检查邮箱同理
    private boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 用户登录
    public User login(String username, String password) throws SQLException {
        // 定义 SQL 查询语句，用于从 users 表中查找匹配指定用户名和密码的记录
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            // 执行查询操作，并将查询结果存储在 ResultSet 对象中
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getInt("role"));
                return user;// 返回包含用户信息的 User 对象
            }
        }
        return null;
    }

    // 检查用户表是否为空
    //同理
    public boolean isUserTableEmpty() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            return rs.getInt(1) == 0;
        }
    }

    // 修改密码
    public boolean changePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            // 执行更新操作，并返回受影响的行数
            int rows = pstmt.executeUpdate();
            // 如果受影响的行数大于 0，说明密码修改成功，返回 true；否则返回 false
            return rows > 0;
        }
    }

    // 查看自己的基本信息
    public User viewUserInfo(int userId) throws SQLException {
        // 定义 SQL 更新语句，用于将 users 表中指定用户 ID 的用户密码更新为新密码
        String sql = "SELECT * FROM users WHERE user_id = ?";
        // 通过 Util 工具类获取数据库连接
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                // 创建一个新的 User 对象，用于存储从数据库中查询到的用户信息
                // 从结果集中获取用户信息并设置到 User 对象中
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getInt("role"));
                // 返回包含用户信息的 User 对象
                return user;
            }
        }
        return null;
    }
}