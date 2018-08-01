package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.UserClient;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientDao {

    private static final Logger logger = LoggerFactory.getLogger(ClientDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ClientDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Long save(UserClient userClient) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.insertClient";
            session.insert(query, userClient);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return userClient.getId();
    }

    public void update(UserClient userClient) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.updateClient";
            session.update(query, userClient);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<UserClient> findByLogin(String login) {
        UserClient userClient = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.findClientByLogin";
            userClient = (UserClient) session.selectOne(query, login);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return Optional.ofNullable(userClient);
    }
}
