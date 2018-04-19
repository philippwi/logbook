package db.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "logbook", catalog = "")
public class UserEntity {
    private String name;
    private String password;
    private byte admin;

    public UserEntity(){}

    public UserEntity(String nm, String pw, byte adm){
        setName(nm);
        setPassword(pw);
        setAdmin(adm);
    }
    @Id
    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Admin", nullable = false)
    public byte getAdmin() {
        return admin;
    }

    public void setAdmin(byte admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return admin == that.admin &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, password, admin);
    }

    @Override
    public String toString(){
        return "(" + name + ", " + password + ", " + admin + ")";
    }
}
