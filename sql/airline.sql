DROP DATABASE IF EXISTS airline;
CREATE DATABASE `airline`;

USE `airline`;

create table if not exists admins
(
	id bigserial not null
		constraint admins_pkey
			primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	patronymic varchar(255),
	position varchar(255) not null,
	login varchar(255) not null,
	password varchar(255) not null
)
;

create table if not exists clients
(
	id bigserial not null
		constraint clients_pkey
			primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	patronymic varchar(255),
	email varchar(255) not null,
	phone varchar(255) not null,
	login varchar(255) not null,
	password varchar(255) not null
)
;

create table if not exists countries
(
	iso3166 varchar(2) not null
		constraint countries_pkey
			primary key,
	name varchar(255)
)
;

create table if not exists classes
(
	id bigserial not null
		constraint classes_pkey
			primary key,
	name varchar(15)
)
;

create table if not exists planes
(
	id bigserial not null
		constraint planes_pkey
			primary key,
	name varchar(255),
	bussiness_row integer,
	economy_row integer,
	places_in_business_row integer,
	places_in_economy_row integer
)
;

create table if not exists flights
(
	id bigserial not null
		constraint flights_pkey
			primary key,
	flight_name varchar(15),
	plane_id bigint
		constraint flights_planes_id_fk
			references planes
				on update cascade on delete cascade,
	from_town varchar(255),
	to_town varchar(255),
	start time,
	duration time,
	from_date timestamp,
	to_date timestamp,
	approved boolean default false
)
;

create table if not exists departures
(
	id bigserial not null
		constraint departures_pkey
			primary key,
	date date,
	flight_id bigint
		constraint departures_flights_id_fk
			references flights
)
;

create table if not exists orders
(
	id bigserial not null
		constraint orders_pkey
			primary key,
	departure_id bigint
		constraint orders_departures_id_fk
			references departures,
	client_id bigint
		constraint orders_clients_id_fk
			references clients
)
;

create table if not exists tickets
(
	id bigserial not null
		constraint tickets_pkey
			primary key,
	order_id bigint
		constraint tickets_orders_id_fk
			references orders
				on update cascade,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	nationality varchar(2)
		constraint tickets_countries_iso3166_fk
			references countries
				on update cascade,
	passport varchar(12) not null,
	class_id bigint
		constraint tickets_classes_id_fk
			references classes
				on update cascade on delete cascade,
	place varchar(5)
)
;

create unique index if not exists flights_flight_name_uindex
	on flights (flight_name)
;

create table if not exists price
(
	flight_id bigint
		constraint price_flights_id_fk
			references flights
				on update cascade on delete cascade,
	class_id bigint
		constraint price_classes_id_fk
			references classes,
	price decimal (10,2)
)
;

create unique index if not exists planes_name_uindex
	on planes (name)
;

create table if not exists databasechangeloglock
(
	id integer not null
		constraint pk_databasechangeloglock
			primary key,
	locked boolean not null,
	lockgranted timestamp,
	lockedby varchar(255)
)
;

create table if not exists period
(
	id bigserial not null
		constraint period_pkey
			primary key,
	value varchar(5)
)
;

create table if not exists period_flight
(
	period_id bigint
		constraint period_flight_period_id_fk
			references period
				on update cascade on delete cascade,
	flight_id bigint
		constraint period_flight_flights_id_fk
			references flights,
	constraint period_flight_period_id_flight_id_pk
		unique (period_id, flight_id)
)
;

