package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Price;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PriceDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void save(Price price) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.insertPrice";
            session.insert(query, price);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<Price> findPricesByFlightId(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.selectPricesForFlight";
            List<Price> entities = session.selectList(query, id);
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

    public void update(Price price) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.updatePrice";
            session.insert(query, price);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void delete(Price price) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.deletePrice";
            session.delete(query, price);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void delete(Long flightId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.deletePricesByFlightId";
            session.delete(query, flightId);
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
