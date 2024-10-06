package dto;

public class EnrolmentInfoForAdminView {
    private String studentName;
    private String subjectName;
    private String enrollTime;
    private Long subjectMark;
    private String subjectGrade;

    public EnrolmentInfoForAdminView() {
    }

    public EnrolmentInfoForAdminView(String studentName, String subjectName, String enrollTime, Long subjectMark, String subjectGrade) {
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.enrollTime = enrollTime;
        this.subjectMark = subjectMark;
        this.subjectGrade = subjectGrade;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Long getSubjectMark() {
        return subjectMark;
    }

    public void setSubjectMark(Long subjectMark) {
        this.subjectMark = subjectMark;
    }

    public String getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(String subjectGrade) {
        this.subjectGrade = subjectGrade;
    }

    @Override
    public String toString() {
        return "{subjectName='" + subjectName + '\'' +
                ", enrollTime='" + enrollTime + '\'' +
                ", subjectMark=" + subjectMark +
                ", subjectGrade='" + subjectGrade + '\'' +
                '}';
    }
}
