package com.airline.service;

import com.airline.dao.ClassTypeDao;
import com.airline.dao.FlightDao;
import com.airline.dao.PeriodDao;
import com.airline.dao.PlaneDao;
import com.airline.dtomapper.FlightDTOMapper;
import com.airline.model.*;
import com.airline.model.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;
    private FlightDTOMapper flightDTOMapper;
    private PlaneDao planeDao;
    private PeriodDao periodDao;
    private ClassTypeDao classTypeDao;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao, FlightDTOMapper flightDTOMapper, PlaneDao planeDao, PeriodDao periodDao, ClassTypeDao classTypeDao) {
        this.flightDao = flightDao;
        this.flightDTOMapper = flightDTOMapper;
        this.planeDao = planeDao;
        this.periodDao = periodDao;
        this.classTypeDao = classTypeDao;
    }

    @Override
    public List<FlightDTO> listByParameters(FlightDTO flightDTO) {

        Flight flight = flightDTOMapper.convertToEntity(flightDTO, planeDao.findPlaneByName(flightDTO.getPlaneName()), null, null, null);
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
        prices.add(new Price(classTypeDao.findClassTypeByName("BUSINESS"), flightDTO.getPriceBusiness()));
        prices.add(new Price(classTypeDao.findClassTypeByName("ECONOMY"), flightDTO.getPriceEconomy()));

        List<Period> periods = new ArrayList<>();
        Arrays.asList(flightDTO.getSchedule().getPeriod()
                .replace(" ", "")
                .split(","))
                .forEach(s -> periods.add(periodDao.selectPeriodByValue(s)));

        List<Departure> departures;
        if (!flightDTO.getDates().isEmpty()) {
            departures = new ArrayList<>();
            flightDTO.getDates().forEach(date -> departures.add(new Departure(date)));
        } else {
            departures = createDepartureByPeriods(periods, flightDTO.getSchedule().getFromDate(), flightDTO.getSchedule().getToDate());
        }

        return flightDTOMapper.convertToEntity(flightDTO, plane, prices, periods, departures);
    }

    private List<Departure> createDepartureByPeriods(List<Period> periods, Date dateStart, Date dateEnd) {
        LocalDate localDateStart = dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> localDates = getDatesBetween(localDateStart, localDateEnd);
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
        } else if (isInteger(value)) {
            dates = getDatesByNumbersDay(values.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()), localDates);
        } else if (isDayOfWeek(value)) {
            dates = getDatesByDaysOfWeek(getDaysOfWeekByString(values), localDates);
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

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }


    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isDayOfWeek(String s) {
        return (s.equals("Sun") ||
                s.equals("Mon") ||
                s.equals("Tue") ||
                s.equals("Wed") ||
                s.equals("Thu") ||
                s.equals("Fri") ||
                s.equals("Sat"));
    }

    public List<DayOfWeek> getDaysOfWeekByString(List<String> strings) {
        Map<String, DayOfWeek> mp = new HashMap<String, DayOfWeek>();

        mp.put("Sun", DayOfWeek.SUNDAY);
        mp.put("Mon", DayOfWeek.MONDAY);
        mp.put("Tue", DayOfWeek.TUESDAY);
        mp.put("Wed", DayOfWeek.WEDNESDAY);
        mp.put("Thu", DayOfWeek.THURSDAY);
        mp.put("Fri", DayOfWeek.FRIDAY);
        mp.put("Sat", DayOfWeek.SATURDAY);

        return strings.stream().map(s -> mp.get(s)).collect(Collectors.toList());
    }

}
