package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;

public class BoardDao {

	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.118:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}

		return connection;
	}


	public Boolean newinsert(BoardVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select ifnull(max(g_no)+1,1) from board";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int g_no_get=0;
			while(rs.next()){
				g_no_get = rs.getInt(1);	
			}


			String sql1 = "insert into board values(null, ?, ?, 0, now(), ?, 1, 0, true, ?)";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, g_no_get);	
			pstmt.setLong(4, vo.getUser_no());


			int count = pstmt.executeUpdate();
			result = (count == 1);


		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;		
	}


	public List<BoardVo> getList(int page, String kwd) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			if(kwd!=null && kwd.length() != 0) {
				String sql = "select board.title, user.no, user.name, board.hit, date_format(board.reg_date, '%Y-%m-%d %h:%i:%s'), board.depth, board.contents, board.no, board.view  from board, user "
						+ "where board.user_no=user.no and (title Like ? or contents Like ?) and view =true order by g_no desc, o_no asc Limit ?, 5";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
				pstmt.setInt(3, page);
			}else {
				String sql = "select board.title, user.no, user.name, board.hit, date_format(board.reg_date, '%Y-%m-%d %h:%i:%s'), board.depth, board.contents, board.no, board.view  from board, user "
						+ "where board.user_no=user.no order by g_no desc, o_no asc Limit ?, 5";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, page);
			}

			rs = pstmt.executeQuery();

			while(rs.next()){
				String title = rs.getString(1);
				Long user_no = rs.getLong(2);
				String user_name = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int depth = rs.getInt(6);
				String contents = rs.getString(7);
				Long no = rs.getLong(8);
				boolean view = rs.getBoolean(9);
				
				BoardVo vo = new BoardVo();
				vo.setTitle(title);
				vo.setUser_name(user_name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setContents(contents);
				vo.setNo(no);
				vo.setView(view);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}	

	public BoardVo get(Long no) {
		BoardVo result = new BoardVo();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), user_no,  g_no, o_no, depth from board where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();

			while(rs.next()){
				String title = rs.getString(1);
				String contents = rs.getString(2);
				int hit = rs.getInt(3);
				String reg_date = rs.getString(4);
				Long user_no = rs.getLong(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);


				BoardVo vo = new BoardVo();
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setUser_no(user_no);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result = vo;
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}	



	public int getCount(String kwd) {
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			if(kwd!=null && kwd.length() != 0) {
				String sql = "select count(*) from board where (title Like ? or contents Like ?) and view =true";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
			}else {
				String sql = "select count(*) from board ";
				pstmt = connection.prepareStatement(sql);
			}

			rs = pstmt.executeQuery();

			while(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int newWrite() {
		int result = 0; 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select ifnull(max(g_no)+1,1) from board";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				result = rs.getInt(1);

			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}	

	public boolean modify(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "update board set title = ?, contents =? where no = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());									
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			System.out.println(vo.getNo()+":"+vo.getContents());
			int count = pstmt.executeUpdate();

			result = (count==1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	public void delete(Long no) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql =" update board set view=false where no = ?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}


	public boolean replyinsert(BoardVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set o_no = o_no+1 where g_no = ? and o_no >= ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, vo.getG_no());
			pstmt.setInt(2, vo.getO_no());
			pstmt.executeUpdate();

			String sql1 = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, true, ?)";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getG_no());	
			pstmt.setInt(4, vo.getO_no());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUser_no());


			int count = pstmt.executeUpdate();
			result = (count == 1);


		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;	

	}


	public boolean visit(Long no) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set hit = hit+1 where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			int count = pstmt.executeUpdate();

			result = (count == 1);


		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if(pstmt != null) {
					pstmt.close();
				}

				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;	

	}



}