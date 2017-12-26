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
		// DI 컨테이너가 DataSource 오브젝트를 주입해줄 때 호출 됨
		// jdbccontext 수동으로 DI(밀접한 관계가 있어서)
//		this.jdbcContext = new JdbcContext();
//		JdbcContext.setDataSource(dataSource);

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
//		this.dataSource = dataSource;
	}
	
	// jdbcTemplate을 생성하면 직접 DI해주닌깐 더이상 필요 없음
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
		 * ResultSet의 결과를 User 오브젝트를 만들어 프로퍼티에 넣어줘야 함
		 * ResultSetExtractor 콜백 대신 RowMapper 콜백 사용
		 * ResultSet을 전달받고, 필요한 정보를 추출해서 리턴하는 방식
		 * ResultSet의 로우 하느를 매핑하기 위해 사용되기 때문에 여러 번 호출될 수 있음
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
		 * StatementStrategy 인터페이스의 makePreparedStatement() 콜백에 대응
		 * JdbcTemplate의 콜백은 
		 * PreparedStatementCreator 인터페이스의 createPreparedStatement() 메소드
		 * Connection을 제공받아서 PreparedStatement를 만들어 돌려 준다는 면에서 구조가 동일
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
		 * PreparedStatementCreator 콜백과
		 * ResultSetExtractor 콜백을 파라미터로 받는 query() 메소드
		 * 첫 번째 PreparedStatementCreator 콜백은 템플릿으로부터 Connection을 받고
		 * PreparedStatement를 돌려줌
		 * 두 번째 ResultSetExtractor는 템플릿으로부터 ResultSet을 받고 거기서 추출한 결과를 돌려줌 
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














