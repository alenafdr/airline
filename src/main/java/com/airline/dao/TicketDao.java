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
            return ticket.getId();
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public Optional<Ticket> findTicketById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findTicketById";
            Ticket ticket = session.selectOne(query, id);
            return Optional.ofNullable(ticket);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<Ticket> findTicketsByOrderId(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findTicketsByOrderId";
            List<Ticket> tickets = session.selectList(query, id);
            return tickets;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
    }

    public List<Ticket> findOccupyPlaces(Long departureId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "TicketMapper.findOccupyPlaces";
            List<Ticket>tickets = session.selectList(query, departureId);
            return tickets;
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
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
