package ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 전략 패턴
 * 개방 폐쇄 원칙(OCP)을 잘 지키는 구조이면서 
 * 템플릿 메소드보다 유연하고 확장성이 뛰어남 
 * 오브젝트를 아애 둘로 분리하고 클래스 레벨에서는 인터페이스를 통해서만 의존
 * 
 * 1. DB 커넥션 가져오기
 * 2. PreparedStatement를 만들어줄 외부 기능 호출하기 => 전략(변하는 부분)
 * 3. 전달받은 PreparedStatement 실행하기
 * 4. 예외가 발생하면 이를 다시 메소드 밖으로 던지기
 * 5. 모든 경우에 만들어진 PreparedStatement와 Connection을 적절히 닫아주기
 */
public interface StatementStrategy {
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
