package entity;

public class Student {
    private String studentId;
    private String email;
    private String userId;

    public Student() {
    }

    public Student(String studentId, String email, String userId) {
        this.studentId = studentId;
        this.email = email;
        this.userId = userId;
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
