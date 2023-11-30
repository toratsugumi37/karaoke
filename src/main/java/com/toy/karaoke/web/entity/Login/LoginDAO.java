package com.toy.karaoke.web.entity.Login;

public interface LoginDAO {
    LoginForm login(String email, String password);
}
