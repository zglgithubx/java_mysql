package model.domain;
import java.sql.Timestamp;
public class Students {
    private long sid;
    private long student_id;
    private String name;
    private Boolean sex;
    private String academy;
    private String specialty;
    private String clss;
    private String phone_number;
    private String dorm_id;
    private String password;
    private Timestamp logindate;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getClss() {
        return clss;
    }

    public void setClss(String clss) {
        this.clss = clss;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDorm_id() {
        return dorm_id;
    }

    public void setDorm_id(String dorm_id) {
        this.dorm_id = dorm_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLogindate() {
        return logindate;
    }

    public void setLogindate(Timestamp logindate) {
        this.logindate = logindate;
    }

    @Override
    public String toString() {
        return "Students{" +
                "sid=" + sid +
                ", student_id=" + student_id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", academy='" + academy + '\'' +
                ", specialty='" + specialty + '\'' +
                ", clss='" + clss + '\'' +
                ", phone_number=" + phone_number +
                ", dorm_id='" + dorm_id + '\'' +
                ", password='" + password + '\'' +
                ", logindate=" + logindate +
                '}';
    }
}
