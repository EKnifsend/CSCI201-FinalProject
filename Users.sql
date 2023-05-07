CREATE DATABASE SalBids;
USE SalBids;
CREATE TABLE Users (
	userID int(11) primary key not null auto_increment,
    username varchar(50) not null,
    password varchar(50) not null,
    fname varchar(50) not null,
    lname varchar(50) not null,
    balance int(11) not null
);