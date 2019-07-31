package finTest;

public class Main {

	public static void main(String[] args) {
		Database db = new Database();
		Login login = new Login(db);
		login.setVisible(true);
	}

}
