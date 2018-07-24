package com.airline.dao;

import com.airline.model.Plane;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaneDao {
    private static final Logger LOG = LoggerFactory.getLogger(PlaneDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PlaneDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Plane findPlanetById(Long id){
        Plane entity = null;
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "PlaneMapper.findPlaneById";
            entity = (Plane) session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entity;
    }

    public Plane findPlaneByName(String name){
        Plane entity = null;
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "PlaneMapper.findPlaneByName";
            entity = (Plane) session.selectOne(query, name);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entity;
    }
}
