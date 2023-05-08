package com.example.backend.services;

import com.example.backend.entity.User;
import com.example.backend.exeptions.ErrorAuth;
import com.example.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    @Autowired
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    /*теперь не просто jwt token, а два отдельных токена*/

    private final String accessTokenSecret;
    private final String refreshTokenSecret;


    /*репозитория который является интерфейсом
    реализует CRUD приложение*/
    public AuthService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            @Value("${application.security.access-token-secret}") String accessTokenSecret,
            @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    //регистрация нового пользователя,
    //пророботка моменты, когда Такоей email уже есть
    public String saveUser(User user) {
        // проверим есть ли такой email
        if (userRepo.existsByEmail(user.getEmail())) {
            return "Данный E-mail уже зарегистрирован!";
        }
        ;
        //если нет такого логина, беру пароль, кодирую, и обратно вставляю
        String password = user.getPassword();
        String new_pass = passwordEncoder.encode(password);
        user.setPassword(new_pass);

        //сохраняю в репозитории, а репозиторий отправляет в базу данных
        userRepo.save(user);
        return "Пользователь зарегистрирован!";
    }

    public Iterable<User> allUsers() {
        return userRepo.findAll();
    }


    // валидаци по паролью и логину
    public Login isLoginValidate(String email, String password) {

        var user = userRepo.findByEmail(email) // и чо зак VAR ключевое слово мне непонятное
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //Хз зачем но надо
        //вообще такого логина нет

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ErrorAuth();
        } else {
            return Login.of(user.getId(),accessTokenSecret,refreshTokenSecret);
        }
    }


    //вот это надо пересмотреть обяхательно
    public User getUserFromToken(String token) {
        return userRepo.findById(JwtToken.from(token,accessTokenSecret).getUserId()) // и чо зак VAR ключевое слово мне непонятное
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String getNewAccessToken(String token) {
        Long id = JwtToken.from(token,refreshTokenSecret).getUserId();// и чо зак VAR ключевое слово мне непонятное
        return Token.of(id,1L,accessTokenSecret).getToken();
    }

}
