package dto;

public class EnrolmentInfoForView {
    private String subjectName;
    private String enrollTime;
    private Long subjectMark;
    private String subjectGrade;

    public EnrolmentInfoForView() {
    }

    public EnrolmentInfoForView(String subjectName, String enrollTime, Long subjectMark, String subjectGrade) {
        this.subjectName = subjectName;
        this.enrollTime = enrollTime;
        this.subjectMark = subjectMark;
        this.subjectGrade = subjectGrade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
        return "{" +
                "subjectName='" + subjectName + '\'' +
                ", enrollTime='" + enrollTime + '\'' +
                ", subjectMark=" + subjectMark +
                ", subjectGrade='" + subjectGrade + '\'' +
                '}';
    }
}
