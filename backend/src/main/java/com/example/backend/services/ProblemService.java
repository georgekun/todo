package com.example.backend.services;


import com.example.backend.entity.Problem;
import com.example.backend.entity.Task;
import com.example.backend.entity.User;
import com.example.backend.exeptions.ErrorAuth;
import com.example.backend.repositories.ProblemRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProblemService {
    public final ProblemRepo ProblemRepo;
    public final AuthService authService;
    public final String refreshTokenSecret;

    public ProblemService(ProblemRepo taskRepo, AuthService authService, @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.ProblemRepo = taskRepo;
        this.authService = authService;
        this.refreshTokenSecret=refreshTokenSecret;
    }


    //запрос перехватывает интерцептор, смотреть есть ли там аттрибут user. Если да получает Id этого авторизованного пользователя
    //в репозитеории таск находит все задачи по этому id
    public List<Task> getAllTaskByUser(HttpServletRequest request) {
        var author = (User) request.getAttribute("user");
        Long id = author.getId();
        //время костыля :)
        //получаю все задачи из репозитория и сравниваю id
        List<Task> new_list = new ArrayList<>();//пустой массив
        for (Problem item : ProblemRepo.findAll()) {
            if (author.getId().equals(item.getAuthor().getId())) {
                Task task = new Task(item.getId(),item.getDescription(),item.getData(),item.getDone());
                new_list.add(task);
            }
        }

        return new_list;
    }

    //сохранение таска
    public void setTask(Problem problem, HttpServletRequest request) throws ErrorAuth{
        var author = (User) request.getAttribute("user");
        problem.setAuthor(author);
        ProblemRepo.save(problem);
    }

    //удаление таска
    public void deleteTask(Long id,HttpServletRequest request){
        ProblemRepo.deleteById(id);
    }

    //обновление таска

    public void updateTask(Long id,String description, HttpServletRequest req){

        Optional<Problem> optional_problem = ProblemRepo.findById(id);
        Problem problem = optional_problem.orElse(null);
        Objects.requireNonNull(problem).setDescription(description);
        ProblemRepo.save(problem);

    }

    //сделать таск выполненным
    public void taskDone(Long id, HttpServletRequest request) {
        Optional<Problem> optional_problem = ProblemRepo.findById(id);
        Problem problem = optional_problem.orElse(null);
        Boolean flag =  Objects.requireNonNull(problem).getDone(); // проверим задача выполнена или нет
        if(flag){
            Objects.requireNonNull(problem).setDone(false);
        }
        else{
        Objects.requireNonNull(problem).setDone(true);
        }

        ProblemRepo.save(problem);
    }
}
