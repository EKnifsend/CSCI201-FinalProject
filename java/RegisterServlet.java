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

@WebServlet("/RegisterServlet")


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println(request.getParameter("data"));
		
		
		User user = new Gson().fromJson(request.getParameter("data"), User.class);
		
		String username = user.username;
		String password = user.password;
		String firstname = user.firstName;
		String lastname = user.lastName;
		int balance = user.balance;
		
		Gson gson = new Gson();
		
		int userID = JDBCConnector.registerUser(username, password, firstname, lastname);
		System.out.println("USER ID \n");
		
		if(userID == -1) {
			//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Username taken";
			System.out.println(error);
			pw.write(gson.toJson(userID));
			pw.flush();
		}
		else if(userID == -2) {
			//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Email is already registered";
			System.out.println(error);
			pw.write(gson.toJson(userID));
			pw.flush();
		}
		else {
			//response.setStatus(HttpServletResponse.SC_OK);
			pw.write(gson.toJson(userID));
			pw.flush();
		}
		
	}
}
