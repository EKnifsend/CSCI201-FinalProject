import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/BidServlet")


public class BidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println("teamID: " + request.getParameter("teamID"));
		System.out.println("userID: " + request.getParameter("userID"));
		System.out.println("amount: " + request.getParameter("amount"));
		System.out.println("matchupID: " + request.getParameter("matchupID"));
		
		
		
		Gson gson = new Gson();
		
		int userID = Integer.parseInt(request.getParameter("userID"));
		int teamID = Integer.parseInt(request.getParameter("teamID"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		int matchupID = Integer.parseInt(request.getParameter("matchupID"));
		
		int balance = JDBCConnector.getBalance(userID);
		System.out.println("balance: " + balance);
		
		// if able to bid
		if(balance - amount > 0) {
			// insert trade into bet
			JDBCConnector.insertBet(userID, teamID, amount, matchupID);
			
			// update balance
			int currentBalance = balance - amount;
			JDBCConnector.updateBalance(userID, currentBalance);
			
			System.out.println("can trade + added to Bet");
			pw.write(gson.toJson(1));
			pw.flush();
		}else {
			// output error message
			System.out.println("cant trade");
			pw.write(gson.toJson(-1));
			pw.flush();
		}
		
	}
	
}

