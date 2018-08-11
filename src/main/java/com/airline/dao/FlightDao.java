package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.exceptions.FlightNotFoundException;
import com.airline.model.*;
import com.airline.model.db.DepartureDB;
import com.airline.model.db.PriceDB;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class FlightDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightDao.class);

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

    public boolean isPresent(String name) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.selectCountByName";
            boolean count = (boolean) session.selectOne(query, name);
            return count;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
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
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return flight.getId();
    }

    public Optional<Flight> findOne(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.selectFlightById";
            Flight entity = (Flight) session.selectOne(query, id);
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

    @Transactional
    public void update(Flight flightNew) {
        Flight flightOld = findOne(flightNew.getId())
                .orElseThrow(() -> new FlightNotFoundException("Not found flight with id " + flightNew.getId()));

        try (SqlSession session = sqlSessionFactory.openSession()) {

            String query = "FlightMapper.updateFlight";
            session.update(query, flightNew);

            //prices
            for (PriceDB priceDB : flightOld.getPricesDB()) {
                priceDB.setFlight(flightNew);
                if (!flightNew.getPricesDB().contains(priceDB)) {
                    priceDao.delete(priceDB);
                }
            }

            for (Price price : flightNew.getPrices()) {
                price.setFlight(flightNew);
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
                    departureDao.delete(departureDB.getId());
                }
            }

            for (Departure departure : flightNew.getDepartures()) {
                departure.setFlight(flightNew);
                departureDao.update(departure);
            }

        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public void delete(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.deleteFlight";
            session.delete(query, id);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<Flight> findListByParameters(Flight flight) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "FlightMapper.selectListByParameters";
            List<Flight> entities = session.selectList(query, flight);
            return entities;
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
