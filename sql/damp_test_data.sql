
INSERT INTO classes (id, name) VALUES (1, 'ECONOMY');
INSERT INTO classes (id, name) VALUES (2, 'BUSINESS');

INSERT INTO clients (id, first_name, last_name, patronymic, email, phone, login, password) VALUES (1, 'Иван', 'Иванов', 'Иванович', 'ivanov@email.ru', '+79999999999', 'ivanov', 'ivanov');
INSERT INTO clients (id, first_name, last_name, patronymic, email, phone, login, password) VALUES (2, 'Петр', 'Петров', null, 'petrov@email.ru', '+79999999991', 'petrov', 'petrov');
INSERT INTO clients (id, first_name, last_name, patronymic, email, phone, login, password) VALUES (5, 'Анатолий', 'Медведев', null, 'medvedev@email.ru', '+79999999994', 'medvedev', 'medvedev');
INSERT INTO clients (id, first_name, last_name, patronymic, email, phone, login, password) VALUES (3, 'Егор', 'Сидоров', 'Сергеевич', 'sidorod@email.ru', '+79999999992', 'sidorod', 'sidorod');
INSERT INTO clients (id, first_name, last_name, patronymic, email, phone, login, password) VALUES (4, 'Петр', 'Зайцев', 'Сергеевич', 'zaicev@email.ru', '+79999999993', 'zaicev', 'zaicev');

INSERT INTO countries (iso3166, name) VALUES ('RU', 'Россия');
INSERT INTO countries (iso3166, name) VALUES ('CH', 'China');
INSERT INTO countries (iso3166, name) VALUES ('IN', 'India');

INSERT INTO planes (plane_id, name, bussiness_row, economy_row, places_in_business_row, places_in_economy_row) VALUES (1, 'ТУ-134', 2, 16, 4, 4);
INSERT INTO planes (plane_id, name, bussiness_row, economy_row, places_in_business_row, places_in_economy_row) VALUES (2, 'Airbus A310', 6, 14, 7, 8);
INSERT INTO planes (plane_id, name, bussiness_row, economy_row, places_in_business_row, places_in_economy_row) VALUES (3, 'Airbus A320', 6, 20, 6, 6);

INSERT INTO flights (id, flight_name, flight_plane_id, from_town, to_town, start_time, duration, from_date, to_date, approved) VALUES (1, '435', 1, 'Омск', 'Москва', '07:30:00', '03:00:00', '2018-01-01 00:00:00.621000', '2018-12-31 23:59:59.574000', false);
INSERT INTO flights (id, flight_name, flight_plane_id, from_town, to_town, start_time, duration, from_date, to_date, approved) VALUES (2, '112', 1, 'Омск', 'Новосибирск', '10:15:00', '01:30:00', '2018-01-01 00:00:00.621000', '2018-12-31 23:59:59.574000', false);
INSERT INTO flights (id, flight_name, flight_plane_id, from_town, to_town, start_time, duration, from_date, to_date, approved) VALUES (3, '53', 2, 'Омск', 'Дели', '19:30:00', '05:15:00', '2018-01-01 00:00:00.621000', '2018-12-31 23:59:59.574000', false);
INSERT INTO flights (id, flight_name, flight_plane_id, from_town, to_town, start_time, duration, from_date, to_date, approved) VALUES (4, '958', 3, 'Омск', 'Владивосток', '23:15:00', '07:20:00', '2018-01-01 00:00:00.621000', '2018-12-31 23:59:59.574000', false);
INSERT INTO flights (id, flight_name, flight_plane_id, from_town, to_town, start_time, duration, from_date, to_date, approved) VALUES (5, '158', 3, 'Омск', 'Париж', '12:00:00', '04:30:00', '2018-01-01 00:00:00.621000', '2018-12-31 23:59:59.574000', false);

