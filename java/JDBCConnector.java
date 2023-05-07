import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnector {
	
	public static int registerUser(String username, String password, String fname, String lname){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			// check if username already exists
			ps = conn.prepareStatement("SELECT username FROM Users");
	        r1= ps.executeQuery();
	        while(r1.next()) {
	        	String DbUserName = r1.getString("username");
	        	System.out.println("DbUsername: " + DbUserName);
	        	if (username.equals(DbUserName)) {
	        		if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
					return -1;
	        	}
	        }
	        
	        // else insert into Users table
	        st = conn.createStatement();
			st.execute("INSERT INTO Users (username, password, fname, lname, balance) VALUES (" + "'" + username + "'" + ", " + "'" + password + "'" + ", " + "'" + fname + "'" + ", '" + lname + "'" + ", 1000)");
	        ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=" + "'" + username + "'" + "");
	        r1 = ps.executeQuery();
	        int userID = 0;
	        if(r1.next()) {
	        	userID = r1.getInt("userID");
	        	System.out.println(userID);
	        	return userID;
	        }
	        return -2;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in registerUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return 1;
	}
	
	public static int loginUser(String username, String password){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			// check if username exists
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Users WHERE username='" + username + "'");
	        r1= ps.executeQuery();
	        while(r1.next()) {
	        	int count = r1.getInt(1);
	        	if(count == 0) {
	        		return -1;
	        	}
	        	// if username does exists
	        	// check if password match
	        	else {
	        	
	        		ps = conn.prepareStatement("SELECT password FROM Users WHERE username='" + username + "'");
	    	        r1= ps.executeQuery();
	    	        String DBPassword = "";
	    	        if(r1.next()) {
	    	        	DBPassword = r1.getString("password");
	    	        }
	    	        System.out.println("DB password: " + DBPassword);
	    	        if(DBPassword.equals(password)) {
	    	        	// get userID
	    	        	ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=" + "'" + username + "'" + "");
	    		        r1 = ps.executeQuery();
	    		        int userID = 0;
	    		        if(r1.next()) {
	    		        	userID = r1.getInt("userID");
	    		        	System.out.println(userID);
	    		        	return userID;
	    		        }
	    	        }else {
	    	        	return -2;
	    	        }
	        	}
	        			
	        }
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in loginUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return 1;
	}
	
	public static void deleteLeague(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
	        st = conn.createStatement();
			st.execute("DELETE FROM Matchups");
		} catch(SQLException sqle) {
			System.out.println("SQLException in registerUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static void createLeague(int homeTeamID, int awayTeamID, int winnerTeamID, double homeLine, double awayLine, int matchupID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
	        st = conn.createStatement();
			st.execute("INSERT INTO Matchups (matchupID, homeTeamID, awayTeamID, winnerTeamID, homeLine, awayLine) VALUES ("  + matchupID  + ", " + homeTeamID  + ", " + awayTeamID + ", " + winnerTeamID + ", " + homeLine  + ", " + awayLine  + ")");
		} catch(SQLException sqle) {
			System.out.println("SQLException in registerUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static boolean leagueHasStarted(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			// check if username exists
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Matchups");
	        r1= ps.executeQuery();
	        while(r1.next()) {
	        	int count = r1.getInt(1);
	        	if(count == 0) {
	        		return false;
	        	}
	        	else {
	        	
	        		return true;
	        	}
	        			
	        }
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in loginUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}
	
	public static List<Matchup> getLeagueData(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		ResultSet r2 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			// check if username exists
			ps = conn.prepareStatement("SELECT * FROM Matchups");
	        r1= ps.executeQuery();
	        
	        List<Matchup> matchups = new ArrayList<>();
	        
	        int count = 0;
	        while(r1.next()) {
	        	System.out.println("hit");
	        	System.out.println(++count);
	        	
	        	// get homeTeamID and awayTeamID
	        	int homeTeamID = r1.getInt("homeTeamID");
	        	int awayTeamID = r1.getInt("awayTeamID");
	        	
	        	// get moneyline
	        	double homeLine = r1.getDouble("homeLine");
	        	double awayLine = r1.getDouble("awayLine");
	        	
	        	// call to Teams table to get data
	        	ps = conn.prepareStatement("SELECT * FROM Teams WHERE TeamID=" + "'" + homeTeamID + "'" + "");
	        	r2 = ps.executeQuery();
	        	// get teamName and strength value
	        	String homeTeamName = "";
	        	int homeStrengthVal = 0;
	        	while(r2.next()) {
	        		homeTeamName = r2.getString("Name");
	        		homeStrengthVal = r2.getInt("Strength");
	        	}
	        	
	        	ps = conn.prepareStatement("SELECT * FROM Teams WHERE TeamID=" + "'" + awayTeamID + "'" + "");
	        	r2= ps.executeQuery();
	        	// get teamName and strength value
	        	String awayTeamName = "";
	        	int awayStrengthVal = 0;
	        	while(r2.next()) {
	        		awayTeamName = r2.getString("Name");
	        		awayStrengthVal = r2.getInt("Strength");
	        	}
	        	
	        	// create Team
	        	Team homeTeam = new Team(homeTeamID, homeTeamName, homeStrengthVal);
	        	Team awayTeam = new Team(awayTeamID, awayTeamName, awayStrengthVal);
	        	
	        	// create matchup
	        	Matchup m = new Matchup(homeTeam, awayTeam, homeLine, awayLine);
	        	matchups.add(m);
	        			
	        }
	        
	        return matchups;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getLeagueData");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
				if(r1 != null) {
					r1.close();
				}
				if(r2 != null) {
					r2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return new ArrayList();
	}
	
	public static int getBalance(int userID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			
			ps = conn.prepareStatement("SELECT balance FROM Users WHERE userID='" + userID + "'");
	        r1= ps.executeQuery();
	      
	        
	        int balance = 0;
	        while(r1.next()) {
	        	balance = r1.getInt("balance");
	        }
	        return balance;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getBalance");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return 0;
	}
	
	
	public static void insertBet(int userID, int teamID, int amount, int matchupID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
		
	        st = conn.createStatement();
			st.execute("INSERT INTO Bets (userID, teamID, amount, matchupID) VALUES (" + "'" + userID + "'" + ", " + "'" + teamID + "'" + ", " + "'" + amount + "'" + ", '" + matchupID + "')");
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in insertBet");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static void updateBalance(int userID, int currentBalance){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
		
	        st = conn.createStatement();
	        st.execute("UPDATE Users SET balance=" + "'" + currentBalance + "'" + " WHERE userID='" + userID + "'");
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in updateBalance");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static List<Bet> getBets(int userID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		ResultSet r2 = null;
		
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
		
			ps = conn.prepareStatement("SELECT * FROM Bets WHERE userID='" + userID + "'");
	        r1= ps.executeQuery();
	        
	        List<Bet> bets = new ArrayList<>();
	        
	        while(r1.next()) {
	        	// get matchupID
	        	// get amount
	        	// get teamID
	        	int matchupID = r1.getInt("matchupID");
	        	int amount = r1.getInt("amount");
	        	int teamID = r1.getInt("teamID");
	        	
	        	// get teamName
	        	ps = conn.prepareStatement("SELECT Name FROM Teams WHERE teamID='" + teamID + "'");
		        r2 = ps.executeQuery();
		        
		        String teamName = "";
		        while(r2.next()) {
		        	teamName = r2.getString(1);
		        }
	        	
		        System.out.println("matchupID: " + matchupID);
		        System.out.println("teamID: " + teamID);
		        
		        double moneyLine = JDBCConnector.getMoneyLine(matchupID, teamID);
		        System.out.println("money line: " + moneyLine);
	        	// create Bet
	        	Bet b = new Bet(teamID, amount, matchupID, teamName, moneyLine);
	        	
	        	// add to Bet list
	        	bets.add(b);
	        }
	        
	        return bets;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getBets");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
				if(r1 != null) {
					r1.close();
				}
				if(r2 != null) {
					r2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return new ArrayList();
	}
	
	public static int getWinnerID(int matchupID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			
			ps = conn.prepareStatement("SELECT winnerTeamID FROM Matchups WHERE matchupID='" + matchupID + "'");
	        r1= ps.executeQuery();
	      
	        
	        int winnerID = 0;
	        while(r1.next()) {
	        	winnerID = r1.getInt(1);
	        }
	        return winnerID;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getWinnerID");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return 0;
	}
	
	public static double getMoneyLine(int matchupID, int teamID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			// check if homeTeam or awayTeam
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Matchups WHERE homeTeamID='" + teamID + "' AND matchupID='" + matchupID + "'");
	        r1= ps.executeQuery();
	      
	        
	        boolean isHomeTeam = true;
	        
	        while(r1.next()) {
	        	
	        	int result = r1.getInt(1);
	        	if(result == 0) {
	        		isHomeTeam = false;
	        	}else {
	        		isHomeTeam = true;
	        	}
	        }
	        
	        double moneyLine = 0;
	        
	        // get money line from homeLine
	        if(isHomeTeam) {
	        	ps = conn.prepareStatement("SELECT homeLine FROM Matchups WHERE matchupID='" + matchupID + "'");
		        r1= ps.executeQuery();
		        
		        if(r1.next()) {
		        	moneyLine = r1.getDouble(1);
		        }
	        }   
	        // get money line from awayLine
	        else {
	        	ps = conn.prepareStatement("SELECT awayLine FROM Matchups WHERE matchupID='" + matchupID + "'");
		        r1= ps.executeQuery();
		        
		        if(r1.next()) {
		        	moneyLine = r1.getDouble(1);
		        }
	        }
	        
	        return moneyLine;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getMoneyLine");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return 0;
	}
	
	public static void deleteBets(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
	        st = conn.createStatement();
			st.execute("DELETE FROM Bets");
		} catch(SQLException sqle) {
			System.out.println("SQLException in registerUser");
			sqle.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public static User getUser(int userID){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet r1 = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/");
			
			
			ps = conn.prepareStatement("SELECT * FROM Users WHERE userID='" + userID + "'");
	        r1= ps.executeQuery();
	      
	        
	        String firstName = "";
	        String lastName = "";
	        int balance = 0;
	        
	        while(r1.next()) {
	        	firstName = r1.getString("fname");
	        	lastName = r1.getString("lname");
	        	balance = r1.getInt("balance");
	        }
	        
	        User user = new User(firstName, lastName, balance);
	        return user;
	        
		} catch(SQLException sqle) {
			System.out.println("SQLException in getWinnerID");
			sqle.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return new User();
	}
	
}
