package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class Entry extends AbstractPersistable<Long> {
    private static int numberCounter;

    private int number;
    private String user;
    private String title;
    private String text;

    private Entry() {
        super();
        this.number = ++numberCounter;
    }

    public Entry(String user, String title, String text) {
        this();
        this.user = user;
        this.title = title;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getNumber() {
        return number;
    }
}
