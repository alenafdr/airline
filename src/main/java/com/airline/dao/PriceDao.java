package com.airline.dao;

import com.airline.model.Price;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceDao {

    private static final Logger LOG = LoggerFactory.getLogger(PriceDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PriceDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void save(Price price){
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "PriceMapper.insertPrice";
            session.insert(query, price);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public List<Price> findPricesByFlightId(Long id) {
        List<Price> entities = null;
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "PriceMapper.selectPricesForFlight";
            entities = session.selectList(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entities;

    }

    public void update(Price price) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.updatePrice";
            session.insert(query, price);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public void delete(Price price){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.deletePrice";
            session.delete(query, price);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public void delete(Long flightId){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "PriceMapper.deletePricesByFlightId";
            session.delete(query, flightId);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }
}
