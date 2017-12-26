package ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


/*
 * ���ؽ�Ʈ
 * Ŭ���̾�Ʈ(���ϴ� ���� ����) => ���ؽ�Ʈ => ����(�������̽�)
 *      |                           |    |    |
 *      |						    ����1  ����2  ����3
 *      ------------------------------>(����)
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
		 *  ���ܰ� �߻��ϸ� �޼ҵ���� �ߴ�
		 *  �Ϲ������� ���������� ���ѵ� ������ DB Ŀ�ؼ��� ����� ���� ������ Ǯ�� ����
		 *  DB Ǯ�� �Ź� etConnection()���� ������ Ŀ�ؼ��� ��������� close()�ؼ� ���������
		 *  Ǯ�� �ִ� ���ҽ��� ������ �ʰ� ������ ��ȯ�ؾ� ��
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
		 * �͸� Ŭ����
		 * new �������̽� �̸�(){ Ŭ���� ����(�������̽� �޼ҵ�) };
		 */
		this.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(query);
				return ps;
			}
		});
	}
}