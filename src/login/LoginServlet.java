package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = null;
		dispatch = request.getRequestDispatcher("Login.jsp");
		dispatch.forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javasystem", "root", "mySql108yen");
			ps = con.prepareStatement("select user_name from user where user_id = ? and password = ?");
			ps.setString(1, userId);
			ps.setString(2, password);
			rs = ps.executeQuery();

			String userName = null;
			while(rs.next()) {
				userName = rs.getString("user_name");
			}

			List<User> userList = new ArrayList<User>(); // 追加
			RequestDispatcher dispatch = null;
			if(userName != null) {
				// 追加
				ps = con.prepareStatement("select user_id, user_name, password from user");
				rs = ps.executeQuery();

				while(rs.next()) {
					User user = new User(rs.getString("user_id"), rs.getString("user_name"), rs.getString("password"));
					userList.add(user);
				}

				dispatch = request.getRequestDispatcher("LoginOK.jsp");
				request.setAttribute("userList", userList);
				dispatch.forward(request, response);
			} else {
				dispatch = request.getRequestDispatcher("LoginNG.jsp");
				dispatch.forward(request, response);
			}
		} catch(SQLException e_sql) {
			e_sql.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null) {
					con.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
