DROP DATABASE if exists SalBids;
CREATE DATABASE SalBids ;
USE SalBids;

CREATE TABLE Teams(
	TeamID int(5) primary key not null auto_increment,
    Name varchar(50) not null,
    Strength int(5) not null
);

INSERT INTO Teams(SID, Name, Strength) VALUES (1, 'Leavey Lions', 3);
INSERT INTO Teams(Name, Strength) VALUES ('Village Victors', 4);
INSERT INTO Teams(Name, Strength) VALUES ('Marshall Maulers', 7);
INSERT INTO Teams(Name, Strength) VALUES ('Dornsife Dragons', 1);
INSERT INTO Teams(Name, Strength) VALUES ('Viterbi Vanquishers', 9);
INSERT INTO Teams(Name, Strength) VALUES ('Figueroa Fighters', 5);
INSERT INTO Teams(Name, Strength) VALUES ('Annenberg Animals', 6);
INSERT INTO Teams(Name, Strength) VALUES ('Kaufman Killers', 2);
INSERT INTO Teams(Name, Strength) VALUES ('McClintock Machines', 10);
INSERT INTO Teams(Name, Strength) VALUES ('Salvatori Sabers', 8);

