package com.airline.dao;

import com.airline.model.UserAdmin;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminDao {

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public AdminDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Long save(UserAdmin userAdmin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "AdminMapper.insertAdmin";
            session.insert(query, userAdmin);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            /*if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }*/
        }
        return userAdmin.getId();
    }

    public void update(UserAdmin userAdmin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "AdminMapper.updateAdmin";
            session.update(query, userAdmin);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            /*if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }*/
        }
    }

    public Optional<UserAdmin> findByLogin(String login) {
        UserAdmin userAdmin = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "AdminMapper.findAdminByLogin";
            userAdmin = (UserAdmin) session.selectOne(query, login);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            /*if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }*/
        }
        return Optional.ofNullable(userAdmin);
    }
}
