package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDaoImpl implements UserDao {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	String errorMassage = "SQL実行が失敗しました。";

	@Override
	public User getLoginUser(User loginInfomation) {
		Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
	    try {
	    	con = ConnectionFactory.createConnection();
	        ps = con.prepareStatement("SELECT * FROM user "
	        						+ "WHERE user_name = ? AND password = ?");
            ps.setString(1, loginInfomation.getUser_name());
            ps.setString(2, loginInfomation.getPassword());
	        rs = ps.executeQuery();

	        User loginUser = null;

	        while(rs.next()) {
		        loginUser = new User(rs.getString("user_id") , rs.getString("user_name") ,
		        		rs.getString("password"));

	        }

	        return loginUser;

	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new IllegalStateException(errorMassage);

	    } finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

	    try {
	    	con = ConnectionFactory.createConnection();
	        ps = con.prepareStatement("SELECT * FROM user ");
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	User user = new User(rs.getString("user_id") , rs.getString("user_name") ,
	        			rs.getString("password"));
	            userList.add(user);
	        }
	        return userList;

	    } catch(SQLException e) {
	        e.printStackTrace();
	    	throw new IllegalStateException(errorMassage);

	    } finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createUser(User user) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionFactory.createConnection();
			ps = con.prepareStatement(
					"INSERT INTO user(user_id, user_name, password) "
					+ "values(?, ?, ?)");
			ps.setString(1, user.getUser_id());
			ps.setString(2, user.getUser_name());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException(errorMassage);
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteUser(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionFactory.createConnection();
			ps = con.prepareStatement("DELETE FROM user "
					+ "WHERE user_id = ?");
			ps.setString(1, user.getUser_id());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException(errorMassage);
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}

				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void updateUser(User user) {
		// rootの名前は更新させない。パスワード
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionFactory.createConnection();
			ps = con.prepareStatement("UPDATE user SET "
										+ "user_name = ?, "
										+ "password = ? "
										+ "WHERE user_id = ? ");
			ps.setString(1, user.getUser_name());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getUser_id());
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException(errorMassage);
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}

				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public int countDuplicateUserId(User user) {
		int countDuplicateCheck = 0;
		Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
	    	con = ConnectionFactory.createConnection();
	        ps = con.prepareStatement("SELECT COUNT(*) FROM user "
	        							+ "WHERE user_id = ?");
	        ps.setString(1, user.getUser_id());
	        rs = ps.executeQuery();
	        if(rs.next()){
	        	countDuplicateCheck = rs.getInt(1);
	        }

	        return countDuplicateCheck;

	    } catch(SQLException e) {
	        e.printStackTrace();
	    	throw new IllegalStateException(errorMassage);

	    } finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
