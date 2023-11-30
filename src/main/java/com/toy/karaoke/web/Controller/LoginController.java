package com.toy.karaoke.web.Controller;

import com.toy.karaoke.web.entity.Login.LoginForm;
import com.toy.karaoke.web.entity.Login.LoginSVC;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginSVC loginSVC;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user, HttpSession session){
        String username = user.get("username");
        String password = user.get("password");
        boolean loginSuccess = false;

        LoginForm loginForm = loginSVC.login(username,password);
        if(loginForm.getEmail()!=null && loginForm.getPasswd() != null){
            loginSuccess = true;
        } else {
            loginSuccess = false;
        }

        if (loginSuccess){
            Map<String,String> body = new HashMap<>();
            body.put("email", loginForm.getEmail());
            session.setAttribute("email",loginForm.getEmail());
            return ResponseEntity.ok().body(body);
        } else {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
