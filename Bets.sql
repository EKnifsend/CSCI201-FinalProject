USE SalBids;
CREATE TABLE Bets (
	betID int(11) primary key not null auto_increment,
    teamID int(11) not null,
    matchupID int(11) not null,
    userID int(11) not null,
    amount int(11) not null
);