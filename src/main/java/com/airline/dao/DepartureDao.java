package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Departure;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DepartureDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartureDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public DepartureDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void save(Departure departure) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.insertDeparture";
            session.insert(query, departure);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<Departure> findDepartureByFlightId(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.selectDeparturesByFlightId";
            List<Departure> entities = session.selectList(query, id);
            return entities;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<Departure> findDepartureById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.selectDepartureById";
            Departure departure = session.selectOne(query, id);
            return Optional.ofNullable(departure);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void update(Departure departure) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.updateDeparture";
            session.insert(query, departure);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void delete(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.deleteDeparture";
            session.delete(query, id);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<Departure> findDepartureByFlightIdAndDate(Departure departure) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "DepartureMapper.selectDepartureByFlightIdAndDate";
            Departure newDeparture = session.selectOne(query, departure);
            return Optional.ofNullable(newDeparture);
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
