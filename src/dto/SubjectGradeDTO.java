package dto;

public class SubjectGradeDTO {
    private String subjectName;
    private String subjectGrade;

    public SubjectGradeDTO() {
    }

    public SubjectGradeDTO(String subjectName, String subjectGrade) {
        this.subjectName = subjectName;
        this.subjectGrade = subjectGrade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(String subjectGrade) {
        this.subjectGrade = subjectGrade;
    }
}
