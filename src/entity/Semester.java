package entity;

public class Semester {
    private String id;
    private String semesterName;
    private boolean active;

    public Semester(String id, String semesterName, boolean active) {
        this.id = id;
        this.semesterName = semesterName;
        this.active = active;
    }

    public Semester() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
