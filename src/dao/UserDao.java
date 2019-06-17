package dao;

import java.util.List;

import model.User;

public interface UserDao {

	/**
	 * ログインユーザ情報検索
	 */
	User getLoginUser(User user);

	/**
	 * 全利用者取得
	 * @return List<User>
	 */
	List<User> getAllUser();

	/**
	 * 利用者情報の登録
	 */
	void createUser(User user);

	/**
	 * 利用者情報の削除
	 */
	void deleteUser(User user);

	/**
	 * 利用者情報の更新
	 */
	void updateUser(User user);

	/**
	 * 利用者情報登録時の重複チェック
	 */
	int countDuplicateUserId(User user);

}
