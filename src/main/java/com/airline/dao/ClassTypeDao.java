package com.airline.dao;

import com.airline.model.ClassType;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassTypeDao {

    private static final Logger LOG = LoggerFactory.getLogger(ClassTypeDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ClassTypeDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public ClassType findClassTypeById(Long id) {

        SqlSession session = sqlSessionFactory.openSession();

        ClassType entity = null;
        try {
            String query = "ClassTypeMapper.findClassTypeById";
            entity = (ClassType) session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        } finally {
            session.close();
        }
        return entity;

    }
}
