package ch01;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
//	public UserDao userDao() {
//		ConnectionMaker connectionMaker = new NConnectionMaker();
//		UserDao userDao = new UserDao(connectionMaker);
//		
//		return userDao;
//	}
//	@Bean
//	public UserDao userDao() {
//		UserDao userDao = new UserDao();
//		userDao.setConnectionMaker(connectionMaker());
//		return userDao;
//	}
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/spring_db");
		dataSource.setUsername("");
		dataSource.setPassword("");
		
		return dataSource;
	}
}
