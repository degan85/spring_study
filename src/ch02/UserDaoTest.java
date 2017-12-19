package ch02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch01.DaoFactory;
import ch01.User;
import ch01.UserDao;

// 테스트 코드 수정

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {

		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		assertThat(dao.getCount(), is(1));
		
		User user2 = dao.get(user.getId());
		
		// static method
		// matcher를 이용해 비교(is)
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
		
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("id_1","이름1","spring1");
		User user2 = new User("id_2","이름2","spring2");
		User user3 = new User("id_3","이름3","spring3");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
//  또는 Run As jUnit Test 클릭	
//	public static void main(String[] args) {
//		JUnitCore.main("ch01.UserDaoTest");
//	}
}
