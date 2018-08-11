package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Period;
import com.airline.model.PeriodFlight;
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
public class PeriodDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PeriodDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Period> listPeriod() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.findAllPeriods";
            List<Period> entities = session.selectList(query);
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

    public List<Period> findPeriodsByFlightId(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.selectPeriodsByFlightId";
            List<Period> entities = session.selectList(query, id);
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

    public Optional<Period> findPeriodById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.selectPeriodById";
            Period entity = session.selectOne(query, id);
            return Optional.ofNullable(entity);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<Period> findPeriodByValue(String value) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.selectPeriodByValue";
            Period entity = session.selectOne(query, value);
            return Optional.ofNullable(entity);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void savePeriodForFlight(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.insertPeriodForFlight";
            session.insert(query, periodFlight);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void updatePeriodForFlight(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.updatePeriodForFlight";
            session.insert(query, periodFlight);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void delete(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.deletePeriodForFlight";
            session.delete(query, periodFlight);
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
