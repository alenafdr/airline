package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.ClassType;
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
public class ClassTypeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassTypeDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ClassTypeDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public ClassType findClassTypeById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClassTypeMapper.findClassTypeById";
            ClassType entity = (ClassType) session.selectOne(query, id);
            return entity;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<ClassType> findClassTypeByName(String name) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "ClassTypeMapper.findClassTypeByName";
            ClassType entity = (ClassType) session.selectOne(query, name);
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
}
