import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/DisplayServlet")

public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Matchup> matchups;
		// Only create a new league if the database is blank
		if(!JDBCConnector.leagueHasStarted()) {
			System.out.println("create league");
			League league = new League();
			matchups = league.getSchedule();
			// add to database
			for(int i = 0; i < matchups.size(); i++) {
				Matchup matchup = matchups.get(i);
				Team homeTeam = matchup.getHomeTeam();
				Team awayTeam = matchup.getAwayTeam();
				double homeLine = matchup.getHomeLine();
				double awayLine = matchup.getAwayLine();
				
				JDBCConnector.createLeague(homeTeam.getTeamID(), awayTeam.getTeamID(), matchup.projectedWinner.getTeamID(), homeLine, awayLine, i+1);
			}
		}else {
			// if league has started
			// get data from database
			System.out.println("get league");
			matchups = JDBCConnector.getLeagueData();
		}
		
		System.out.println("matchups: " + matchups);
		
		Gson gson = new Gson();
		String json = gson.toJson(matchups);
		pw.println(json);
	}
}
