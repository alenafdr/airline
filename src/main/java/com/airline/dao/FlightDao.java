package com.airline.dao;

import com.airline.model.*;
import com.airline.model.db.DepartureDB;
import com.airline.model.db.PriceDB;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FlightDao {

    private static final Logger logger = LoggerFactory.getLogger(FlightDao.class);

    private SqlSessionFactory sqlSessionFactory;
    private PriceDao priceDao;
    private PeriodDao periodDao;
    private DepartureDao departureDao;

    @Autowired
    public FlightDao(SqlSessionFactory sqlSessionFactory,
                     PriceDao priceDao,
                     PeriodDao periodDao,
                     DepartureDao departureDao) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.priceDao = priceDao;
        this.periodDao = periodDao;
        this.departureDao = departureDao;
    }

    @Transactional
    public Long save(Flight flight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.insertFlight";
            session.insert(query, flight);

            if (!flight.getPrices().isEmpty()) {
                for (Price price : flight.getPrices()) {
                    price.setFlight(flight);
                    priceDao.save(price);
                }
            }

            if (!flight.getPeriods().isEmpty()) {
                for (Period period : flight.getPeriods()) {
                    PeriodFlight periodFlight = new PeriodFlight(period.getId(), flight.getId());
                    periodDao.savePeriodForFlight(periodFlight);
                }
            }

            if (!flight.getDepartures().isEmpty()) {
                for (Departure departure : flight.getDepartures()) {
                    departure.setFlight(flight);
                    departureDao.save(departure);
                }
            }

        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
        }
        return flight.getId();
    }

    public Flight findOne(Long id) {
        Flight entity = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.selectFlightById";
            entity = (Flight) session.selectOne(query, id);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
        }
        return entity;
    }

    @Transactional
    public void update(Flight flightNew) {
        Flight flightOld = findOne(flightNew.getId());

        try (SqlSession session = sqlSessionFactory.openSession()) {

            String query = "FlightMapper.updateFlight";
            session.update(query, flightNew);

            //prices
            for (PriceDB priceDB : flightOld.getPricesDB()) {
                priceDB.setFlight(flightNew);
                if (!flightNew.getPricesDB().contains(priceDB)) {
                    logger.info("delete " + priceDB);
                    priceDao.delete(priceDB);
                }
            }

            for (Price price : flightNew.getPrices()) {
                price.setFlight(flightNew);
                logger.info("update " + price);
                priceDao.update(price);
            }

            //periods
            for (Period period : flightOld.getPeriods()) {
                PeriodFlight periodFlight = new PeriodFlight(period.getId(), flightNew.getId());
                if (!flightNew.getPeriods().contains(period)) {
                    periodDao.delete(periodFlight);
                }
            }

            for (Period period : flightNew.getPeriods()) {
                PeriodFlight periodFlight = new PeriodFlight(period.getId(), flightNew.getId());
                periodDao.updatePeriodForFlight(periodFlight);
            }

            //departures
            for (DepartureDB departureDB : flightOld.getDeparturesDB()) {
                departureDB.setFlight(flightNew);
                if (!flightNew.getDeparturesDB().contains(departureDB)) {
                    logger.info("delete " + departureDB);
                    departureDao.delete(departureDB.getId());
                }
            }

            for (Departure departure : flightNew.getDepartures()) {
                logger.info("update " + departure);
                departure.setFlight(flightNew);
                departureDao.update(departure);
            }

        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
        }
    }

    public void delete(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.deleteFlight";
            session.delete(query, id);
        } catch (PersistenceException pe) {
            logger.error(pe.getMessage());
        }
    }

}