INSERT INTO period (id, value) VALUES (1, '1');
INSERT INTO period (id, value) VALUES (2, '2');
INSERT INTO period (id, value) VALUES (3, '3');
INSERT INTO period (id, value) VALUES (4, '4');
INSERT INTO period (id, value) VALUES (5, '5');
INSERT INTO period (id, value) VALUES (6, '6');
INSERT INTO period (id, value) VALUES (7, '7');
INSERT INTO period (id, value) VALUES (8, '8');
INSERT INTO period (id, value) VALUES (9, '9');
INSERT INTO period (id, value) VALUES (10, '10');
INSERT INTO period (id, value) VALUES (11, '11');
INSERT INTO period (id, value) VALUES (12, '12');
INSERT INTO period (id, value) VALUES (13, '13');
INSERT INTO period (id, value) VALUES (14, '14');
INSERT INTO period (id, value) VALUES (15, '15');
INSERT INTO period (id, value) VALUES (16, '16');
INSERT INTO period (id, value) VALUES (17, '17');
INSERT INTO period (id, value) VALUES (18, '18');
INSERT INTO period (id, value) VALUES (19, '19');
INSERT INTO period (id, value) VALUES (20, '20');
INSERT INTO period (id, value) VALUES (21, '21');
INSERT INTO period (id, value) VALUES (22, '22');
INSERT INTO period (id, value) VALUES (23, '23');
INSERT INTO period (id, value) VALUES (24, '24');
INSERT INTO period (id, value) VALUES (25, '25');
INSERT INTO period (id, value) VALUES (26, '26');
INSERT INTO period (id, value) VALUES (27, '27');
INSERT INTO period (id, value) VALUES (28, '28');
INSERT INTO period (id, value) VALUES (29, '29');
INSERT INTO period (id, value) VALUES (30, '30');
INSERT INTO period (id, value) VALUES (31, '31');
INSERT INTO period (id, value) VALUES (32, 'daily');
INSERT INTO period (id, value) VALUES (33, 'odd');
INSERT INTO period (id, value) VALUES (34, 'even');
INSERT INTO period (id, value) VALUES (35, 'Sun');
INSERT INTO period (id, value) VALUES (36, 'Mon');
INSERT INTO period (id, value) VALUES (37, 'Tue');
INSERT INTO period (id, value) VALUES (38, 'Wed');
INSERT INTO period (id, value) VALUES (39, 'Thu');
INSERT INTO period (id, value) VALUES (40, 'Fri');
INSERT INTO period (id, value) VALUES (41, 'Sat');

INSERT INTO period_flight (period_id, flight_id) VALUES (32, 1);
INSERT INTO period_flight (period_id, flight_id) VALUES (36, 2);
INSERT INTO period_flight (period_id, flight_id) VALUES (38, 2);
INSERT INTO period_flight (period_id, flight_id) VALUES (40, 2);
INSERT INTO period_flight (period_id, flight_id) VALUES (2, 3);
INSERT INTO period_flight (period_id, flight_id) VALUES (12, 3);
INSERT INTO period_flight (period_id, flight_id) VALUES (22, 3);
INSERT INTO period_flight (period_id, flight_id) VALUES (40, 4);
INSERT INTO period_flight (period_id, flight_id) VALUES (37, 4);
INSERT INTO period_flight (period_id, flight_id) VALUES (40, 5);
INSERT INTO period_flight (period_id, flight_id) VALUES (15, 5);

INSERT INTO prices (flight_id, class_id, price) VALUES (1, 1, 10000);
INSERT INTO prices (flight_id, class_id, price) VALUES (1, 2, 15000);
INSERT INTO prices (flight_id, class_id, price) VALUES (2, 1, 10000);
INSERT INTO prices (flight_id, class_id, price) VALUES (2, 2, 15000);
INSERT INTO prices (flight_id, class_id, price) VALUES (3, 1, 10000);
INSERT INTO prices (flight_id, class_id, price) VALUES (3, 2, 15000);
INSERT INTO prices (flight_id, class_id, price) VALUES (4, 1, 10000);
INSERT INTO prices (flight_id, class_id, price) VALUES (4, 2, 15000);
INSERT INTO prices (flight_id, class_id, price) VALUES (5, 1, 10000);
INSERT INTO prices (flight_id, class_id, price) VALUES (5, 2, 15000);

