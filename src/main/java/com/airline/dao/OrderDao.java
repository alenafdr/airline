package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Order;
import com.airline.model.Ticket;
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
public class OrderDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDao.class);

    private SqlSessionFactory sqlSessionFactory;
    private TicketDao ticketDao;

    @Autowired
    public OrderDao(SqlSessionFactory sqlSessionFactory, TicketDao ticketDao) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.ticketDao = ticketDao;
    }

    @Transactional
    public Long saveOrder(Order order) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "OrderMapper.insertOrder";
            session.insert(query, order);

            for (Ticket ticket : order.getTickets()) {
                ticket.setOrder(order);
                ticketDao.save(ticket);
            }

        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return order.getId();
    }

    public Optional<Order> findOrderById(Long id) {
        Order order = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "OrderMapper.findOrderById";
            order = (Order) session.selectOne(query, id);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return Optional.ofNullable(order);
    }

    public List<Order> findOrdersByParameters(Order order){
        List<Order> orders;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "OrderMapper.findOrdersByParameters";
            orders = session.selectList(query, order);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }
        return orders;
    }

}
