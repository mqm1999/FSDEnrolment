package entity;

public class Student {
    private String studentId;
    private String studentName;
    private String email;
    private String userId;

    public Student() {
    }

    public Student(String studentId, String studentName, String email, String userId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.userId = userId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
