package entity;

public class Semester {
    private String id;
    private String semesterName;

    public Semester() {
    }

    public Semester(String id, String semesterName) {
        this.id = id;
        this.semesterName = semesterName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}
