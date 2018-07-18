package com.airline.dao;

import com.airline.model.Period;
import com.airline.model.PeriodFlight;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeriodDao {

    private static final Logger LOG = LoggerFactory.getLogger(PeriodDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PeriodDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Period> listPeriod() {
        List<Period> entities = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.findAllPeriods";
            entities = session.selectList(query);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entities;

    }

    public List<Period> selectPeriodsByFlightId(Long id){
        List<Period> entities = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.selectPeriodsByFlightId";
            entities = session.selectList(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entities;
    }

    public Period selectPeriodById(Long id){
        Period entity = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.selectPeriodById";
            entity = session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entity;
    }

    public void savePeriodForFlight(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.insertPeriodForFlight";
            session.insert(query, periodFlight);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public void updatePeriodForFlight(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.updatePeriodForFlight";
            session.insert(query, periodFlight);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public void delete(PeriodFlight periodFlight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.deletePeriodForFlight";
            session.delete(query, periodFlight);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }
}
