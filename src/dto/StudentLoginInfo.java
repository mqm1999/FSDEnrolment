package dto;

public class StudentLoginInfo {
    private String email;
    private String studentId;

    public StudentLoginInfo() {
    }

    public StudentLoginInfo(String email, String studentId) {
        this.email = email;
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
