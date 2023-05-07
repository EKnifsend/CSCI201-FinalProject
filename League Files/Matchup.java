package knifend_CSCI201_GroupProject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Matchup {
	Team homeTeam;
	Team awayTeam;
	
	Team projectedWinner;
	Team winner;
	
	double homeLine;	// Value between 0.00 and 1.00 (although it will never reach those values in practice) representing 
						// payout for bets on the home team. Payout for other team is (1 - line). The way this 
						// value works is it is the amount won for every dollar bet. For example, if value
						// is 0.66, then the home team has a projected 33% to win (projected loser). If I bet $2.00 on the 
						// home team to win. Then I would get back my $2.00 PLUS $1.32
	double awayLine;	// See above.
	
	/* 
	 * Constructor.
	 * 
	 * @param homeTeam Team representing the "home" team.
	 * @param awayTeam Team representing the "away" team.
	 * @see setLines()
	 */
	public Matchup(Team homeTeam, Team awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		
		setLines();
	}
	
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	public Team getAwayTeam() {
		return awayTeam;
	}
	
	public Team getProjectedWinner() {
		return projectedWinner;
	}
	
	public Team getWinner() {
		return winner;
	}
	
	public double getHomeLine() {
		return homeLine;
	}
	
	public double getAwayLine() {
		return awayLine;
	}
	
	/*
	 * Simulate Game method. It calculates the results of the game based on very simple formula: chance of 
	 * team winning is = team strength / (combined strength of both team). So, for example, if the home 
	 * team has strength of 4 and the away team has a strength of 5, then the chance of the home team 
	 * winning is 4 / 9. This is done by generating a random in between 1 and 9. If the value is between 
	 * 1-4, then the home team wins. Otherwise, the away team wins.
	 * 
	 * @param Unused.
	 * @returns Nothing.
	 * @see Team.getTeamStrength()
	 */
	public void simulateGame() {
		int combinedStrength = homeTeam.getTeamStrength() + awayTeam.getTeamStrength();
		
		Random rand = new Random();
		int gameSim = rand.nextInt(combinedStrength) + 1;
		
		if (gameSim <= homeTeam.getTeamStrength()) {
			winner = homeTeam;
		}
		else {
			winner = awayTeam;
		}
	}
	
	/*
	 * Private Helper Method. It sets the projected winner and payout for the winner based on the chances of
	 * winning as specified in simulateGame(). 
	 * 
	 * @param Unused.
	 * @return Nothing.
	 * @see setEstimatedTeamStrength(), round()
	 */
	private void setLines() {
		int projectedHomeTeamStrength = setEstimatedTeamStrength(homeTeam);
		int projectedAwayTeamStrength = setEstimatedTeamStrength(awayTeam);
		
		if (projectedHomeTeamStrength == projectedAwayTeamStrength) {
			projectedWinner = homeTeam;
			homeLine = 0.49;
			awayLine = 0.51;
		}
		else {
			homeLine = 1.00 - ((double) projectedHomeTeamStrength) / (projectedHomeTeamStrength + projectedAwayTeamStrength);
			awayLine = 1.00 - ((double) projectedAwayTeamStrength) / (projectedHomeTeamStrength + projectedAwayTeamStrength);
			
			homeLine = round(homeLine, 2);
			awayLine = round(awayLine, 2);
			
			if (projectedHomeTeamStrength > projectedAwayTeamStrength) {
				projectedWinner = homeTeam;
			}
			else {
				projectedWinner = awayTeam;
			}
		}
	}
	
	/*
	 * Private Helper Method. Creates "estimated" team strength by distorting actual team strength values. This
	 * is to simulate the inaccuracy of predictions. It does this by randomly applying a swing of up to 2 points. 
	 * For example, if the team strength value is 9, then the "estimated" value will be some value between 7 - 11. 
	 * This means that the estimated strength value will be some value between -1 to 12. To Adjust, a base boost 
	 * of 2 will be added to the value, so the range will be 1 to 14.
	 * 
	 * @param team Team whose strength value will be "estimated"
	 * @return int "Estimated" team strength value
	 */
	private int setEstimatedTeamStrength (Team team) {
		Random rand = new Random();
		int swing = rand.nextInt(5);
		
		return team.getTeamStrength() + swing;
	}
	
	/*
	 * Private Helper Method. Rounds decimal to nth decimal place.
	 * 
	 * @param value Double representing the value to be rounded.
	 * @param places This of decimal places that the value will be rounded to.
	 * @returns double Double that has been rounded.
	 */
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
