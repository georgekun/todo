package com.example.backend.services;

//это класс объеденяет два токена
//создает 2 докена с разными ключами и временем. Соответвенно есть начальный токен и конечный
public class Login {


    private final Token accessToken;
    private final Token refreshToken;


    private Login(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Login of(Long userId, String accessSecret, String refreshSecret){
        return new Login(
                Token.of(userId,1440L,accessSecret),//тут должно быть 1l
                Token.of(userId,10080L,refreshSecret)//тут должно быть 1440l
        );
    }
    public Token getAccessToken() {
        return accessToken;
    }
    public Token getRefreshToken() {
        return refreshToken;
    }
}
