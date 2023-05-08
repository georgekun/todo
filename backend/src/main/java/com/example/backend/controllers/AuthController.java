package com.example.backend.controllers;


import com.example.backend.entity.User;
import com.example.backend.services.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    /*тупой контроллер*/
    @GetMapping("")
    public String getUsers() {
        return "Hello, I am main page!";
    }

    @GetMapping("/all")
    public Iterable<User> getAllUser() {
        return service.allUsers();
    }

    /*в пост запрос в теле запроса отправляем объект user*/
    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        return service.saveUser(user);
    }


    record loginResponse(@JsonProperty("access") String a, @JsonProperty("refresh") String r) {
    }

    @PostMapping("/login")
    public loginResponse login(@RequestBody User user, HttpServletResponse response) {

        //Надо разобраться зачем VAR без него как то странно работает
        var login = service.isLoginValidate(user.getEmail(), user.getPassword());
        String access = login.getAccessToken().getToken();
        String refresh = login.getRefreshToken().getToken();

        //ебашу в куки
        Cookie cookie = new Cookie("refresh_token", refresh);
        cookie.setMaxAge(36000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new loginResponse(access, refresh);
    }

    record newAccess(@JsonProperty("new_access") String token) {
    }

    @PostMapping("/refresh")
    public newAccess refresh(@CookieValue("refresh_token") String refreshToken) {
            //получили куки
        //System.out.println(refreshToken);
        return new newAccess(service.getNewAccessToken(refreshToken));
    }

    record LogoutResponse(String message) {
    }

    @PostMapping(value = "/logout")
    public LogoutResponse logout(HttpServletResponse response, @CookieValue("refresh_token") String token) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new LogoutResponse("success");
    }

    /*такая сериализация в Json ответы*/
    record responseUser(@JsonProperty("name") String name, @JsonProperty("id") Long id) {
    }
    /*получим конечного пользователя*/
    @GetMapping("/user")
    public responseUser userAuthenticated(HttpServletRequest request) {
        var user = (User) request.getAttribute("user");
        return new responseUser(user.getName(), user.getId());
    }
}
