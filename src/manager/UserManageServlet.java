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
		String resultMsg = null;
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
				// 登録ユーザー情報の各項目に空欄がないかチェック、いずれかの項目が空欄ならエラーメッセージを表示する
				if (user.getUser_id() != "" &&
					user.getUser_name() != "" &&
					user.getPassword() != "") {

					// 登録ユーザー情報の各項目lengthチェック。SQLException防止。
					if (user.getUser_id().length() > 4) {
						resultMsg = "ユーザーIDが4文字以内で指定してください";
						request.setAttribute("resultMsg", resultMsg);
						break;
					} else if (user.getUser_name().length() > 20) {
						resultMsg = "ユーザー名は20文字以内で指定してください";
						request.setAttribute("resultMsg", resultMsg);
						break;
					} else if (user.getPassword().length() > 10) {
						resultMsg = "パスワードは10文字以内で指定してください";
						request.setAttribute("resultMsg", resultMsg);
						break;
					}


					// 新規ユーザーIDを判定して、1の場合は登録NG。
					if("1".equals(user.getUser_id())) {
						resultMsg = "そのユーザーIDは使用できません";
						request.setAttribute("resultMsg", resultMsg);
						break;
					// 新規ユーザー名を判定して、rootの場合は登録NG。
					} else if("root".equals(user.getUser_name())) {
						resultMsg = "そのユーザー名は使用できません";
						request.setAttribute("resultMsg", resultMsg);
						break;
					// それ以外は登録OK
					} else {
						/*
						 * 登録しようとしているユーザーIDと同じIDの重複チェックをする
						 * 重複がなければ登録可
						 *
						 */
						if (dao.countDuplicateUserId(user) == 0) {
							resultMsg = "新規ユーザーを登録しました　＞　" + user.getUser_name();
							request.setAttribute("resultMsg", resultMsg);
							dao.createUser(user);
							break;
						} else {
							resultMsg = "そのIDはすでに使用されています";
							request.setAttribute("resultMsg", resultMsg);
							break;
						}
					}
				} else {
					resultMsg = "ユーザーID、ユーザー名、パスワードは必ず入力してください";
					request.setAttribute("resultMsg", resultMsg);
					break;
				}
			case "更新":
				// ユーザーID＝1のrootの名前は変更NG。パスワードは変更可。まずはユーザーID＝1かどうかを判定
				if ("1".equals(user.getUser_id())) {
					// rootの名前が変わっているかを判定し、変わっていたら変更NG
					if (!("root".equals(user.getUser_name()))) {
						resultMsg = "そのユーザー名は変更できません";
						request.setAttribute("resultMsg", resultMsg);
						break;
					// パスワードだけの変更はOK
					} else {
						resultMsg = "ユーザー情報を更新しました";
						request.setAttribute("resultMsg", resultMsg);
						dao.updateUser(user);
						break;
					}
				// ユーザーID＝1以外のユーザの変更はOK
				} else {
					resultMsg = "ユーザー情報を更新しました";
					request.setAttribute("resultMsg", resultMsg);
					dao.updateUser(user);
					break;
				}
			default: // 削除
				// ユーザーID＝1かどうかを判定。1（＝root）の場合は削除NG。
				if ("1".equals(user.getUser_id())) {
					resultMsg = "このユーザーは削除できません";
					request.setAttribute("resultMsg", resultMsg);
					break;
				// ユーザーID＝1以外のユーザの削除はOK
				} else {
					resultMsg = "ユーザーを削除しました";
					request.setAttribute("resultMsg", resultMsg);
					dao.deleteUser(user);
					break;
				}
		}

		// 利用者管理システム画面の再表示
		List<User> userList = dao.getAllUser();
		session.setAttribute("userList", userList);
		dispatch = request.getRequestDispatcher("LoginOK.jsp");
		dispatch.forward(request, response);

	}

}
