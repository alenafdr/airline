package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Ticket;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public TicketDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Long save(Ticket ticket) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.insertTicket";
            session.insert(query, ticket);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return ticket.getId();
    }

    public Optional<Ticket> findTicketById(Long id) {
        Ticket ticket;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findTicketById";
            ticket = session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return Optional.ofNullable(ticket);
    }

    public List<Ticket> findTicketsByOrderId(Long id) {
        List<Ticket> tickets;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findTicketsByOrderId";
            tickets = session.selectList(query, id);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return tickets;
    }

    public List<Ticket> findBusyPlaces(Long departureId) {
        List<Ticket> tickets;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findBusyPlaces";
            tickets = session.selectList(query, departureId);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return tickets;
    }

    public void updatePlaceInTicket(Ticket ticket) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.updatePlaceInTicket";
            session.selectList(query, ticket);
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
