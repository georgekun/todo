package com.example.backend.controllers;

import com.example.backend.entity.Problem;
import com.example.backend.entity.Task;
import com.example.backend.exeptions.ErrorAuth;
import com.example.backend.services.AuthService;
import com.example.backend.services.ProblemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class ProblemController {
    //инициализация сервиса
    public final ProblemService service;

    public ProblemController(ProblemService service, AuthService authService) {
        this.service = service;
    }


    record responseProblem() {
    }

    //по этой ссылке, отправляется запрос
    @GetMapping("get")
    public List<Task> getTask(HttpServletRequest request) {
        return service.getAllTaskByUser(request);
    }

    @PostMapping("post")
    public void setTask(@RequestBody Problem problem, HttpServletRequest request) throws ErrorAuth {
        service.setTask(problem, request);
    }

    @PostMapping("delete")
    public void deleteTask(@RequestBody Task task, HttpServletRequest request){
        service.deleteTask(task.getId(), request);
    }

    @PostMapping("done")
    public void taskIsDone(@RequestBody Task task, HttpServletRequest request){
        service.taskDone(task.getId(), request);
    }
    @PostMapping("update")
    public void updateTask(@RequestBody Task task, HttpServletRequest req){
        service.updateTask(task.getId(),task.getDescription(),req);
    }
}
