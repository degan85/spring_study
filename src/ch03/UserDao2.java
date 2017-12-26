package ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import ch01.User;

public class UserDao2 {
//	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		// DI �����̳ʰ� DataSource ������Ʈ�� �������� �� ȣ�� ��
		// jdbccontext �������� DI(������ ���谡 �־)
//		this.jdbcContext = new JdbcContext();
//		JdbcContext.setDataSource(dataSource);

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
//		this.dataSource = dataSource;
	}
	
	// jdbcTemplate�� �����ϸ� ���� DI���ִѱ� ���̻� �ʿ� ����
//	private DataSource dataSource;
//	private Connection setConnection() throws SQLException {
//		Connection c = dataSource.getConnection();
//		return c;
//	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
//		this.jdbcContext.workWithStatementStrategy( new StatementStrategy() {
//			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//				PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
//				ps.setString(1, user.getId());
//				ps.setString(2, user.getName());
//				ps.setString(3, user.getPassword());
//				
//				return ps;
//			}
//		});
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
				user.getId(), user.getName(), user.getPassword());
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		
		/*
		 * ResultSet�� ����� User ������Ʈ�� ����� ������Ƽ�� �־���� ��
		 * ResultSetExtractor �ݹ� ��� RowMapper �ݹ� ���
		 * ResultSet�� ���޹ް�, �ʿ��� ������ �����ؼ� �����ϴ� ���
		 * ResultSet�� �ο� �ϴ��� �����ϱ� ���� ���Ǳ� ������ ���� �� ȣ��� �� ����
		 */
		return this.jdbcTemplate.queryForObject("select * from users where id =?", new Object[] {id},
				this.userMapper);
//		Connection c = setConnection();
//		
//		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
//		ps.setString(1, id);
//		
//		ResultSet rs = ps.executeQuery();
//		User user = null;
//		if(rs.next()) {
//			user = new User();
//			user.setId(rs.getString("id"));
//			user.setName(rs.getString("name"));
//			user.setPassword(rs.getString("password"));
//		}
//		 
//		
//		rs.close();
//		ps.close();
//		c.close();
//		
//		if(user == null) throw new EmptyResultDataAccessException(1);
//		
//		return user;
	}

	private RowMapper<User> userMapper =
		new RowMapper<User>() { public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		return user;
		}
	};
	

	public void deleteAll() throws SQLException, ClassNotFoundException {
//		this.jdbcContext.executeSql("delete from users");
		
		/*
		 * StatementStrategy �������̽��� makePreparedStatement() �ݹ鿡 ����
		 * JdbcTemplate�� �ݹ��� 
		 * PreparedStatementCreator �������̽��� createPreparedStatement() �޼ҵ�
		 * Connection�� �����޾Ƽ� PreparedStatement�� ����� ���� �شٴ� �鿡�� ������ ����
		 */
		this.jdbcTemplate.update( new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("delete from users");
			}
		});
//		this.jdbcTemplate.update("delete from users");
	}

	public int getCount() throws SQLException, ClassNotFoundException {
		
		/*
		 * PreparedStatementCreator �ݹ��
		 * ResultSetExtractor �ݹ��� �Ķ���ͷ� �޴� query() �޼ҵ�
		 * ù ��° PreparedStatementCreator �ݹ��� ���ø����κ��� Connection�� �ް�
		 * PreparedStatement�� ������
		 * �� ��° ResultSetExtractor�� ���ø����κ��� ResultSet�� �ް� �ű⼭ ������ ����� ������ 
		 * 
		 */
		
		return this.jdbcTemplate.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("select count(*) from users");
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
		});
//		Connection c = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int count = 0;
//		
//		try {
//			c = setConnection();
//
//			ps = c.prepareStatement("select count(*) from users");
//			
//			rs = ps.executeQuery();
//			rs.next();
//			count = rs.getInt(1);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//				}
//			}
//			if(ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//				}
//			}
//			if(c != null) {
//				try {
//					c.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		return count;
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}
}














