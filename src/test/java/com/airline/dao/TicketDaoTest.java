package com.airline.dao;

import com.airline.model.Order;
import com.airline.model.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import({TicketDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketDaoTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(TicketDaoTest.class);

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void findTicketByIdTest() {
        Ticket ticket = ticketDao.findTicketById(11L).get();

        assertNotNull(ticket.getOrder());
        assertNotNull(ticket.getOrder().getDeparture().getFlight().getPlane());
    }

    @Test
    public void insertTicketWithRequiredFieldsTest() {
        ticketDao.save(buildTicket());
    }

    @Test
    public void updatePlaceInTicket() {
        Ticket ticket = buildTicket();

        Long id = ticketDao.save(ticket);
        ticket = ticketDao.findTicketById(id).get();

        assertNull(ticket.getPlace());

        ticket.setPlace("1A");
        ticketDao.updatePlaceInTicket(ticket);
        ticket = ticketDao.findTicketById(id).get();

        assertEquals(ticket.getPlace(), "1A");

    }

    @Test
    public void findEngagedPlacesTest() {
        for (Ticket ticket : ticketDao.findBusyPlaces(2L)) {
            assertNotNull(ticket.getPlace());
        }
    }

    private Ticket buildTicket() {
        Ticket ticket = new Ticket();
        ticket.setFirstName("test");
        ticket.setLastName("test");
        ticket.setPassport("test");
        ticket.setOrder(new Order(1L));

        return ticket;
    }
}
