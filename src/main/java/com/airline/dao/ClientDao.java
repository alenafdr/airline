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

import java.util.List;
import java.util.Optional;

@Component
public class ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ClientDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Long save(UserClient userClient) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.insertClient";
            session.insert(query, userClient);
            return userClient.getId();
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void update(UserClient userClient) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.updateClient";
            session.update(query, userClient);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<UserClient> findByLogin(String login) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.findClientByLogin";
            UserClient userClient = (UserClient) session.selectOne(query, login);
            return Optional.ofNullable(userClient);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<UserClient> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.findClientById";
            UserClient userClient = (UserClient) session.selectOne(query, id);
            return Optional.ofNullable(userClient);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<UserClient> findList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClientMapper.list";
            List<UserClient> userClients = session.selectList(query);
            return userClients;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }
}
