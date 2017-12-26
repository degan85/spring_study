package ch02;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch01.User;
import ch03.UserDao2;


// �׽�Ʈ �ڵ� ����
@RunWith(SpringJUnit4ClassRunner.class) // �������� �׽�Ʈ ���ؽ�Ʈ �����ӿ�ũ�� JUnit Ȯ���� ����
@ContextConfiguration(locations = "../ch01/applicationContext.xml") // xml
//@ContextConfiguration(classes  = DaoFactory.class)	// annotation class
@DirtiesContext	//�׽�Ʈ �޼ҵ忡�� ���ø����̼� ���ؽ�Ʈ�� �����̳� ���¸� �����Ѵٴ� ���� �׽�Ʈ ���ؽ�Ʈ �����ӿ�ũ�� �˷���
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;	// �ʱ�ȭ �� �� �ڱ� �ڽŵ� ������ ���
	
	// �Ƚ�ó(fixture)
	@Autowired	// ���� Ÿ�԰� ��ġ�ϴ� ���ؽ�Ʈ ���� ���� �˻�
	private UserDao2 dao;	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
//		System.out.println(this.context);
//		System.out.println(this);
		
		user1 = new User("id_1","�̸�1","spring1");
		user2 = new User("id_2","�̸�2","spring2");
		user3 = new User("id_3","�̸�3","spring3");
		
		// �׽�Ʈ���� UserDao�� ����� DataSource ������Ʈ�� ���� ����
//		DataSource dataSource = new SingleConnectionDataSource(
//				"jdbc:mysql://localhost/spring_db?autoReconnect=true&useSSL=false","id","password",true);
		
		// �ڵ忡 ���� ���� DI
//		dao.setDataSource(dataSource);
	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		// static method
		// matcher�� �̿��� ��(is)
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
	
	@Test
	public void getAll() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		
		List<User> user0 = dao.getAll();
		assertThat(user0.size(), is(0));
		
		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user1, users3.get(0));
		checkSameUser(user2, users3.get(1));
		checkSameUser(user3, users3.get(2));
	}
//  �Ǵ� Run As jUnit Test Ŭ��	
//	public static void main(String[] args) {
//		JUnitCore.main("ch01.UserDaoTest");
//	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}
}
