package SystemWeek1.Object;

public class Students {
    private int student_id;//students身份id
    private int user_id;//关联users的id
    private String student_name;//学生姓名
    private String gender;//性别
    private String class_info;//班级信息
    private String phone;//手机号

    public Students(int student_id, int user_id, String student_name, String gender, String class_info, String phone) {
        this.student_id = student_id;
        this.user_id = user_id;
        this.student_name = student_name;
        this.gender = gender;
        this.class_info = class_info;
        this.phone = phone;
    }

    public Students() {
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClass_info() {
        return class_info;
    }

    public void setClass_info(String class_info) {
        this.class_info = class_info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}