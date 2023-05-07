USE SalBids;
CREATE TABLE Matchups (
	matchupID int(11) primary key not null,
    homeTeamID int(11) not null,
    awayTeamID int(11) not null,
    winnerTeamID int(11) not null,
    awayLine float(11) not null,
    homeLine float(11) not null
);