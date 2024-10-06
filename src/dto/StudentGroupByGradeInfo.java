package dto;

public class StudentGroupByGradeInfo {
    private String studentId;
    private String studentName;
    private String subjectId;
    private String subjectName;
    private Long subjectMark;
    private String subjectGrade;

    public StudentGroupByGradeInfo() {
    }

    public StudentGroupByGradeInfo(String studentId, String studentName, String subjectId, String subjectName, Long subjectMark, String subjectGrade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectMark = subjectMark;
        this.subjectGrade = subjectGrade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
        return "{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", subjectMark=" + subjectMark +
                ", subjectGrade='" + subjectGrade + '\'' +
                '}';
    }
}
