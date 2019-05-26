package login;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatch;

	/*
	 * ログイン画面への遷移
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatch = request.getRequestDispatcher("Login.jsp");
		dispatch.forward(request, response);
	}

	/*
	 * 利用者IDが
	 * 　あれば利用者一覧画面へ遷移
	 * 　なければログイン失敗画面へ遷移
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");

		User loginInformation = new User(userName, password);
		UserDao dao = new UserDaoImpl();
		User loginUser = dao.getLoginUser(loginInformation);

		if (loginUser == null) {
			dispatch = request.getRequestDispatcher("LoginNG.jsp");
			dispatch.forward(request, response);

		} else {
			session.setAttribute("loginInformation", loginInformation);
			List<User> userList = dao.getAllUser();
			session.setAttribute("userList", userList);
			dispatch = request.getRequestDispatcher("LoginOK.jsp");
			dispatch.forward(request, response);
		}

	}

}
