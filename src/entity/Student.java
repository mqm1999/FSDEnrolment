package entity;

public class Student {
    private String studentId;
    private String email;
    private String phone;
    private Long userId;

    public Student() {
    }

    public Student(String studentId, String email, String phone, Long userId) {
        this.studentId = studentId;
        this.email = email;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
