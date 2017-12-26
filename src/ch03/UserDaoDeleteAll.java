package ch03;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/*
 * 템플릿 메소드 패턴
 * 상속을 통해 기능을 확장
 * 변하지 않는 부분은 슈퍼 클래스로 두고 변하는 부분은 추상 메소드로 정의
 * 서브클래스에서 오버라이드하여 새롭게 정의해서 사용
 * 단점 : DAO로직마다 새로운 서브클래스를 생성해야 함
 */
public class UserDaoDeleteAll extends UserDao2{
	
	protected PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("delete from users");
		return ps;
	}
}
