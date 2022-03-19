package com.example.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author sjl
 */
@Data
public class StudentEntity {
    private String id;
    private String name;
    private Integer age;
    private String clazz;
    private Date date;

    public StudentEntity() {
    }

    public StudentEntity(String id, String name, Integer age, String clazz, Date date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.clazz = clazz;
        this.date = date;
    }
}
