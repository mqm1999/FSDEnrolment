package entity;

public class SubjectEnrolment {
    private String id;
    private String subjectId;
    private String studentId;
    private String enrollTime;
    private Long subjectMark;
    private String subjectGrade;

    public SubjectEnrolment() {
    }

    public SubjectEnrolment(String id, String subjectId, String studentId, String enrollTime, Long subjectMark, String subjectGrade) {
        this.id = id;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.enrollTime = enrollTime;
        this.subjectMark = subjectMark;
        this.subjectGrade = subjectGrade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
}
