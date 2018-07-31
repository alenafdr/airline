package com.airline.dao;

import com.airline.model.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({ClientDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {
    private final static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    ClientDao clientDao;

    @Test
    public void insertClientTest(){
        clientDao.save(buildClient());
        UserClient userClient = clientDao.findByLogin("test").get();
        assertNotNull(userClient);
    }

    @Test
    public void updateClientTest(){
        UserClient userClient = clientDao.findByLogin("ivanov").get();
        userClient.setLastName("test");
        clientDao.update(userClient);

        userClient = clientDao.findByLogin("ivanov").get();
        assertTrue(userClient.getLastName().equals("test"));
    }

    public UserClient buildClient(){
        UserClient userClient = new UserClient();
        userClient.setFirstName("test");
        userClient.setLastName("test");
        userClient.setPhone("test");
        userClient.setEmail("test");
        userClient.setLogin("test");
        userClient.setPassword("test");
        return userClient;
    }
}
