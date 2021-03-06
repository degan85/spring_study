package ch01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private DataSource dataSource;
	
	private ConnectionMaker connectionMaker;
	
	// 수정자 메소드 set(id값)으로 해야 xml 설정에서 DI 됨
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
	private Connection setConnection() throws SQLException {
		//		Connection c = connectionMaker.makeConnection();
		Connection c = dataSource.getConnection();
		return c;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = setConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = setConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		User user = null;
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		 
		
		rs.close();
		ps.close();
		c.close();
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		return user;
	}

	public void deleteAll() throws SQLException, ClassNotFoundException {
		Connection c = setConnection();
		PreparedStatement ps = c.prepareStatement("delete from users");
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public int getCount() throws SQLException, ClassNotFoundException {
		Connection c = setConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		
		ResultSet rs = ps.executeQuery();
		rs.next();

		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
}














