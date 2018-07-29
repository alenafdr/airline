package com.airline.rest;

import com.airline.model.BasedUserEntity;
import com.airline.model.dto.BasedUserDTO;
import com.airline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/session/")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private UserService userService;

    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> login(HttpServletRequest request,
                                        @RequestBody Map<String, String> json){
        String login = json.get("login");
        Object user = userService.getUserByLogin(login);
        BasedUserDTO basedUserDTO = (BasedUserDTO) user;
        if (basedUserDTO.getPassword().equals(json.get("password"))){
            request.getSession().setAttribute("login", login);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> logout(HttpServletRequest request){
        request.getSession().removeAttribute("login");
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
