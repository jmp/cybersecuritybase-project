package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class User extends AbstractPersistable<Long> {
    private String name;
    private String password;

    private User() {
        super();
    }

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
