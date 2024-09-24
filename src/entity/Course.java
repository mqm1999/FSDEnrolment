package entity;

import java.util.Date;

public class Course {
    private String id;
    private String subjectId;
    private Date startTime;
    private Date endTime;
    private String day;
    private String room;
    private String classType;
    private String semesterId;

    public Course() {
    }

    public Course(String id, String subjectId, Date startTime, Date endTime, String day, String room, String classType, String semesterId) {
        this.id = id;
        this.subjectId = subjectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.room = room;
        this.classType = classType;
        this.semesterId = semesterId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }
}
