package com.airline.service;

import com.airline.dao.AdminDao;
import com.airline.dao.ClientDao;
import com.airline.dto.mapper.UserAdminMapper;
import com.airline.dto.mapper.UserClientMapper;
import com.airline.exceptions.AlreadyExistsException;
import com.airline.exceptions.ChangePasswordException;
import com.airline.exceptions.UserNotFoundException;
import com.airline.model.UserAdmin;
import com.airline.model.UserClient;
import com.airline.model.dto.UserAdminDTO;
import com.airline.model.dto.UserClientDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelMapper.class)
public class UserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);
    private static final String LOGIN = "test login";
    private static final String PASSWORD = "test password";
    private static final String NEW_PASSWORD = "new test password";

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private AdminDao adminDao;

    @MockBean
    private ClientDao clientDao;

    private UserServiceImpl userService;

    @Before
    public void init() {
        UserClientMapper userClientMapper = new UserClientMapper(modelMapper);
        UserAdminMapper userAdminMapper = new UserAdminMapper(modelMapper);
        userService = new UserServiceImpl(adminDao, clientDao, userClientMapper, userAdminMapper);
    }

    @Test
    public void saveAdminTest() {
        when(adminDao.save(any(UserAdmin.class))).thenReturn(1L);
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        UserAdminDTO userAdminDTO = userService.saveAdmin(buildUserAdminDTO());

        assertNotNull(userAdminDTO.getId());
    }

    @Test(expected = AlreadyExistsException.class)
    public void saveAdminAlreadyExistsTest() {
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(new UserAdmin()));
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        UserAdminDTO userAdminDTO = userService.saveAdmin(buildUserAdminDTO());
    }

    @Test
    public void updateAdminTest() {
        doNothing().when(adminDao).update(any(UserAdmin.class));
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(buildUserAdmin()));

        UserAdminDTO userAdminDTO = buildUserAdminDTO();
        userAdminDTO.setNewPassword(NEW_PASSWORD);
        userAdminDTO.setOldPassword(PASSWORD);
        userAdminDTO = userService.updateAdmin(userAdminDTO);

        assertEquals(userAdminDTO.getPassword(), NEW_PASSWORD);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateAdminUserNotFoundTest() {
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        UserAdminDTO userAdminDTO = userService.updateAdmin(buildUserAdminDTO());
    }

    @Test(expected = ChangePasswordException.class)
    public void updateAdminChangePasswordExcTest() {
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(buildUserAdmin()));

        UserAdminDTO userAdminDTO = buildUserAdminDTO();
        userAdminDTO.setNewPassword(NEW_PASSWORD);
        userAdminDTO.setOldPassword(PASSWORD + "wrong");
        userAdminDTO = userService.updateAdmin(userAdminDTO);
    }

    @Test
    public void saveClientTest() {
        when(clientDao.save(any(UserClient.class))).thenReturn(1L);
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        UserClientDTO userClientDTO = userService.saveClient(buildUserClientDTO());

        assertNotNull(userClientDTO.getId());

    }

    @Test(expected = AlreadyExistsException.class)
    public void saveClientAlreadyExistsTest() {
        when(clientDao.save(any(UserClient.class))).thenReturn(1L);
        when(adminDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(new UserClient()));
        UserClientDTO userClientDTO = userService.saveClient(buildUserClientDTO());
    }

    @Test
    public void updateClientTest() {
        doNothing().when(clientDao).update(any(UserClient.class));
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(buildUserClient()));

        UserClientDTO userClientDTO = buildUserClientDTO();
        userClientDTO.setNewPassword(NEW_PASSWORD);
        userClientDTO.setOldPassword(PASSWORD);
        userClientDTO = userService.updateClient(userClientDTO);

        assertEquals(userClientDTO.getPassword(), NEW_PASSWORD);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateClientUserNotFoundTest() {
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        UserClientDTO userClientDTO = userService.updateClient(buildUserClientDTO());
    }

    @Test(expected = ChangePasswordException.class)
    public void updateClientChangePasswordExcTest() {
        when(clientDao.findByLogin(LOGIN)).thenReturn(Optional.ofNullable(buildUserClient()));

        UserClientDTO userClientDTO = buildUserClientDTO();
        userClientDTO.setNewPassword(NEW_PASSWORD);
        userClientDTO.setOldPassword(PASSWORD + "wrong");
        userClientDTO = userService.updateClient(userClientDTO);
    }

    private UserAdminDTO buildUserAdminDTO() {
        UserAdminDTO userAdmin = new UserAdminDTO();
        userAdmin.setFirstName("test first name");
        userAdmin.setLastName("test last name");
        userAdmin.setPosition("test position");
        userAdmin.setLogin(LOGIN);
        userAdmin.setPassword(PASSWORD);
        return userAdmin;
    }

    private UserAdmin buildUserAdmin(){
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFirstName("test first name");
        userAdmin.setLastName("test last name");
        userAdmin.setPosition("test position");
        userAdmin.setLogin(LOGIN);
        userAdmin.setPassword(PASSWORD);
        return userAdmin;
    }

    private UserClientDTO buildUserClientDTO() {
        UserClientDTO userClient = new UserClientDTO();
        userClient.setFirstName("test first name");
        userClient.setLastName("test last name");
        userClient.setLogin(LOGIN);
        userClient.setPassword(PASSWORD);
        return userClient;
    }

    private UserClient buildUserClient(){
        UserClient userClient = new UserClient();
        userClient.setFirstName("test first name");
        userClient.setLastName("test last name");
        userClient.setLogin(LOGIN);
        userClient.setPassword(PASSWORD);
        return userClient;
    }
}
