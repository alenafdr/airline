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

@RestController
@RequestMapping(value = "/api/")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserAdminDTO> registrationAdmin(@RequestBody
                                                          @Validated(value = New.class)
                                                                  UserAdminDTO userAdminDTO,
                                                          HttpServletRequest request) {
        userService.saveAdmin(userAdminDTO);
        request.getSession().setAttribute("login", userAdminDTO.getLogin());
        return new ResponseEntity<>(userAdminDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserAdminDTO> updateAdmin(@RequestBody
                                                    @Validated(value = Exist.class)
                                                            UserAdminDTO userAdminDTO) {
        return new ResponseEntity<>(userService.updateAdmin(userAdminDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserClientDTO> registrationClient(@RequestBody
                                                            @Validated(value = New.class)
                                                                    UserClientDTO userClientDTO,
                                                            HttpServletRequest request) {
        userService.saveClient(userClientDTO);
        request.getSession().setAttribute("login", userClientDTO.getLogin());
        return new ResponseEntity<>(userClientDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserClientDTO> updateClient(@RequestBody
                                                      @Validated(value = Exist.class)
                                                              UserClientDTO userClientDTO) {
        return new ResponseEntity<>(userService.updateClient(userClientDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/account",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntityDTO> readAccount(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        return new ResponseEntity<>(userService.getUserByLogin(login), HttpStatus.OK);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<UserClientDTO>> listClients() {
        return new ResponseEntity<>(userService.getListClients(), HttpStatus.OK);
    }
}
