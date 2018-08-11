package com.airline.rest;

import com.airline.dto.validation.Exist;
import com.airline.dto.validation.New;
import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import com.airline.model.dto.UserEntityDTO;
import com.airline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Класс используется для обработки входящих запросов по адресам "/api/admin", "/api/client", "/api/account"
 * и "/api/clients". Имеет методы для обработки запросов:
 * POST "/api/admin"
 * PUT "/api/admin"
 * POST "/api/client"
 * PUT "/api/client"
 * GET "/api/account"
 * GET "/api/clients"
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/api/")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод обрабатывает входящие POST запросы по адресу "/api/admin". Принимает объект {@link UserAdminDTO}, который
     * проходит предварительную валидацию (валидация настроена с помощью аннотаций в объекте {@link UserAdminDTO}).
     * Для зарегистрированного пользователя проходит автологин (выставляются необходимые для аутентификации
     * атрибуты сессии)
     *
     * @param userAdminDTO валидный объект для регистрации
     * @param request      для установки атрибутов сессии
     * @return {@link ResponseEntity<UserAdminDTO>}
     */

    @PostMapping(value = "/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserAdminDTO> registrationAdmin(@RequestBody
                                                          @Validated(value = New.class)
                                                                  UserAdminDTO userAdminDTO,
                                                          HttpServletRequest request) {
        userService.saveAdmin(userAdminDTO);
        request.getSession().setAttribute("login", userAdminDTO.getLogin());
        request.getSession().setAttribute("userId", userAdminDTO.getId());
        request.getSession().setAttribute("userType", userAdminDTO.getUserType());
        return new ResponseEntity<>(userAdminDTO, HttpStatus.OK);
    }

    /**
     * Метод обрабатывает входящие PUT запросы по адресу "/api/admin". Принимает объект {@link UserAdminDTO}, который
     * проходит предварительную валидацию (валидация настроена с помощью аннотаций в объекте {@link UserAdminDTO}).
     * По условиям валидации поле login должно быть пустым, поэтому login берется из атрибутов сессии.
     *
     * @param userAdminDTO валидный объект для регистрации
     * @param request      необходим для получения атрибутов сессии
     * @return {@link ResponseEntity<UserAdminDTO>}
     */

    @PutMapping(value = "/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserAdminDTO> updateAdmin(@RequestBody
                                                    @Validated(value = Exist.class)
                                                            UserAdminDTO userAdminDTO,
                                                    HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        userAdminDTO.setLogin(login);
        return new ResponseEntity<>(userService.updateAdmin(userAdminDTO), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает входящие POST запросы по адресу "/api/client". Принимает объект {@link UserClientDTO}, который
     * проходит предварительную валидацию (валидация настроена с помощью аннотаций в объекте {@link UserClientDTO}).
     * Для зарегистрированного пользователя проходит автологин (выставляются необходимые для аутентификации
     * атрибуты сессии)
     *
     * @param userClientDTO валидный объект для регистрации
     * @param request       для установки атрибутов сессии
     * @return {@link ResponseEntity<UserClientDTO>}
     */

    @PostMapping(value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserClientDTO> registrationClient(@RequestBody
                                                            @Validated(value = New.class)
                                                                    UserClientDTO userClientDTO,
                                                            HttpServletRequest request) {
        userService.saveClient(userClientDTO);
        request.getSession().setAttribute("login", userClientDTO.getLogin());
        request.getSession().setAttribute("userId", userClientDTO.getId());
        request.getSession().setAttribute("userType", userClientDTO.getUserType());
        return new ResponseEntity<>(userClientDTO, HttpStatus.OK);
    }

    /**
     * Метод обрабатывает входящие PUT запросы по адресу "/api/client". Принимает объект {@link UserClientDTO}, который
     * проходит предварительную валидацию (валидация настроена с помощью аннотаций в объекте {@link UserClientDTO}).
     * По условиям валидации поле login должно быть пустым, поэтому login берется из атрибутов сессии.
     *
     * @param userClientDTO валидный объект для регистрации
     * @param request       необходим для получения атрибутов сессии
     * @return {@link ResponseEntity<UserClientDTO>}
     */

    @PutMapping(value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserClientDTO> updateClient(@RequestBody
                                                      @Validated(value = Exist.class)
                                                              UserClientDTO userClientDTO,
                                                      HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        userClientDTO.setLogin(login);
        return new ResponseEntity<>(userService.updateClient(userClientDTO), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает входящие GET запросы по адресу "/api/account". Считывает атрибут сессии login и передает
     * запрос в сервис.
     *
     * @param request для считывания атрибутов сессии
     * @return {@link ResponseEntity<UserEntityDTO>}
     */

    @GetMapping(value = "/account",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntityDTO> readAccount(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(userService.getUserByLogin(login), HttpStatus.OK);
    }

    /**
     * Метод обрабатывает входящие GET запросы по адресу "/api/clients". Возвращает список клиентов.
     *
     * @return
     */

    @GetMapping(value = "/clients")
    public ResponseEntity<List<UserClientDTO>> listClients() {
        return new ResponseEntity<>(userService.getListClients(), HttpStatus.OK);
    }
}
