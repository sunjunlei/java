package com.example.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author sjl
 */
@Data
public class LogEntity {
    private String id;
    private Date importDate;
    private String logData;

    public LogEntity(String id, Date importDate, String logData) {
        this.id = id;
        this.importDate = importDate;
        this.logData = logData;
    }

    public LogEntity() {
    }
}
