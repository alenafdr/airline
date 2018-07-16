package com.airline.dao;

import com.airline.model.Departure;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartureDao {
    private static final Logger LOG = LoggerFactory.getLogger(DepartureDao.class);

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
            LOG.error(pe.getMessage());
        }
    }

    public List<Departure> selectDepartureByFlightId(Long id){
        List<Departure> entities = null;
        try (SqlSession session = sqlSessionFactory.openSession();){
            String query = "DepartureMapper.selectDeparturesByFlightId";
            entities = session.selectList(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entities;
    }
}
