import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/LoginServlet")


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		
		User user = new Gson().fromJson(request.getParameter("data"), User.class);
		
		String username = user.username;
		String password = user.password;
		
		Gson gson = new Gson();
		
		int userID = JDBCConnector.loginUser(username, password);
		System.out.println("USER ID \n");
		
		if(userID == -1) {
			//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Username does not exist";
			System.out.println(error);
			pw.write(gson.toJson(userID));
			pw.flush();
		}
		else if(userID == -2) {
			//response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Password does not match";
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
