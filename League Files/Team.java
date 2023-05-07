package knifend_CSCI201_GroupProject;

class Team implements Comparable<Team> {
	private int teamID;			// Unique value identifying team
	private String teamName;
	private int teamStrength;	// Value representing strength of team between 1 and 10
	
	/* 
	 * Constructor.
	 */
	public Team (int teamID, String teamName, int teamStrength) {
		this.teamID = teamID;
		this.teamName = teamName;
		this.teamStrength = teamStrength;
	}

	public int getTeamID () {
		return teamID;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public int getTeamStrength() {
		return teamStrength;
	}

	/* 
	 * Compares the IDs of two teams. It returns ID of this team object - ID of team object
	 * being compared to.
	 */
	@Override
	public int compareTo(Team team) {
		// TODO Auto-generated method stub
		return teamID - team.getTeamID();
	}
}

