package com.toy.karaoke.web.entity.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginSVCImpl implements LoginSVC{
    private final LoginDAO loginDAO;
    @Override
    public LoginForm login(String email, String password) {
        return loginDAO.login(email,password);
    }
}
