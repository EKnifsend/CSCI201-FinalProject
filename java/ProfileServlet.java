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

@WebServlet("/ProfileServlet")


public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println("userID: " + request.getParameter("userID"));	
	
		int userID = Integer.parseInt(request.getParameter("userID"));
		
		// retrieve bets for specific user
		List<Bet> bets = JDBCConnector.getBets(userID);
		
		System.out.println("bets size: " + bets.size());
		
		// retrieve user info
		User user = JDBCConnector.getUser(userID);
		
		Profile profile = new Profile(user, bets);
		
		Gson gson = new Gson();
		
		pw.write(gson.toJson(profile));
		
	}
	
}

