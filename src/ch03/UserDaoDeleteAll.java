package ch03;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/*
 * ���ø� �޼ҵ� ����
 * ����� ���� ����� Ȯ��
 * ������ �ʴ� �κ��� ���� Ŭ������ �ΰ� ���ϴ� �κ��� �߻� �޼ҵ�� ����
 * ����Ŭ�������� �������̵��Ͽ� ���Ӱ� �����ؼ� ���
 * ���� : DAO�������� ���ο� ����Ŭ������ �����ؾ� ��
 */
public class UserDaoDeleteAll extends UserDao2{
	
	protected PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("delete from users");
		return ps;
	}
}
