package com.airline.dao;

import com.airline.model.Departure;
import com.airline.model.Order;
import com.airline.model.Ticket;
import com.airline.model.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({OrderDao.class, TicketDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderDaoTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderDaoTest.class);

    @Autowired
    private OrderDao orderDao;

    @Test
    public void getOrderByIdTest() {
        Order order = orderDao.getOrderById(11L).get();
        assertNotNull(order.getDeparture().getFlight());
        assertNotNull(order.getDeparture().getFlight().getPlane().getName());
    }

    @Test
    public void insertOrderTest() {
        orderDao.saveOrder(buildOrder());
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setDeparture(new Departure(1L));
        order.setUserClient(new UserClient(1L));

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(buildTicket(2L));
        tickets.add(buildTicket(3L));
        order.setTickets(tickets);
        return order;
    }

    private Ticket buildTicket(Long id) {
        Ticket ticket = new Ticket();
        ticket.setFirstName("test");
        ticket.setLastName("test");
        ticket.setPassport("test");
        ticket.setId(id);

        return ticket;
    }
}
