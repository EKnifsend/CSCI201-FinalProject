import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/ExecuteBetServlet")


public class ExecuteBetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		//response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println("userID: " + request.getParameter("userID"));	
	
		int userID = Integer.parseInt(request.getParameter("userID"));
		
		// retrieve bets for specific user
		List<Bet> bets = JDBCConnector.getBets(userID);
		
		System.out.println("bets size: " + bets.size());
		
		var result = "";
		
		result += "Result:\n";
		
		// for each bet
		for(int i = 0; i < bets.size(); i++) {
			Bet b = bets.get(i);
			
			// use the matchupID to get (winnerID and the line)
			int winnerID = JDBCConnector.getWinnerID(b.matchupID);
			
			System.out.println("winnerID: " + winnerID);
			
			// win
			if(winnerID == b.teamID) {
				System.out.println("win");
				
				// get line
				double moneyLine = b.line;
				
				// update balance
				double moneyGained = b.amount + b.amount * moneyLine;
				
				System.out.println("money gained: " + moneyGained);
				
				int balance = JDBCConnector.getBalance(userID);
				
				System.out.println("original balance: " + balance);
				
				int currentBalance = (int) (balance + moneyGained);
				
				System.out.println("updated balance: " + currentBalance);
				JDBCConnector.updateBalance(userID, currentBalance);
				
				result += b.teamName + " Won!\n";
				
				
			}
			// lose
			else {
				System.out.println("lose");
				
				result += b.teamName + " Lost!\n";
			}
			
			
		}
		
		// re-create league
		JDBCConnector.deleteLeague();
		
		League league = new League();
		List<Matchup> matchups = league.getSchedule();
		// add to database
		for(int i = 0; i < matchups.size(); i++) {
			Matchup matchup = matchups.get(i);
			Team homeTeam = matchup.getHomeTeam();
			Team awayTeam = matchup.getAwayTeam();
			double homeLine = matchup.getHomeLine();
			double awayLine = matchup.getAwayLine();
			
			JDBCConnector.createLeague(homeTeam.getTeamID(), awayTeam.getTeamID(), matchup.projectedWinner.getTeamID(), homeLine, awayLine, i+1);
		}
		
		// remove previous biddings in database
		JDBCConnector.deleteBets();
		
		// send result string to client 
		
		// i.e. Result
		
		// teamName (win/lose) 
		// money gain : (either 0 or the calaculation from money line)
		
		// then output total gain: 
	
		pw.println(result);
		
	
		
	}
	
}

