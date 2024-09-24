package entity;

public class Admin {
    private String id;
    private String email;
    private String phone;
    private Long userId;

    public Admin() {
    }

    public Admin(String id, String email, String phone, Long userId) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
