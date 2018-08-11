package com.airline.dao;

import com.airline.exceptions.ConnectDataBaseException;
import com.airline.exceptions.DataBaseException;
import com.airline.model.Country;
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
public class CountryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public CountryDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Optional<Country> findCountryByIso(String iso) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "NationalityMapper.selectCountryByIso";
            Country country = session.selectOne(query, iso);
            return Optional.ofNullable(country);
        } catch (PersistenceException pe) {
            LOGGER.error(pe.getMessage());
            if (pe.getCause() instanceof CannotGetJdbcConnectionException) {
                throw new ConnectDataBaseException("No connection to database");
            } else {
                throw new DataBaseException("Database error");
            }
        }

    }

    public List<Country> findCountries() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            String query = "NationalityMapper.selectCountries";
            List<Country> countries = session.selectList(query);
            return countries;
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
