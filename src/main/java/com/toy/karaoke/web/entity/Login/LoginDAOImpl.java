package com.toy.karaoke.web.entity.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginDAOImpl implements LoginDAO{
    private final NamedParameterJdbcTemplate template;
    @Override
    public LoginForm login(String email, String password) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT EMAIL, passwd ");
        sql.append(  "FROM MEMBER ");
        sql.append( "WHERE EMAIL = :email AND ");
        sql.append(    "passwd = :password ");

        Map<String,String> param = Map.of("email",email,
                                          "password",password);

        List<LoginForm> loginAccessed = template.query(sql.toString(),param,new BeanPropertyRowMapper<>(LoginForm.class));
        LoginForm logined = loginAccessed.get(0);

        return logined;
    }
}
