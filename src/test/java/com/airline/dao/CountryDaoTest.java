package com.airline.dao;

import com.airline.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({CountryDao.class})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CountryDaoTest {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void getCountriesTest(){
        List<Country> countries = countryDao.findCountries();
        assertFalse(countries.isEmpty());
        Country country = countries.get(0);
        assertNotNull(country.getIso());
        assertNotNull(country.getName());
    }
}
