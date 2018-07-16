package com.airline.dao;

import com.airline.model.Period;
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

    public void savePeriodForFlight(Period period) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PeriodMapper.insertPeriod";
            session.insert(query, period);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }
}
