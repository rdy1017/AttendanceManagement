package finTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	Connection con = null;
	
	public Database(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� ���� Ȯ��");
			String url = "jdbc:mysql://aemaeth.iptime.org/JAVAFINAL";
			con = DriverManager.getConnection(url, "javauser", "wkqk");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� ������ : ���� ��ġ�� Ŀ���͸� �߰��Ͻÿ�");
		}
		catch (SQLException e) {
			System.out.println("�����ͺ��̽� ���� ���� : ���̵�, �н�����, �����ͺ��̽� ���� ���� Ȯ���Ͻÿ�");
		}
	}
	
	public int executeUpdate(String sql) {
		Statement ps = null;
		System.out.println("���۵� ������ : " + sql);
		try {
			ps = con.createStatement();
			return ps.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ResultSet executeQuery(String sql) {
		Statement ps = null;
		System.out.println("���۵� ������ : " + sql);
		try {
			ps = con.createStatement();
			return ps.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
}