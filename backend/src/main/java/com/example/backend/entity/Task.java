package com.example.backend.entity;

import java.time.LocalDate;

public class Task {
    //этот класс сделан мною, чтоб удобным способом отправлять данные из сервера
    public Long id;



    public String description;
    public LocalDate date; //надо поменять на дату
    public Boolean done;
    public Task() {
    }

    public Task(Long id, String description, LocalDate date,Boolean done) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

}
