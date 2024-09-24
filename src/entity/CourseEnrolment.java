package entity;

import java.util.Date;

public class CourseEnrolment {
    private String id;
    private Long courseId;
    private String studentId;
    private Date enrollTime;

    public CourseEnrolment() {
    }

    public CourseEnrolment(String id, Long courseId, String studentId, Date enrollTime) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrollTime = enrollTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
}
