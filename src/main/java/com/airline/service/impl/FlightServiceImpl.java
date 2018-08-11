package com.airline.service.impl;

import com.airline.dao.ClassTypeDao;
import com.airline.dao.FlightDao;
import com.airline.dao.PeriodDao;
import com.airline.dao.PlaneDao;
import com.airline.dto.mapper.FlightDTOMapper;
import com.airline.exceptions.AlreadyExistsException;
import com.airline.exceptions.FlightNotFoundException;
import com.airline.exceptions.PeriodNotFoundException;
import com.airline.exceptions.PlaneNotFoundException;
import com.airline.model.*;
import com.airline.model.dto.FlightDTO;
import com.airline.service.FlightService;
import com.airline.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс используется для связи между слоями контроллера и БД, маппит объект {@link Flight} из объекта
 * {@link FlightDTO} и обратно
 */
@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

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

    /**
     * Метод прнимает на вход объект {@link FlightDTO}, в котором указаны параметры для поиска (все параметры
     * не обязательные). Проверка параметров на null осуществляется непосредственно при поиске в
     * resources/mappers/FlightMapper.xml
     *
     * @param flightDTO
     * @return {@link FlightDTO}
     * @throws {@link PlaneNotFoundException} если в параметрах было указано неверное имя {@link Plane}
     */

    @Override
    public List<FlightDTO> listByParameters(FlightDTO flightDTO) {
        Plane plane = null;
        if (flightDTO.getPlaneName() != null) {
            plane = planeDao.findPlaneByName(flightDTO.getPlaneName())
                    .orElseThrow(() -> new PlaneNotFoundException("Not found plane with name " + flightDTO.getPlaneName()));
        }

        Flight flight = flightDTOMapper.convertToEntity(flightDTO,
                plane,
                null,
                null,
                null);
        return flightDao.findListByParameters(flight)
                .stream()
                .map(flight1 -> flightDTOMapper.convertToDTO(flight1))
                .collect(Collectors.toList());
    }

    /**
     * Метод принимает объект для сохранения в БД. Перед сохранением проверяет, не существует ли уже в БД рейс
     * с таким именем. Далее использует метод {@link FlightServiceImpl#buildDependencies(FlightDTO)} для сборки
     * полноценного объекта {@link Flight}, сохраняет в БД, маппит собранный объект {@link Flight} в {@link FlightDTO}
     * и возвращает контроллеру
     *
     * @param flightDTO
     * @return {@link FlightDTO}
     * @throws {@link AlreadyExistsException} при попытке сохранить в БД объект {@link Flight} с именем, которое
     *                уже есть в БД
     */

    @Override
    public FlightDTO save(FlightDTO flightDTO) {
        if (!flightDao.isPresent(flightDTO.getFlightName())) {
            throw new AlreadyExistsException("Flight with name " + flightDTO.getFlightName() + " already exists");
        }
        Flight flight = buildDependencies(flightDTO);
        flightDao.save(flight);
        return flightDTOMapper.convertToDTO(flight);
    }

    /**
     * Метод принимает объект для сохранения в БД. Перед сохранением проверяет, не существует ли уже в БД рейс
     * с таким именем. Далее использует метод {@link FlightServiceImpl#buildDependencies(FlightDTO)} для сборки
     * полноценного объекта {@link Flight}, обновляет в БД, маппит собранный объект {@link Flight} в {@link FlightDTO}
     * и возвращает контроллеру
     *
     * @param flightDTO
     * @return
     * @throws {@link AlreadyExistsException} при попытке сохранить в БД объект {@link Flight} с именем, которое
     *                уже есть в БД
     */

    @Override
    public FlightDTO update(FlightDTO flightDTO) {
        if (!flightDao.isPresent(flightDTO.getFlightName())) {
            throw new AlreadyExistsException("Flight with name " + flightDTO.getFlightName() + " already exists");
        }
        Flight flight = buildDependencies(flightDTO);
        flightDao.update(flight);
        return flightDTOMapper.convertToDTO(flight);
    }

    /**
     * Метод принимает на вход id объекта, который нужно удалить и отправляет запрос в БД
     *
     * @param id
     */

    @Override
    public void delete(Long id) {
        if (!getById(id).isApproved()) flightDao.delete(id);
    }

    /**
     * Метод принимает на вход id, отправлят запрос в БД, при удачном запросе конвертирует объект в {@link FlightDTO}
     * и возвращает контроллеру
     *
     * @param id
     * @return {@link FlightDTO}
     * @throws {@link FlightNotFoundException}
     */

    @Override
    public FlightDTO getById(Long id) {
        return flightDTOMapper.convertToDTO(flightDao.findOne(id)
                .orElseThrow(() -> new FlightNotFoundException("Not found flight with id " + id)));
    }

    /**
     * Метод принимает на вход id, по id ищет объект {@link Flight}, устанавливает {@link Flight#setApproved(boolean)}
     * в положение true, сохраняет в БД, конвертирует объект в {@link FlightDTO} и возвращает контроллеру
     *
     * @param id
     * @return {@link FlightDTO}
     * @throws {@link FlightNotFoundException}
     */

    @Override
    public FlightDTO approved(Long id) {
        Flight flight = flightDao.findOne(id)
                .orElseThrow(() -> new FlightNotFoundException("Not found flight with id " + id));
        flight.setApproved(true);
        flightDao.save(flight);
        return flightDTOMapper.convertToDTO(flight);
    }

    /**
     * Метод принимает на вход объект {@link FlightDTO}.
     * Получает объект {@link Plane} по имени из объекта {@link FlightDTO#getPlaneName()},
     * Создает список объектов {@link Price},
     * Проверяет, какой из двух параметров доступен для формирования дат вылетов (по условиям валидации должен быть
     * заполнен один из двух параметров на выбор : dates или period), формирует список объектов {@link Departure} по
     * списку переданных дат, либо по списку периодов, вызывая метод
     * {@link FlightServiceImpl#createDepartureByPeriods(List, Date, Date)}
     * Конвертирует объект {@link FlightDTO} в {@link Flight}
     *
     * @param flightDTO
     * @return {@link Flight}
     * @throws {@link PlaneNotFoundException}
     * @throws {@link PeriodNotFoundException}
     */

    private Flight buildDependencies(FlightDTO flightDTO) {

        Plane plane = planeDao.findPlaneByName(flightDTO.getPlaneName())
                .orElseThrow(() -> new PlaneNotFoundException("Not found plane with name " + flightDTO.getPlaneName()));

        List<Price> prices = new ArrayList<>();

        classTypeDao.findClassTypeByName(ClassTypeEnum.BUSINESS.name())
                .ifPresent(classType -> prices.add(new Price(classType, flightDTO.getPriceBusiness())));

        classTypeDao.findClassTypeByName(ClassTypeEnum.ECONOMY.name())
                .ifPresent(classType -> prices.add(new Price(classType, flightDTO.getPriceEconomy())));

        List<Departure> departures;
        List<Period> periods = new ArrayList<>();
        if (!flightDTO.getSchedule().getPeriods().isEmpty()) {
            periods = flightDTO.getSchedule().getPeriods()
                    .stream()
                    .map(name -> periodDao.findPeriodByValue(name)
                            .orElseThrow(() -> new PeriodNotFoundException("Not found period with value " + name)))
                    .collect(Collectors.toList());
            departures = createDepartureByPeriods(periods,
                    flightDTO.getSchedule().getFromDate(),
                    flightDTO.getSchedule().getToDate());
        } else {
            departures = new ArrayList<>();
            flightDTO.getDates().forEach(date -> departures.add(new Departure(date)));
        }

        return flightDTOMapper.convertToEntity(flightDTO, plane, prices, periods, departures);
    }

    /**
     * Метод получает на вход дату начала и оконочания полетов по текущему рейсу, считает количество дней между датами
     * Получает список значений периодов в строков выражении.
     * В зависимости от значений периодов, которые могут быть заданы одни из 4-х возможных способов, вызывает
     * соотвествующие методы:
     * - ежедневные вылеты (берет все даты в полученном промежутке)
     * - четные или нечетные дни {@link FlightServiceImpl#getDatesByEven(boolean, List)}
     * - дни месяца (1, 5, 7) {@link FlightServiceImpl#getDatesByNumbersDay(List, List)}
     * - дни недели (Sun, Wed, Fri) {@link FlightServiceImpl#getDatesByDaysOfWeek(List, List)}
     *
     * @param periods   список периодов для формирования дат вылетов
     * @param dateStart дата начала полетов по текущему рейсу
     * @param dateEnd   дата окончания полетов по текущему рейсу
     * @return {@link List<Departure>}
     */

    public List<Departure> createDepartureByPeriods(List<Period> periods, Date dateStart, Date dateEnd) {
        LocalDate localDateStart = dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> localDates = Utils.getDatesBetween(localDateStart, localDateEnd);
        List<String> values = periods.stream()
                .map(Period::getValue)
                .collect(Collectors.toList());
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

        return dates.stream()
                .map(Departure::new)
                .collect(Collectors.toList());
    }

    /**
     * Метод получает список необходимых дней недели, для которых нужно сформировать вылеты и список дат,
     * среди которых нужно выбрать нужные. Фильтурет по необходимым дням недели, маппит {@link LocalDate}
     * в {@link Date} и возвращает список
     *
     * @param daysOfWeek дни недели, которые нужно выбрать в списке дат
     * @param localDates список дат, среди которых нужно выбрать нужные дни недели
     * @return {@link List<Date>}
     */

    private List<Date> getDatesByDaysOfWeek(List<DayOfWeek> daysOfWeek, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> daysOfWeek.contains(localDate.getDayOfWeek()))
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

    /**
     * Метод получает список необходимых дней месяца, для которых нужно сформировать вылеты и список дат,
     * среди которых нужно выбрать нужные. Фильтурет по необходимым дням месяца, маппит {@link LocalDate}
     * в {@link Date} и возвращает список
     *
     * @param days       дни месяца, которые нужно выбрать в списке дат
     * @param localDates список дат, среди которых нужно выбрать нужные дни месяца
     * @return {@link List<Date>}
     */

    private List<Date> getDatesByNumbersDay(List<Integer> days, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> days.contains(localDate.getDayOfMonth()))
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

    /**
     * Метод получает boolean значение и список дат, среди которых нужно выбрать четные (true) или нечетные (false),
     * маппит {@link LocalDate} в {@link Date} и возвращает список
     *
     * @param even       указывает на то, нужны ли нам четные (true) или нечетные (false) дни месяца
     * @param localDates список дат, среди которых нужно выбрать нужные дни месяца
     * @return {@link List<Date>}
     */

    private List<Date> getDatesByEven(boolean even, List<LocalDate> localDates) {
        return localDates
                .stream()
                .filter(localDate -> (localDate.getDayOfMonth() % 2 == 0) == even)
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

}

