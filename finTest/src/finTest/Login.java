package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField pwField;
	private JTextField idField;
	private JButton loginBtn;
	private JButton joinBtn;
	Database db;
	public boolean access = false;

	/**
	 * Create the frame.
	 */
	public Login(Database db) {
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		this.db = db;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("�� �� �� : ");
		lblNewLabel.setBounds(208, 141, 79, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("��й�ȣ : ");
		lblNewLabel_1.setBounds(208, 193, 79, 18);
		contentPane.add(lblNewLabel_1);
		
		pwField = new JPasswordField();
		pwField.setBounds(301, 190, 228, 24);
		contentPane.add(pwField);
		pwField.setColumns(10);
		pwField.setEchoChar('*');
		
		idField = new JTextField();
		idField.setColumns(10);
		idField.setBounds(301, 138, 228, 24);
		contentPane.add(idField);
		
		loginBtn = new JButton("�α���");
		loginBtn.setBounds(280, 266, 88, 27);
		contentPane.add(loginBtn);
		loginBtn.addActionListener(new ButtonListener());
		
		joinBtn = new JButton("ȸ������");
		joinBtn.setBounds(390, 266, 88, 27);
		contentPane.add(joinBtn);
		joinBtn.addActionListener(new ButtonListener());
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginBtn) {
				//���̵� ��ĭ���� Ȯ��
				if (idField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���.");
				} else {
					String sql = "SELECT Log_ID, Log_PW, Log_name FROM login WHERE Log_ID ='" + idField.getText() + "';";
					try {
						ResultSet rs = db.executeQuery(sql);
						if (rs.next()) {
							//��й�ȣ�� ��ġ���� ������
							if (!rs.getString("Log_PW").equals(new String(pwField.getPassword())))
								JOptionPane.showMessageDialog(null, "�Է������� ��ġ�ϴ� ȸ���� �����ϴ�.");
							else {
								JOptionPane.showMessageDialog(null, "�ȳ��ϼ���. " + rs.getString("Log_name") + "��");
								PageBundle pb = new PageBundle(db);
								pb.setVisible(true);
								dispose();
							}
						}
						//��ġ�ϴ� ���̵� ������
						else {
							JOptionPane.showMessageDialog(null, "�Է������� ��ġ�ϴ� ȸ���� �����ϴ�.");
						}
					} catch (SQLException sqle) {
						// TODO Auto-generated catch block
						sqle.printStackTrace();
					}
				}
			}
			
			if (e.getSource() == joinBtn) {
				Register register = new Register(db);
				register.setVisible(true);
			}
			
		}
	}
}