INSERT INTO departures (id, date, flight_id) VALUES (1, '2018-07-12', 1);
INSERT INTO departures (id, date, flight_id) VALUES (2, '2018-07-13', 1);
INSERT INTO departures (id, date, flight_id) VALUES (3, '2018-07-13', 1);
INSERT INTO departures (id, date, flight_id) VALUES (4, '2018-07-09', 2);
INSERT INTO departures (id, date, flight_id) VALUES (5, '2018-07-11', 2);
INSERT INTO departures (id, date, flight_id) VALUES (6, '2018-07-13', 2);
INSERT INTO departures (id, date, flight_id) VALUES (7, '2018-07-02', 3);
INSERT INTO departures (id, date, flight_id) VALUES (8, '2018-07-12', 3);
INSERT INTO departures (id, date, flight_id) VALUES (9, '2018-07-22', 3);
INSERT INTO departures (id, date, flight_id) VALUES (10, '2018-07-13', 4);
INSERT INTO departures (id, date, flight_id) VALUES (11, '2018-07-19', 4);
INSERT INTO departures (id, date, flight_id) VALUES (12, '2018-07-17', 4);
INSERT INTO departures (id, date, flight_id) VALUES (13, '2018-07-13', 5);
INSERT INTO departures (id, date, flight_id) VALUES (14, '2018-07-13', 5);
INSERT INTO departures (id, date, flight_id) VALUES (15, '2018-07-13', 5);

INSERT INTO orders (id, departure_id, client_id) VALUES (1, 1, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (2, 1, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (3, 2, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (4, 2, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (5, 3, 3);
INSERT INTO orders (id, departure_id, client_id) VALUES (6, 3, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (7, 4, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (8, 4, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (9, 5, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (10, 5, 3);
INSERT INTO orders (id, departure_id, client_id) VALUES (11, 6, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (12, 6, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (13, 7, 3);
INSERT INTO orders (id, departure_id, client_id) VALUES (14, 7, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (15, 8, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (16, 8, 1);
INSERT INTO orders (id, departure_id, client_id) VALUES (17, 9, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (18, 9, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (19, 10, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (20, 10, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (21, 11, 3);
INSERT INTO orders (id, departure_id, client_id) VALUES (22, 11, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (23, 12, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (24, 12, 5);
INSERT INTO orders (id, departure_id, client_id) VALUES (25, 13, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (26, 13, 3);
INSERT INTO orders (id, departure_id, client_id) VALUES (27, 14, 4);
INSERT INTO orders (id, departure_id, client_id) VALUES (28, 14, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (29, 15, 2);
INSERT INTO orders (id, departure_id, client_id) VALUES (30, 15, 3);

INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (32, 14, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (33, 14, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (7, 2, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (6, 1, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (9, 3, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (8, 2, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (11, 4, 'Иван', 'Иванов', 'IN', '5555 55555', 2, 1A);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (10, 3, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (13, 4, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (12, 4, 'Иван', 'Иванов', 'RU', '5555 55555', 2, 1B);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (15, 5, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (14, 5, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (17, 6, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (16, 5, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (19, 8, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (18, 7, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (21, 9, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (20, 9, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (23, 10, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (22, 10, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (25, 11, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (24, 11, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (27, 12, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (26, 11, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (29, 13, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (28, 12, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (31, 13, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (30, 13, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (34, 15, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);


INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (35, 16, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (36, 16, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (37, 17, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (38, 18, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (39, 19, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (40, 17, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (41, 20, 'Иван', 'Иванов', 'IN', '5555 55555', 2, '1A');
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (42, 19, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (43, 20, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (44, 20, 'Иван', 'Иванов', 'RU', '5555 55555', 2, '1B');
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (45, 21, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (46, 21, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (47, 6, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (48, 21, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (49, 22, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (50, 23, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (51, 24, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (52, 24, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (53, 25, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (54, 25, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (55, 26, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (56, 26, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (57, 27, 'Иван', 'Иванов', 'RU', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (58, 26, 'Иван', 'Иванов', 'IN', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (59, 28, 'Иван', 'Иванов', 'IN', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (60, 27, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (61, 29, 'Иван', 'Иванов', 'CH', '5555 55555', 2, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (62, 29, 'Иван', 'Иванов', 'RU', '5555 55555', 1, null);
INSERT INTO tickets (id, order_id, first_name, last_name, nationality, passport, class_id, place) VALUES (63, 30, 'Иван', 'Иванов', 'CH', '5555 55555', 1, null);


INSERT INTO admins (id, first_name, last_name, patronymic, position, login, password) VALUES (1, 'Иван', 'Иванов', null, 'инженер', 'ivanovadmin', '123456');
INSERT INTO admins (id, first_name, last_name, patronymic, position, login, password) VALUES (2, 'Петр', 'Петров', null, 'инженер', 'petrovadmin', '123456');