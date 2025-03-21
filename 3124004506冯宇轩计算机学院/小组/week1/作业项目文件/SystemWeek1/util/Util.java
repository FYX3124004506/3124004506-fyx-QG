package SystemWeek1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // 数据库连接配置信息
    private static final String URL = "jdbc:mysql://localhost:3306/qgweek1555";
    private static final String USER = "root";
    private static final String PASSWORD = "4936Z5963Km9!B";
    /**
     * 获取数据库连接对象
     * @return java.sql.Connection 数据库连接对象
     * @throws SQLException 如果连接失败（如：地址错误、权限不足、服务未启动等）
     * 使用示例：
     * try (Connection conn = Util.getConnection()) {
     *     // 执行数据库操作...
     * } catch (SQLException e) {
     *     e.printStackTrace();
     * }
     * 注意：调用者必须在使用后关闭连接（推荐使用 try-with-resources）
     */
    public static Connection getConnection() throws SQLException {
        // 使用 DriverManager 创建连接
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
