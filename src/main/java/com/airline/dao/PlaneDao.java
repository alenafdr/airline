package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Plane;
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
public class PlaneDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaneDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PlaneDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Optional<Plane> findPlanetById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PlaneMapper.findPlaneById";
            Plane entity = (Plane) session.selectOne(query, id);
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

    public Optional<Plane> findPlaneByName(String name) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PlaneMapper.findPlaneByName";
            Plane entity = (Plane) session.selectOne(query, name);
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

    public List<Plane> getPlanes() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PlaneMapper.findPlanes";
            List<Plane> planes = session.selectList(query);
            return planes;
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
