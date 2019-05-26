package manager;

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

/**
 *
 */
@WebServlet("/UserManageServlet")
public class UserManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestDispatcher dispatch;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String DBOperation = request.getParameter("crud");
		UserDao dao = new UserDaoImpl();
		String userId = request.getParameter("user_id");
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		User user = new User(userId, userName, password);
		switch(DBOperation) {
			case "登録":
				dao.createUser(user);
				break;
			case "更新":
				dao.updateUser(user);
				break;
			default: // 削除
				dao.deleteUser(user);
				break;
		}

		// 利用者管理システム画面の再表示
		List<User> userList = dao.getAllUser();
		session.setAttribute("userList", userList);
		dispatch = request.getRequestDispatcher("LoginOK.jsp");
		dispatch.forward(request, response);

	}

}
