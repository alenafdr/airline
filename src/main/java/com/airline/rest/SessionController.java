package com.airline.rest;

import com.airline.model.dto.UserEntityDTO;
import com.airline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Класс используется для обработки входящих запросов по адресу "/api/session/", имеет методы для обработки запросов
 * POST
 * DELETE
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/api/session/")
public class SessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    private UserService userService;

    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод служит для авторизации, принимает на вход JSON с параметрами login и password, добавляет в текущую
     * сессию атрибуты для прохождения аутентификации
     *
     * @param request для установки атрибутов сессии
     * @param json    {@link Map<String, String>} со значения login и password
     * @return {@link ResponseEntity<UserEntityDTO>}
     */

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntityDTO> login(HttpServletRequest request,
                                               @RequestBody Map<String, String> json) {
        String login = json.get("login");
        UserEntityDTO userEntityDTO = userService.getUserByLogin(login);
        if (userEntityDTO.getPassword().equals(json.get("password"))) {
            request.getSession().setAttribute("login", userEntityDTO.getLogin());
            request.getSession().setAttribute("userId", userEntityDTO.getId());
            request.getSession().setAttribute("userType", userEntityDTO.getUserType());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userEntityDTO, HttpStatus.OK);
    }

    /**
     * Метод служит для выхода из сеанса, очистки атрибутов сессии. Сама сессия не уничтожается, но пройти
     * аутентификацию с данной сессией в дальнейшем будет невозможно
     *
     * @param request для очистки атрибутов сессии
     * @return {@link ResponseEntity<Object>}
     */

    @DeleteMapping(value = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("userType");
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
