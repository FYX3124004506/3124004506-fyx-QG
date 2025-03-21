package SystemWeek1.Object;

public class Course {
    private int course_id;//课程id
    private String course_name;//课程名称
    private int credit;//学分
    private boolean is_opened;//是否开课
    private String timeplace;//上课时间和地点
    private int capacity;//上课人数限制

    public Course() {
    }

    public Course(int course_id, String course_name, int credit, boolean is_opened, String timeplace, int capacity) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.credit = credit;
        this.is_opened = is_opened;
        this.timeplace = timeplace;
        this.capacity = capacity;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isIs_opened() {
        return is_opened;
    }

    public void setIs_opened(boolean is_opened) {
        this.is_opened = is_opened;
    }

    public String getTimeplace() {
        return timeplace;
    }

    public void setTimeplace(String timeplace) {
        this.timeplace = timeplace;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}