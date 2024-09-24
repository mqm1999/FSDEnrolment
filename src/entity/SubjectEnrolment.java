package entity;

import java.math.BigDecimal;
import java.util.Date;

public class SubjectEnrolment {
    private String id;
    private String subjectId;
    private String studentId;
    private Date enrollTime;
    private BigDecimal subjectMark;

    public SubjectEnrolment() {
    }

    public SubjectEnrolment(String id, String subjectId, String studentId, Date enrollTime, BigDecimal subjectMark) {
        this.id = id;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.enrollTime = enrollTime;
        this.subjectMark = subjectMark;
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

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public BigDecimal getSubjectMark() {
        return subjectMark;
    }

    public void setSubjectMark(BigDecimal subjectMark) {
        this.subjectMark = subjectMark;
    }
}
