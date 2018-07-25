package com.airline.service;

import com.airline.dao.ClassTypeDao;
import com.airline.dao.FlightDao;
import com.airline.dao.PeriodDao;
import com.airline.dao.PlaneDao;
import com.airline.dtomapper.FlightDTOMapper;
import com.airline.model.*;
import com.airline.model.dto.FlightDTO;
import com.airline.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;
    private FlightDTOMapper flightDTOMapper;
    private PlaneDao planeDao;
    private PeriodDao periodDao;
    private ClassTypeDao classTypeDao;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao,
                             FlightDTOMapper flightDTOMapper,
                             PlaneDao planeDao,
                             PeriodDao periodDao,
                             ClassTypeDao classTypeDao) {
        this.flightDao = flightDao;
        this.flightDTOMapper = flightDTOMapper;
        this.planeDao = planeDao;
        this.periodDao = periodDao;
        this.classTypeDao = classTypeDao;
    }

    @Override
    public List<FlightDTO> listByParameters(FlightDTO flightDTO) {

        Flight flight = flightDTOMapper.convertToEntity(flightDTO,
                planeDao.findPlaneByName(flightDTO.getPlaneName()),
                null,
                null,
                null);
        return flightDao.listByParameters(flight)
                .stream()
                .map(flight1 -> flightDTOMapper.convertToDTO(flight))
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO save(FlightDTO flightDTO) {
        Flight flight = buildDependencies(flightDTO);
        flightDao.save(flight);
        return flightDTOMapper.convertToDTO(flight);
    }

    @Override
    public FlightDTO update(FlightDTO flightDTO) {

        Flight flight = buildDependencies(flightDTO);
        flightDao.update(flight);
        return flightDTOMapper.convertToDTO(flight);
    }

    @Override
    public void delete(Long id) {
        flightDao.delete(id);
    }

    @Override
    public FlightDTO getById(Long id) {
        return flightDTOMapper.convertToDTO(flightDao.findOne(id));
    }

    private Flight buildDependencies(FlightDTO flightDTO) {
        Plane plane = planeDao.findPlaneByName(flightDTO.getPlaneName());

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(classTypeDao.findClassTypeByName(ClassTypeEnum.BUSINESS.name()),
                flightDTO.getPriceBusiness()));
        prices.add(new Price(classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name()),
                flightDTO.getPriceEconomy()));

        List<Period> periods = flightDTO.getSchedule().getPeriods()
                .stream()
                .map(s -> periodDao.selectPeriodByValue(s))
                .collect(Collectors.toList());

        List<Departure> departures;
        if (!flightDTO.getDates().isEmpty()) {
            departures = new ArrayList<>();
            flightDTO.getDates().forEach(date -> departures.add(new Departure(date)));
        } else {
            departures = createDepartureByPeriods(periods,
                    flightDTO.getSchedule().getFromDate(),
                    flightDTO.getSchedule().getToDate());
        }

        return flightDTOMapper.convertToEntity(flightDTO, plane, prices, periods, departures);
    }

    private List<Departure> createDepartureByPeriods(List<Period> periods, Date dateStart, Date dateEnd) {
        LocalDate localDateStart = dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> localDates = Utils.getDatesBetween(localDateStart, localDateEnd);
        List<String> values = periods.stream().map(period -> period.getValue()).collect(Collectors.toList());
        String value = values.get(0);
        List<Date> dates = null;
        if (value.equalsIgnoreCase("daily")) {
            dates = localDates
                    .stream()
                    .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .collect(Collectors.toList());
        } else if (value.equalsIgnoreCase("odd") || value.equalsIgnoreCase("even")) {
            dates = getDatesByEven(value.equalsIgnoreCase("odd"), localDates);
        } else if (Utils.isInteger(value)) {
            dates = getDatesByNumbersDay(values.stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()), localDates);
        } else if (Utils.isDayOfWeek(value)) {
            dates = getDatesByDaysOfWeek(Utils.getDaysOfWeekByString(values), localDates);
        }

        return dates.stream().map(date -> new Departure(date)).collect(Collectors.toList());
    }


    private List<Date> getDatesByDaysOfWeek(List<DayOfWeek> daysOfWeek, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> daysOfWeek.contains(localDate.getDayOfWeek()))
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

    private List<Date> getDatesByNumbersDay(List<Integer> days, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> days.contains(localDate.getDayOfMonth()))
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

    private List<Date> getDatesByEven(boolean even, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> (localDate.getDayOfMonth() % 2 == 0) == even)
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

}
