package ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


/*
 * 컨텍스트
 * 클라이언트(원하는 전략 생성) => 컨텍스트 => 전략(인터페이스)
 *      |                           |    |    |
 *      |						    전략1  전략2  전략3
 *      ------------------------------>(선택)
 */
public class JdbcContext {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) {
		Connection c = null;
		PreparedStatement ps = null;

		/*
		 *  예외가 발생하면 메소드실행 중단
		 *  일반적으로 서버에서는 제한된 개수의 DB 커넥션을 만들어 재사용 가능한 풀로 관리
		 *  DB 풀은 매번 etConnection()으로 가져간 커넥션을 명시적으로 close()해서 돌려줘야함
		 *  풀에 있는 리소스가 고갈되지 않게 빠르게 반환해야 함
		 */
		
		try {
			c = this.dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null) { try { ps.close(); } catch(SQLException e) {} }
			if(c != null) { try { c.close(); } catch(SQLException e) {} }
		}
	}
	
	public void executeSql(final String query) {
		/*
		 * 익명 클래스
		 * new 인터페이스 이름(){ 클래스 본문(인터페이스 메소드) };
		 */
		this.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(query);
				return ps;
			}
		});
	}
}