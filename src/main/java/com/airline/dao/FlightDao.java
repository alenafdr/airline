package com.airline.dao;

import com.airline.model.*;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightDao {

    private static final Logger LOG = LoggerFactory.getLogger(FlightDao.class);

    private SqlSessionFactory sqlSessionFactory;

    private PriceDao priceDao;

    private PeriodDao periodDao;

    private DepartureDao departureDao;

    @Autowired
    public FlightDao(SqlSessionFactory sqlSessionFactory, PriceDao priceDao, PeriodDao periodDao, DepartureDao departureDao) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.priceDao = priceDao;
        this.periodDao = periodDao;
        this.departureDao = departureDao;
    }

    public Long save(Flight flight){
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "FlightMapper.insertFlight";
            flight.setId((long)session.insert(query, flight));

            LOG.info(flight.getId().toString());

            if (!flight.getPrices().isEmpty()){
                for(Price price : flight.getPrices()){
                    price.setFlight(flight);
                    priceDao.save(price);
                }
            }

            if (!flight.getPeriods().isEmpty()){
                for(Period period : flight.getPeriods()){
                    PeriodFlight periodFlight = new PeriodFlight(period.getId(), flight.getId());
                    periodDao.savePeriodForFlight(periodFlight);
                }
            }

            if (!flight.getDepartures().isEmpty()){
                for(Departure departure : flight.getDepartures()){
                    departure.setFlight(flight);
                    departureDao.save(departure);
                }
            }

        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return flight.getId();
    }

    public Flight findOne(Long id){
        Flight entity = null;
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "FlightMapper.selectFlightById";
            entity = (Flight) session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
        return entity;
    }

    public void update(Flight flight){
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "FlightMapper.updateFlight";
            session.update(query, flight);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

    public void delete(Long id){
        try (SqlSession session = sqlSessionFactory.openSession()){
            String query = "FlightMapper.deleteFlight";
            session.delete(query, id);
        } catch (PersistenceException pe) {
            LOG.error(pe.getMessage());
        }
    }

}
