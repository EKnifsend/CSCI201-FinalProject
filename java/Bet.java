public class Bet{
	public int teamID;
	public int amount;
	public int matchupID;
	public String teamName;
	public double line;
	
	Bet(int teamID, int amount, int matchupID, String teamName, double line){
		this.teamID = teamID;
		this.amount = amount;
		this.matchupID = matchupID;
		this.teamName = teamName;
		this.line = line;
	}

}