package com.raveleen;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Святослав on 14.12.2016.
 */
@Entity
@Table(name = ("Photos"))
public class Photo {
    @Id
    @GenericGenerator(name = "auto_inc", strategy = "increment")
    @GeneratedValue(generator = "auto_inc")
    private long id;
    @Column(name = ("Name"))
    private String name;
    @Column(name = ("body"), length = 16777215)
    private byte[] body;

    public Photo() {}

    public Photo(String name, byte[] body) {
        this.name = name;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
