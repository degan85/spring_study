package ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * ���� ����
 * ���� ��� ��Ģ(OCP)�� �� ��Ű�� �����̸鼭 
 * ���ø� �޼ҵ庸�� �����ϰ� Ȯ�强�� �پ 
 * ������Ʈ�� �ƾ� �ѷ� �и��ϰ� Ŭ���� ���������� �������̽��� ���ؼ��� ����
 * 
 * 1. DB Ŀ�ؼ� ��������
 * 2. PreparedStatement�� ������� �ܺ� ��� ȣ���ϱ� => ����(���ϴ� �κ�)
 * 3. ���޹��� PreparedStatement �����ϱ�
 * 4. ���ܰ� �߻��ϸ� �̸� �ٽ� �޼ҵ� ������ ������
 * 5. ��� ��쿡 ������� PreparedStatement�� Connection�� ������ �ݾ��ֱ�
 */
public interface StatementStrategy {
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
