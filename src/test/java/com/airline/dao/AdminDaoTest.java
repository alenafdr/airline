package com.airline.dao;

import com.airline.model.UserAdmin;
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
@Import({AdminDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminDaoTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDaoTest.class);

    @Autowired
    AdminDao adminDao;

    @Test
    public void insertAdminTest() {
        adminDao.save(buildAdmin());
        UserAdmin userAdmin = adminDao.findByLogin("test").get();
        assertNotNull(userAdmin);
    }

    @Test
    public void updateAdminTest() {
        UserAdmin userAdmin = adminDao.findByLogin("ivanovadmin").get();
        userAdmin.setLastName("test");
        adminDao.update(userAdmin);

        userAdmin = adminDao.findByLogin("ivanovadmin").get();
        assertTrue(userAdmin.getLastName().equals("test"));
    }

    public UserAdmin buildAdmin() {
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFirstName("test");
        userAdmin.setLastName("test");
        userAdmin.setPosition("test");
        userAdmin.setLogin("test");
        userAdmin.setPassword("test");
        return userAdmin;
    }
}
