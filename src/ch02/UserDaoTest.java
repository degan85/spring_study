package ch02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch01.User;
import ch01.UserDao;

// 테스트 코드 수정
@RunWith(SpringJUnit4ClassRunner.class) // 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@ContextConfiguration(locations = "../ch01/applicationContext.xml") // xml
//@ContextConfiguration(classes  = DaoFactory.class)	// annotation class
@DirtiesContext	//테스트 메소드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려줌
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;	// 초기화 할 때 자기 자신도 빈으로 등록
	
	// 픽스처(fixture)
	@Autowired	// 변수 타입과 일치하는 컨텍스트 내의 빈을 검색
	private UserDao dao;	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
//		System.out.println(this.context);
//		System.out.println(this);
		
		user1 = new User("id_1","이름1","spring1");
		user2 = new User("id_2","이름2","spring2");
		user3 = new User("id_3","이름3","spring3");
		
		// 테스트에서 UserDao가 사용할 DataSource 오브젝트를 직접 생성
		DataSource dataSource = new SingleConnectionDataSource(
				"jdbc:mysql://localhost/spring_db?autoReconnect=true&useSSL=false","root","inno100",true);
		
		// 코드에 의한 수동 DI
		dao.setDataSource(dataSource);
	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		// static method
		// matcher를 이용해 비교(is)
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}

	@Test
	public void count() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected= EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
	
//  또는 Run As jUnit Test 클릭	
//	public static void main(String[] args) {
//		JUnitCore.main("ch01.UserDaoTest");
//	}
}
