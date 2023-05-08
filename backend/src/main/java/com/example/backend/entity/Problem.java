package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "task_schema")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id",
            foreignKey = @ForeignKey(name = "user_id")
    )
    private User author;

    @Column(name = "task_des")
    private String description;


    @Column(name = "task_data")
    private LocalDate data;



    @Column(name = "done")
    private Boolean done;
    //конструкторы
    public Problem() {
    }


    public Problem(Long id, User user, String description, LocalDate data,Boolean done) {
        this.id = id;
        this.author = user;
        this.description = description;
        this.data = data;
        this.done= done;
    }


    //геттера и сеттеры
    public LocalDate getData() {
        return data;
    }
    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", author=" + author.getId() +
                ", description='" + description + '\'' +
                '}';
    }


}
