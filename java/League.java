import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class League {
	
	List <Team> teams;
	static List <Matchup> schedule;
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	Statement s = null;
	
	boolean readyToAdvance;		// Indicates whether you are ready to advance to new set of matchups (by
								// calling startNewWeek())
	
	/* 
	 * Constructor. Creates connection to database, gets teams from database. Creates initial schedule.
	 * 
	 * @param Unused.
	 * @see createSchedule()
	 */
	public League() {
		// Access database, get teams
		
		List<Team> teamsList = new ArrayList<Team> ();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/SalBids?user=root&password=Asian4life");		// Connection should be changed to database
			st = conn.createStatement();
			
			rs = ((java.sql.Statement) st).executeQuery("SELECT TeamID, Name, Strength\n"
					+ "FROM Teams\n");		// Assuming table is called League with columns ID, TeamName, TeamStrength
			
			while(rs.next()) {
				int teamID = rs.getInt("TeamID");
				String teamName = rs.getString("Name");
				int teamStrength = rs.getInt("Strength");
				
				Team teamToAdd = new Team(teamID, teamName, teamStrength);
				
				teamsList.add(teamToAdd);
			}
			
			System.out.println("team size: " + teamsList.size());
			
		} catch (SQLException sqle) {
			System.out.println (sqle.getMessage());
			//throw sqle;
		}
		
		teams = Collections.synchronizedList(teamsList);
		
		//List<Matchup> unsynchSchedule = new ArrayList<Matchup> ();
		schedule = new ArrayList<Matchup>();
		
		createSchedule();
	}
	
	/* 
	 * Accessor. Returns schedule list.
	 * 
	 * @param Unused.
	 * @return List<Team> List of teams in league
	 */
	public List<Team> getTeams() {
		return teams;
	}
	
	/* 
	 * Accessor. Returns schedule list.
	 * 
	 * @param Unused.
	 * @return List<Matchup> List of matchups in schedule
	 */
	public static List<Matchup> getSchedule() {
		return schedule;
	}
	
	/* 
	 * Simulates all matchups in schedule and returns schedule of completed matchups.
	 * 
	 * @param Unused.
	 * @return List<Matchup> Schedule of matchups after matchups have been completed
	 * @see Matchup.simulateGame(), updateRecord()
	 */
	public List<Matchup> simulateWeek() {
		for (Matchup m : schedule) {
			m.simulateGame();
		}
		
		updateRecord();
		
		readyToAdvance = true;
		
		return schedule;
	}
	
	/* 
	 * If ready, it will move on by creating a new schedule.
	 * 
	 * @param Unused.
	 * @return boolean Represents if it successfully advanced to new week.
	 * @see createSchedule()
	 */
	public boolean startNewWeek() {
		if (readyToAdvance) {
			schedule.clear();
			createSchedule();
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/* 
	 * Private Helper Method. Creates new schedule of randomly assigned matchups. If odd number of teams, 
	 * then one team will not be included.
	 * 
	 * @param Unused.
	 * @return Nothing.
	 */
	private void createSchedule() {
		List<Team> availableTeams = new ArrayList<Team> ();
		
		for (Team t : teams) {
			availableTeams.add(t);
		}
		
		while (availableTeams.size() > 1) {
			Random rand = new Random();
			int teamIndex = rand.nextInt(availableTeams.size());
			Team homeTeam = availableTeams.get(teamIndex);
			availableTeams.remove(teamIndex);
			
			teamIndex = rand.nextInt(availableTeams.size());
			Team awayTeam = availableTeams.get(teamIndex);
			availableTeams.remove(teamIndex);
			System.out.println("homeTeam: " + homeTeam.getTeamName());
			System.out.println("awayTeam: " + awayTeam.getTeamName());
			Matchup matchup = new Matchup(homeTeam, awayTeam);
			
			schedule.add(matchup);
		}
		
		readyToAdvance = false;
	}
	
	/* 
	 * Private Helper Method. Accesses database and updates record.
	 */
	private void updateRecord() {
		
	}
	
	/* 
	 * Closes connection to database.
	 * 
	 * @param Unused.
	 * @return Nothing.
	 */
	private void closeConnection() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
}