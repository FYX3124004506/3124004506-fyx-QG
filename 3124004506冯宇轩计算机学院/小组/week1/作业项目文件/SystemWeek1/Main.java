package SystemWeek1;

import SystemWeek1.Object.Students;
import SystemWeek1.Object.User;
import SystemWeek1.service.AdminService;
import SystemWeek1.service.StudentServices;
import SystemWeek1.service.UserServices;

import java.sql.SQLException;
import java.util.Scanner;

import static SystemWeek1.service.UserServices.isEmailExists;

public class Main {
    // 用于存储当前登录管理员的用户 ID
    private static int currentAdminUserId = -1;
    public static void main(String[] args) {

        //创建实例
        AdminService adminDAO = new AdminService();
        UserServices userDAO = new UserServices();
        StudentServices studentDAO = new StudentServices();
        Scanner input = new Scanner(System.in);
        //选课菜单界面
        while (true) {
            System.out.println("\n===========================");
            System.out.println("🎓 学生选课管理系统");
            System.out.println("===========================");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请选择操作（输入 1 - 3）：");
            // 读取用户输入的操作选项
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    try {
                        //检查用户表是否为空
                        if (userDAO.isUserTableEmpty()) {
                            System.out.println("用户表为空，请先注册。");
                            break;
                        }
                        //用户登录界面
                        System.out.println("===== 用户登录 =====");
                        System.out.print("请输入用户名：");
                        String username = input.nextLine();
                        System.out.print("请输入密码：");
                        String password = input.nextLine();
                        // 进行用户登录
                        User user = userDAO.login(username, password);

                        if (user != null) {

                            if (user.getRole() == 1) {
                                //学生身份
                                Students student = new Students();
                                student.setUser_id(user.getUser_id());
                                student.setStudent_name(user.getUsername());
                                System.out.println("登录成功！你的角色是：学生");
                                //进入学生操作界面
                                studentMenu(student, studentDAO, input);
                            } else if (user.getRole() == 2) {
                                //管理员身份
                                System.out.println("登录成功！你的角色是：管理员");
                                //进入管理员界面
                                adminMenu(adminDAO, input);
                            }
                        } else {
                            System.out.println("登录失败，用户名或密码错误。");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        registerUser(userDAO, studentDAO, input);
                    } catch (SQLException e) {
                        System.out.println("注册失败：" + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("感谢使用，再见！");
                    input.close();
                    return;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    private static void studentMenu(Students student, StudentServices studentDAO, Scanner input) throws SQLException {
        while (true) {
            //学生菜单界面
            System.out.println("\n===== 学生菜单 =====");
            System.out.println("1. 查看可选课程");
            System.out.println("2. 选择课程");
            System.out.println("3. 退选课程");
            System.out.println("4. 查看已选课程");
            System.out.println("5. 修改手机号");
            System.out.println("6. 修改密码");
            System.out.println("7. 查看自己的基本信息");
            System.out.println("8. 退出");
            System.out.print("请选择操作（输入 1 - 8）：");
            //获取用户输入
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    //查看可选课程
                    studentDAO.listAvailableCourses();
                    break;
                case "2":
                    //选课
                    System.out.print("请输入要选择的课程ID：");
                    try {
                        //通过选课id找到课程
                        int courseId = Integer.parseInt(input.nextLine());
                        studentDAO.selectCourse(student.getStudent_id(), courseId);
                        System.out.println("选课成功！");
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的课程ID。");
                    } catch (SQLException e) {
                        System.out.println("选课失败：" + e.getMessage());
                    }
                    break;
                case "3":
                    //退选课程
                    System.out.print("请输入要退选的课程ID：");
                    try {
                        //与选课同理
                        int courseId = Integer.parseInt(input.nextLine());
                        studentDAO.dropCourse(student.getStudent_id(), courseId);
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的课程ID。");
                    } catch (SQLException e) {
                        System.out.println("退选失败：" + e.getMessage());
                    }
                    break;
                case "4":
                    //查看已选课程
                    studentDAO.listSelectedCourses(student.getStudent_id());
                    break;
                case "5":
                    //修改手机号
                    System.out.print("请输入新的手机号：");
                    //输入修改后的手机号
                    String newPhone = input.nextLine();
                    try {
                        //调用修改手机号的方法
                        if (studentDAO.updatePhone(student.getStudent_id(), newPhone)) {
                            System.out.println("手机号修改成功！");
                        } else {
                            System.out.println("手机号修改失败。");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("手机号格式无效：" + e.getMessage());
                    }
                    break;
                case "6":
                    //修改密码
                    System.out.print("请输入新密码：");
                    //输入修改后的密码
                    String newPassword = input.nextLine();
                    System.out.print("请确认新密码：");
                    String okPassword = input.nextLine();
                    if (newPassword.equals(okPassword)) {
                        try {
                            UserServices userServices = new UserServices();
                            //调用修改密码方法
                            if (userServices.changePassword(student.getUser_id(), newPassword)) {
                                System.out.println("密码修改成功！");
                            } else {
                                System.out.println("密码修改失败。");
                            }
                        } catch (SQLException e) {
                            System.out.println("密码修改失败：" + e.getMessage());
                        }
                    } else {
                        System.out.println("两次输入的密码不一致，请重新操作。");
                    }
                    break;
                case "7":
                    //查看自己的基本信息
                    //创建一个Students对象，将学生信息存入其中
                    Students studentInfo = studentDAO.viewStudentInfo(student.getStudent_id());
                    if (studentInfo != null) {
                        System.out.println("学生ID：" + studentInfo.getStudent_id());
                        System.out.println("姓名：" + studentInfo.getStudent_name());
                        System.out.println("班级：" + studentInfo.getClass_info());
                        System.out.println("手机号：" + studentInfo.getPhone());
                    } else {
                        System.out.println("未找到学生信息。");
                    }
                    break;
                case "8":
                    //退出
                    return;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    private static void adminMenu(AdminService adminDAO, Scanner input) throws SQLException {
        while (true) {
            //管理员菜单
            System.out.println("\n===== 管理员菜单 =====");
            System.out.println("1. 查询所有学生");
            System.out.println("2. 修改学生手机号");
            System.out.println("3. 查询所有课程");
            System.out.println("4. 添加课程");
            System.out.println("5. 删除课程");
            System.out.println("6. 查询某课程的学生名单");
            System.out.println("7. 查询某个学生的选课信息");
            System.out.println("8. 修改密码");
            System.out.println("9. 查看自己的基本信息");
            System.out.println("10. 退出");
            System.out.print("请选择操作（输入 1 - 10）：");
            //获取管理员操作
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    //查询所有学生
                    adminDAO.listAllStudents();
                    break;
                case "2":
                    //修改学生手机号
                    System.out.print("请输入学生ID：");
                    try {
                        int studentId = Integer.parseInt(input.nextLine());
                        System.out.print("请输入新的手机号：");
                        String newPhone = input.nextLine();
                        if (adminDAO.updateStudentPhone(studentId, newPhone)) {
                            System.out.println("手机号修改成功！");
                        } else {
                            System.out.println("手机号修改失败。");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的学生ID。");
                    }
                    break;
                case "3":
                    //查询所有课程
                    adminDAO.listAllCourses();
                    break;
                case "4":
                    //添加课程
                    System.out.print("请输入课程名：");
                    String courseName = input.nextLine();
                    System.out.print("请输入学分：");
                    int credit = Integer.parseInt(input.nextLine());
                    System.out.print("是否开课（true/false）：");
                    boolean isOpened = Boolean.parseBoolean(input.nextLine());
                    System.out.print("请输入上课时间地点：");
                    String timePlace = input.nextLine();
                    System.out.print("请输入课程容量：");
                    int capacity = Integer.parseInt(input.nextLine());
                    SystemWeek1.Object.Course course = new SystemWeek1.Object.Course();
                    course.setCourse_name(courseName);
                    course.setCredit(credit);
                    course.setIs_opened(isOpened);
                    course.setTimeplace(timePlace);
                    course.setCapacity(capacity);
                    if (adminDAO.addCourse(course)) {
                        System.out.println("课程添加成功！");
                    } else {
                        System.out.println("课程添加失败。");
                    }
                    break;
                case "5":
                    //删除课程
                    System.out.print("请输入要删除的课程ID：");
                    try {
                        int courseId = Integer.parseInt(input.nextLine());
                        if (adminDAO.deleteCourse(courseId)) {
                            System.out.println("课程删除成功！");
                        } else {
                            System.out.println("课程删除失败。");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的课程ID。");
                    }
                    break;
                case "6":
                    //查询某课程的学生名单
                    System.out.print("请输入课程ID：");
                    try {
                        int courseId = Integer.parseInt(input.nextLine());
                        adminDAO.getCourseStudents(courseId);
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的课程ID。");
                    }
                    break;
                case "7":
                    //查询某个学生的选课信息
                    System.out.print("请输入学生ID：");
                    try {
                        int studentId = Integer.parseInt(input.nextLine());
                        adminDAO.viewStudentEnrollment(studentId);
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入有效的学生ID。");
                    }
                    break;
                case "8":
                    //修改密码
                    System.out.print("请输入新密码：");
                    String newPassword = input.nextLine();
                    System.out.print("请确认新密码：");
                    String confirmPassword = input.nextLine();
                    if (newPassword.equals(confirmPassword)) {
                        try {
                            UserServices userServices = new UserServices();
                            // 这里假设管理员的用户ID可以从某个地方获取，暂时用一个示例值
                            int adminUserId = 1;
                            if (userServices.changePassword(adminUserId, newPassword)) {
                                System.out.println("密码修改成功！");
                            } else {
                                System.out.println("密码修改失败。");
                            }
                        } catch (SQLException e) {
                            System.out.println("密码修改失败：" + e.getMessage());
                        }
                    } else {
                        System.out.println("两次输入的密码不一致，请重新操作。");
                    }
                    break;
                case "9":
                    //查看自己的基本信息
                    UserServices userServices = new UserServices();
                    // 使用记录的 当前登录管理员的用户 ID
                    User adminInfo = userServices.viewUserInfo(currentAdminUserId);
                    if (adminInfo != null) {
                        System.out.println("用户ID：" + adminInfo.getUser_id());
                        System.out.println("用户名：" + adminInfo.getUsername());
                        System.out.println("邮箱：" + adminInfo.getEmail());
                        System.out.println("角色：" + (adminInfo.getRole() == 1 ? "学生" : "管理员"));
                    } else {
                        System.out.println("未找到管理员信息");
                    }
                    break;
                case "10":
                    return;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
    }

    private static void registerUser(UserServices userDAO, StudentServices studentDAO, Scanner input) throws SQLException {
        //用户注册界面
        System.out.println("===== 用户注册 =====");
        //用户名
        System.out.print("请输入用户名：");
        String username = input.nextLine();
        //密码
        System.out.print("请输入密码：");
        String password = input.nextLine();
        //确认密码
        System.out.print("请确认密码：");
        String confirmPassword = input.nextLine();
        if (!password.equals(confirmPassword)) {//密码一致
            System.out.println("两次输入的密码不一致，请重新操作。");
            return;
        }
        //电子邮件
        System.out.print("请输入电子邮件：");
        String email = input.nextLine();
        //检查邮箱是否重复
        if (isEmailExists(email)) {
            System.out.println("该邮箱已被注册，请使用其他邮箱。");
            return;
        }
        //角色标识
        int role = 0;
        while (true) {
            System.out.print("请选择角色（输入 1 代表学生，2 代表管理员）：");
            try {
                role = Integer.parseInt(input.nextLine());
                if (role == 1 || role == 2) {
                    break;
                } else {
                    System.out.println("输入无效，请输入 1 或 2。");
                }
            } catch (NumberFormatException e) {
                System.out.println("输入无效，请输入有效的数字。");
            }
        }
        //创建User对象，并载入注册信息
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        int userId = userDAO.register(user);
        if (userId > 0) {
            System.out.println("注册成功！请返回主界面登录(如为学生请继续补充您的相关信息)");
            if (role == 1) {
                //补充学生信息
                //姓名
                System.out.print("请输入学生姓名：");
                String studentName = input.nextLine();
                //班级
                System.out.print("请输入学生班级：");
                String classInfo = input.nextLine();
                //手机号
                System.out.print("请输入学生手机号：");
                String phone = input.nextLine();
                //创建user对象并载入相关信息
                Students student = new Students();
                student.setUser_id(userId);
                student.setStudent_name(studentName);
                student.setClass_info(classInfo);
                student.setPhone(phone);
                studentDAO.addStudentInfo(student);
            }
        } else {
            System.out.println("注册失败，请稍后重试。");
        }
    }
}