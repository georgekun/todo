package com.example.backend.repositories;

import com.example.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    /*под капотом спринг сделает запрос в базу данных в таком виде*/
    //select *  from user u where u.email = : email
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); //ахуенно, я просто это написал, а метод спринг уже реализовал, умный сука
}
